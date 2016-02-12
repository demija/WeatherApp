package com.glaboratory.weatherapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Adem on 07.02.2016..
 */
public class CurrentWeather {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mMinTemperature;
    private double mMaxTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mPrecipType;
    private String mSummary;
    private String mTimeZone;
    private double mWindSpeed;

    public CurrentWeather() {
        mIcon = "n/a";
        mTime = 0;
        mTemperature = 0.0;
        mMinTemperature = 0.0;
        mMaxTemperature = 0.0;
        mHumidity = 0.0;
        mPrecipChance = 0.0;
        mPrecipType = "n/a";
        mSummary = "n/a";
        mTimeZone = "n/a";
        mWindSpeed = 0.0;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getBackgroundId() {
        int iconId;

        switch (mIcon) {
            case "clear-day":
                iconId = R.drawable.clear_day_background;
                break;

            case "clear-night":
                iconId = R.drawable.clear_night_background;
                break;

            case "rain":
                iconId = R.drawable.rain_background;
                break;

            case "snow":
                iconId = R.drawable.snow_background;
                break;

            case "sleet":
                iconId = R.drawable.sleet_background;
                break;

            case "wind":
                iconId = R.drawable.wind_background;
                break;

            case "fog":
                iconId = R.drawable.fog_background;
                break;

            case "cloudy":
                iconId = R.drawable.cloudy_background;
                break;

            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy_day_background;
                break;

            case "partly-cloudy-night":
                iconId = R.drawable.partly_cloudy_night_background;
                break;

            default:
                iconId = R.color.White;
        }

        return iconId;
    }

    public int getIconId() {
        int iconId;

        switch (mIcon) {
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;

            case "clear-night":
                iconId = R.drawable.clear_night;
                break;

            case "rain":
                iconId = R.drawable.rain;
                break;

            case "snow":
                iconId = R.drawable.snow;
                break;

            case "sleet":
                iconId = R.drawable.sleet;
                break;

            case "wind":
                iconId = R.drawable.wind;
                break;

            case "fog":
                iconId = R.drawable.fog;
                break;

            case "cloudy":
                iconId = R.drawable.cloudy;
                break;

            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;

            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;

            default:
                iconId = R.drawable.clear_day;
        }

        return iconId;
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime() {
        SimpleDateFormat formater = new SimpleDateFormat("h:mm a");
        formater.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formater.format(dateTime);
        return  timeString;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int) Math.round(((mTemperature - 32) / 1.8000));
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public int getMinTemperature() {
        return (int) Math.round(((mMinTemperature - 32)/1.8000));
    }

    public void setMinTemperature(double minTemperature) {
        mMinTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return (int) Math.round(((mMaxTemperature - 32)/1.8000));
    }

    public void setMaxTemperature(double maxTemperature) {
        mMaxTemperature = maxTemperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPrecipChance() {
        return (int)Math.round(mPrecipChance * 100);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getPrecipType() {
        return mPrecipType;
    }

    public void setPrecipType(String precipType) {
        mPrecipType = precipType;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }
}
