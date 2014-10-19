package edu.gw.krunal.weathercatch;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WeatherActivity extends ListActivity {


    private double mLatitude;
    private double mLongitude;
    private final String weatherUndergroundUrl = "http://api.wunderground.com/api/26f9a89412c778c5/forecast10day/q/";
    private String customizableApiUrl;
    private ProgressDialog mProgressSymbol;
    private List<WeatherDataModel> weatherDataList= new ArrayList<WeatherDataModel>();
    public final String PREFERENCE_FILE = "MyPreference";
    public final String WEATHER_ACTIVITY_TAG="WeatherActivityDebug";
    private SharedPreferences sharedpreferences;
    private ArrayAdapterWeather adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //setting default preference(settings) if first time user accessing application.
        sharedpreferences = getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        if(sharedpreferences.contains(settingsConstant.DEFAULT)) {
            Log.d(WEATHER_ACTIVITY_TAG,"Don't set default preferences");
        }
        else{
            setDefaultPreference();
            Log.d(WEATHER_ACTIVITY_TAG, "First Time User Open App.");
        }


        // Acquire a reference to the system Location Manager

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //Called when a new location is found by the network location provider.
                //Store current latitude and longitude.
                mLatitude = location.getLatitude();
                mLongitude=location.getLongitude();
                loadFromWeatherApi();
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            public void onProviderEnabled(String provider) {
                Toast.makeText(WeatherActivity.this,getResources().getString(R.string.Refresh_result),Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) {
                Toast.makeText(WeatherActivity.this,getResources().getString(R.string.GPS_disabled),Toast.LENGTH_SHORT).show();
            }
        };
        //Requesting Single Location update using Network provider.
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);

    }

    private void setDefaultPreference(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(settingsConstant.ZIPCODE_TEST_KEY,false);
        editor.putString(settingsConstant.ZIPCODE_KEY,"20037");
        editor.putString(settingsConstant.METRIC_KEY,settingsConstant.TEMPERATURE_F);
        editor.putInt(settingsConstant.DAY_COUNT,3);
        editor.putBoolean(settingsConstant.DEFAULT,true);
        editor.commit();
    }

    private void loadFromWeatherApi(){
        //checking zipcode settings.
        if((sharedpreferences.contains(settingsConstant.DEFAULT))&&(sharedpreferences.getBoolean(settingsConstant.ZIPCODE_TEST_KEY,false)))
        {
            //Zipcode is set to true condition so need to customize API url by zipcode.
            customizableApiUrl = weatherUndergroundUrl+sharedpreferences.getString(settingsConstant.ZIPCODE_KEY,"20037")+".json";
        }
        else{
            //customize according to current location.
            if(mLatitude==0 || mLongitude==0)
            {
                Toast.makeText(WeatherActivity.this,getResources().getString(R.string.Network_not_available),Toast.LENGTH_SHORT).show();
                //get last location in future.
                return;
            }
            customizableApiUrl = weatherUndergroundUrl+mLatitude+","+mLongitude+".json";
        }

        Log.d("weather conditions:",customizableApiUrl);
        //3rd party library
        AsyncHttpClient clientWeather = new AsyncHttpClient();
        clientWeather.get(customizableApiUrl,new TextHttpResponseHandler() {

            @Override
            public void onStart(){
                mProgressSymbol= mProgressSymbol.show(WeatherActivity.this, getResources().getString(R.string.Wait_dialog_title),getResources().getString(R.string.Wait_dialog_description),false);
            }

            @Override
            public void onFinish(){
                mProgressSymbol.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // add to string file.
                Toast.makeText(WeatherActivity.this,getResources().getString(R.string.internet_not_working),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JSONObject jsonWeatherData;
                try {
                    jsonWeatherData = new JSONObject(responseString);
                    parseAndDisplayWeatherData(jsonWeatherData);
                }catch (Exception e){
                    // add to string file.
                    Toast.makeText(WeatherActivity.this,getResources().getString(R.string.check_settings),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void parseAndDisplayWeatherData(JSONObject jsonWeatherData){
        try{

            //clearing data if it has old data.
            weatherDataList.clear();

            //getting correct Json Array
            JSONObject simpleForecastObject = jsonWeatherData.getJSONObject("forecast").getJSONObject("simpleforecast");
            Log.d("weather conditions:",simpleForecastObject.toString());
            JSONArray daysForecastArrayObject = simpleForecastObject.getJSONArray("forecastday");
            int iteration = sharedpreferences.getInt(settingsConstant.DAY_COUNT,3);

            if(iteration>daysForecastArrayObject.length())
                iteration=daysForecastArrayObject.length();

            //Iterating through the array to get weather data.
            for(int i=0;i<iteration;i++)
            {
                JSONObject singleDayObject = daysForecastArrayObject.getJSONObject(i);
                singleDayObject.getJSONObject("date").getString("weekday");
                //adding data into our weather data model.
                weatherDataList.add(new WeatherDataModel(
                        (singleDayObject.getJSONObject("date")).getString("weekday"),
                        (singleDayObject.getJSONObject("high")).getString("fahrenheit"),
                        (singleDayObject.getJSONObject("high")).getString("celsius"),
                        (singleDayObject.getJSONObject("low")).getString("fahrenheit"),
                        (singleDayObject.getJSONObject("low")).getString("celsius"),
                        singleDayObject.getString("conditions"),
                        singleDayObject.getString("icon")
                ));

                //debugging correct data entered.
                Log.d("weather conditions:", (weatherDataList.get(i)).toString());


            }
        }catch (Exception e){
            Toast.makeText(WeatherActivity.this,getResources().getString(R.string.check_settings),Toast.LENGTH_SHORT).show();
            Log.d("weather conditions:",e.getLocalizedMessage());
        }
        adapter = new ArrayAdapterWeather(WeatherActivity.this, R.layout.single_row_list,weatherDataList, sharedpreferences);
        setListAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        //called when return from settings page.
        loadFromWeatherApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsActivityIntent = new Intent(WeatherActivity.this,WeatherSettings.class);
            //startActivity(settingsActivityIntent);
            startActivityForResult(settingsActivityIntent,0);
        }

        if(id == R.id.action_refresh){
            loadFromWeatherApi();
        }
        return super.onOptionsItemSelected(item);
    }
}
