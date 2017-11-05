package com.tuhin.weathora.network.models;

public class Main {
    private String humidity;

    private double pressure;

    private String temp_max;

    private String temp_min;

    private float temp;

    public String getHumidity ()
    {
        return humidity;
    }

    public void setHumidity (String humidity)
    {
        this.humidity = humidity;
    }

    public double getPressure ()
    {
        return pressure;
    }

    public void setPressure (double pressure)
    {
        this.pressure = pressure;
    }

    public String getTemp_max ()
    {
        return temp_max;
    }

    public void setTemp_max (String temp_max)
    {
        this.temp_max = temp_max;
    }

    public String getTemp_min ()
    {
        return temp_min;
    }

    public void setTemp_min (String temp_min)
    {
        this.temp_min = temp_min;
    }

    public float getTemp ()
    {
        return temp;
    }

    public void setTemp (float temp)
    {
        this.temp = temp;
    }

    @Override
    public String toString()
    {
        return "MainPojo [humidity = "+humidity+", pressure = "+pressure+", temp_max = "+temp_max+", temp_min = "+temp_min+", temp = "+temp+"]";
    }
}
