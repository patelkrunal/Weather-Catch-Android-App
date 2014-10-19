package edu.gw.krunal.weathercatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mukta on 10/16/2014.
 */
public class WeatherDataModel {
    private String mDayName;
    private String mHighTempF;
    private String mHighTempC;
    private String mLowTempF;
    private String mLowTempC;
    private String mDescription;

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getDayName() {
        return mDayName;
    }

    public void setDayName(String dayName) {
        mDayName = dayName;
    }

    public String getHighTempF() {
        return mHighTempF;
    }

    public void setHighTempF(String highTempF) {
        mHighTempF = highTempF;
    }

    public String getHighTempC() {
        return mHighTempC;
    }

    public void setHighTempC(String highTempC) {
        mHighTempC = highTempC;
    }

    public String getLowTempF() {
        return mLowTempF;
    }

    public void setLowTempF(String lowTempF) {
        mLowTempF = lowTempF;
    }

    public String getLowTempC() {
        return mLowTempC;
    }

    public void setLowTempC(String lowTempC) {
        mLowTempC = lowTempC;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    private String mIcon;

    public WeatherDataModel(String dayName, String highTempF, String highTempC, String lowTempF,String lowTempC, String description, String icon){
        mDayName = dayName;
        mHighTempF=highTempF;
        mHighTempC=highTempC;
        mLowTempF=lowTempF;
        mLowTempC=lowTempC;
        mDescription=description;
        mIcon=icon;
    }


    @Override
    public String toString(){
        return mDayName+" "+mDescription;
    }
}
