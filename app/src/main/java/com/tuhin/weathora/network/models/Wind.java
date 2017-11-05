package com.tuhin.weathora.network.models;

public class Wind {
    private float speed;

    private String deg;

    public float getSpeed (){
        return speed;
    }

    public void setSpeed (float speed)
    {
        this.speed = speed;
    }

    public String getDeg ()
    {
        return deg;
    }

    public void setDeg (String deg)
    {
        this.deg = deg;
    }

    @Override
    public String toString()
    {
        return "WindPojo [speed = "+speed+", deg = "+deg+"]";
    }
}
