package com.bhumik.practiseproject.databind;

/**
 * Created by bhumik on 18/5/16.
 */
public class DataBindData {
    private String temperatureCelsius;

    public DataBindData(String temp) {
        this.temperatureCelsius = temp;
    }

    public String getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(String temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }
}
