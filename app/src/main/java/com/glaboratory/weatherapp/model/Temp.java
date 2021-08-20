package com.glaboratory.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Adem on 20.08.2021..
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//TODO lombok not working
@Data
@Getter
public class Temp {
    @JsonProperty("day")
    private Float day;

    @JsonProperty("min")
    private Float min;

    @JsonProperty("max")
    private Float max;

    @JsonProperty("night")
    private Float night;

    @JsonProperty("eve")
    private Float eve;

    @JsonProperty("morn")
    private Float morn;

    public Temp() {
    }

    public Temp(Float day, Float min, Float max, Float night, Float eve, Float morn) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public Float getDay() {
        return day;
    }

    public void setDay(Float day) {
        this.day = day;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public Float getNight() {
        return night;
    }

    public void setNight(Float night) {
        this.night = night;
    }

    public Float getEve() {
        return eve;
    }

    public void setEve(Float eve) {
        this.eve = eve;
    }

    public Float getMorn() {
        return morn;
    }

    public void setMorn(Float morn) {
        this.morn = morn;
    }
}
