package com.glaboratory.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Adem on 07.02.2016..
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//TODO lombok not working
@Data
@Getter
public class Forecast {
    @JsonProperty("lat")
    private Float lat;

    @JsonProperty("lon")
    private Float lon;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("timezone_offset")
    private Integer timezoneOffset;

    @JsonProperty("current")
    private WeatherData current;

    @JsonProperty("hourly")
    private List<HourlyWeatherData> hourly;

    @JsonProperty("daily")
    private List<DailyWeatherData> daily;

    @JsonProperty("alerts")
    private Alert alerts;

    public Forecast() {
    }

    public Forecast(Float lat, Float lon, String timezone, Integer timezoneOffset, WeatherData current, List<HourlyWeatherData> hourly, List<DailyWeatherData> daily, Alert alerts) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
        this.current = current;
        this.hourly = hourly;
        this.daily = daily;
        this.alerts = alerts;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public WeatherData getCurrent() {
        return current;
    }

    public void setCurrent(WeatherData current) {
        this.current = current;
    }

    public List<HourlyWeatherData> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyWeatherData> hourly) {
        this.hourly = hourly;
    }

    public List<DailyWeatherData> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyWeatherData> daily) {
        this.daily = daily;
    }

    public Alert getAlerts() {
        return alerts;
    }

    public void setAlerts(Alert alerts) {
        this.alerts = alerts;
    }
}
