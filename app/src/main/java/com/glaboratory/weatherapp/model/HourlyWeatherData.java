package com.glaboratory.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Adem on 18.08.2021..
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//TODO lombok not working
@Data
@Getter
public class HourlyWeatherData {
    @JsonProperty("dt")
    private Integer dt;

    @JsonProperty("temp")
    private Float temp;

    @JsonProperty("feels_like")
    private Float feelsLike;

    @JsonProperty("pressure")
    private Integer pressure;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("dew_point")
    private Float dewPoint;

    @JsonProperty("uvi")
    private Float uvi;

    @JsonProperty("clouds")
    private Integer clouds;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("wind_speed")
    private Float windSpeed;

    @JsonProperty("wind_deg")
    private Integer windDeg;

    @JsonProperty("wind_gust")
    private Float windGust;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("pop")
    private Integer pop;

    public HourlyWeatherData() {
    }

    public HourlyWeatherData(Integer dt, Float temp, Float feelsLike, Integer pressure, Integer humidity, Float dewPoint, Float uvi, Integer clouds, Integer visibility, Float windSpeed, Integer windDeg, Float windGust, List<Weather> weather, Integer pop) {
        this.dt = dt;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.uvi = uvi;
        this.clouds = clouds;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.windGust = windGust;
        this.weather = weather;
        this.pop = pop;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Float getUvi() {
        return uvi;
    }

    public void setUvi(Float uvi) {
        this.uvi = uvi;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(Integer windDeg) {
        this.windDeg = windDeg;
    }

    public Float getWindGust() {
        return windGust;
    }

    public void setWindGust(Float windGust) {
        this.windGust = windGust;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }
}
