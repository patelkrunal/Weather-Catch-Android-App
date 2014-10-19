package edu.gw.krunal.weathercatch;

/**
 * Created by Krunal on 10/16/2014.
 */
import java.util.List;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArrayAdapterWeather extends ArrayAdapter<WeatherDataModel> {
    public final String PREFERENCE_FILE = "MyPreference";
    private final List<WeatherDataModel> mWeatherModels;
    private final Context mContext;
    private SharedPreferences mSharedPreferences;

    public ArrayAdapterWeather(Context context, int resourceViewId, List<WeatherDataModel> objects,SharedPreferences sharedPreferences) {
        super(context, resourceViewId, objects);
        mWeatherModels = objects;
        mContext = context;
        mSharedPreferences=sharedPreferences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.single_row_list, parent, false);

        WeatherDataModel weatherModel = mWeatherModels.get(position);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView dayTextView = (TextView) rowView.findViewById(R.id.day);
        TextView tempTextView = (TextView) rowView.findViewById(R.id.temp);
        TextView descriptionTextView = (TextView) rowView
                .findViewById(R.id.description);

        dayTextView.setText(weatherModel.getDayName());
        //check user's setting for celsius?
        if((mSharedPreferences.getString(settingsConstant.METRIC_KEY,"F")).equalsIgnoreCase(settingsConstant.TEMPERATURE_F))
        {
            tempTextView.setText(weatherModel.getHighTempF()+" \u2109"+"/"+weatherModel.getLowTempF()+" \u2109");
        }
        else{
            tempTextView.setText(weatherModel.getHighTempC()+" \u2103"+"/"+weatherModel.getLowTempC()+" \u2103");
        }

        descriptionTextView.setText(weatherModel.getDescription());

        //inefficient code to set icon.
        if (weatherModel.getIcon().equals("chanceflurries")) {
            imageView.setImageResource(R.drawable.chanceflurries);
        } else if (weatherModel.getIcon().equals("chancerain")) {
            imageView.setImageResource(R.drawable.chancerain);
        }
        else if (weatherModel.getIcon().equals("chancesleet")) {
            imageView.setImageResource(R.drawable.chancesleet);
        }
        else if (weatherModel.getIcon().equals("chancesnow")) {
            imageView.setImageResource(R.drawable.chancesnow);
        }
        else if (weatherModel.getIcon().equals("chancetstorms")) {
            imageView.setImageResource(R.drawable.chancetstorms);
        }
        else if (weatherModel.getIcon().equals("clear")) {
            imageView.setImageResource(R.drawable.clear);
        }
        else if (weatherModel.getIcon().equals("cloudy")) {
            imageView.setImageResource(R.drawable.cloudy);
        }
        else if (weatherModel.getIcon().equals("flurries")) {
            imageView.setImageResource(R.drawable.flurries);
        }
        else if (weatherModel.getIcon().equals("fog")) {
            imageView.setImageResource(R.drawable.fog);
        }
        else if (weatherModel.getIcon().equals("hazy")) {
            imageView.setImageResource(R.drawable.hazy);
        }
        else if (weatherModel.getIcon().equals("mostlycloudy")) {
            imageView.setImageResource(R.drawable.mostlycloudy);
        }
        else if (weatherModel.getIcon().equals("mostlysunny")) {
            imageView.setImageResource(R.drawable.mostlysunny);
        }
        else if (weatherModel.getIcon().equals("partlycloudy")) {
            imageView.setImageResource(R.drawable.partlycloudy);
        }
        else if (weatherModel.getIcon().equals("partlysunny")) {
            imageView.setImageResource(R.drawable.partlysunny);
        }
        else if (weatherModel.getIcon().equals("rain")) {
            imageView.setImageResource(R.drawable.rain);
        }
        else if (weatherModel.getIcon().equals("sleet")) {
            imageView.setImageResource(R.drawable.sleet);
        }
        else if (weatherModel.getIcon().equals("sunny")) {
            imageView.setImageResource(R.drawable.sunny);
        }
        else if (weatherModel.getIcon().equals("tstorms")) {
            imageView.setImageResource(R.drawable.tstorms);
        }


        return rowView;
    }
}
