package edu.gw.krunal.weathercatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


public class WeatherSettings extends Activity {
    private RadioButton zipcodeYesRadio;
    private RadioButton zipcodeNoRadio;
    private RadioButton metricFRadio;
    private RadioButton metricCRadio;
    private EditText zipcodeEditText;
    private Button saveZipcodeButton;
    private Button saveDaysButtun;
    private Spinner dayCountSpinner;
    public final String PREFERENCE_FILE = "MyPreference";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private final String TEST_AUTOROTATE = "test_autorotate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_settings);

        //initializing shared preferences reference
        sharedpreferences = getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);

        //wire up important items.
        zipcodeYesRadio = (RadioButton)findViewById(R.id.radio_zipcode_yes);
        zipcodeNoRadio = (RadioButton)findViewById(R.id.radio_zipcode_no);
        metricFRadio= (RadioButton)findViewById(R.id.radio_metric_f);
        metricCRadio=(RadioButton)findViewById(R.id.radio_metric_c);
        zipcodeEditText =(EditText)findViewById(R.id.zipcode_edittext);
        saveZipcodeButton =(Button)findViewById(R.id.save_zipcode_button_id);
        dayCountSpinner=(Spinner)findViewById(R.id.day_count_spinner);
        saveDaysButtun=(Button)findViewById(R.id.save_days_button_id);

        //Applying Current Settings on UI.
        applyCurrentUISetting();

        //recording changes.
        saveZipcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = sharedpreferences.edit();
                editor.putString(settingsConstant.ZIPCODE_KEY,zipcodeEditText.getText().toString());
                editor.commit();
                Toast.makeText(WeatherSettings.this,zipcodeEditText.getText().toString()+" " +getResources().getString(R.string.zipcode_saved), Toast.LENGTH_SHORT).show();
            }
        });

        saveDaysButtun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = sharedpreferences.edit();
                editor.putInt(settingsConstant.DAY_COUNT, Integer.parseInt(dayCountSpinner.getSelectedItem().toString()));
                editor.commit();
                Toast.makeText(WeatherSettings.this,dayCountSpinner.getSelectedItem().toString()+" " +getResources().getString(R.string.day_count_number), Toast.LENGTH_SHORT).show();
            }
        });



    }
    
    private void applyCurrentUISetting(){
        //Set temperature radio button
        if((sharedpreferences.getString(settingsConstant.METRIC_KEY,settingsConstant.TEMPERATURE_F)).equalsIgnoreCase(settingsConstant.TEMPERATURE_C)){
            //temperature is in celsius
            metricCRadio.setChecked(true);
        }
        else{
            metricFRadio.setChecked(true);
        }

        //set zipcode
        zipcodeEditText.setText(sharedpreferences.getString(settingsConstant.ZIPCODE_KEY,"20037"));

        //set zipcode yes no radio button
        if(sharedpreferences.getBoolean(settingsConstant.ZIPCODE_TEST_KEY,false)){
            zipcodeYesRadio.setChecked(true);
        }

        dayCountSpinner.setSelection(sharedpreferences.getInt(settingsConstant.DAY_COUNT,3)-1);
    }

    //Following method handles click event of Zipcode radio buttons
    public void onZipcodeRadioButtonClicked(View view) {

        editor = sharedpreferences.edit();
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_zipcode_yes:
                if (checked)
                    // Zipcode is set to use from next
                    editor.putBoolean(settingsConstant.ZIPCODE_TEST_KEY,true);
                    break;
            case R.id.radio_zipcode_no:
                if (checked)
                    // Use latitude and longitude
                    editor.putBoolean(settingsConstant.ZIPCODE_TEST_KEY,false);
                    break;
        }
        editor.commit();
    }

    public void onMetricRadioButtonClicked(View view) {

        editor = sharedpreferences.edit();
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_metric_c:
                if (checked)
                    // Set celsius
                    editor.putString(settingsConstant.METRIC_KEY, settingsConstant.TEMPERATURE_C);
                break;
            case R.id.radio_metric_f:
                if (checked)
                    // Set Fahrenheit
                    editor.putString(settingsConstant.METRIC_KEY, settingsConstant.TEMPERATURE_F);
                break;
        }
        editor.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TEST_AUTOROTATE,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
             finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
