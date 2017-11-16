package com.hrg.anew.bean;
import java.io.Serializable;

public class Getmonitor implements Serializable
{
    public Sleep sleep;
    public Heart_data heart_data;
    public Sport_data sport_data;
    public Blood_data blood_data;

    public void setSleep(Sleep sleep)
    {
        this.sleep = sleep;
    }

    public void setHeart_data(Heart_data heart_data)
    {
        this.heart_data = heart_data;
    }

    public void setSport_data(Sport_data sport_data)
    {
        this.sport_data = sport_data;
    }

    public void setBlood_data(Blood_data blood_data)
    {
        this.blood_data = blood_data;
    }

    public Blood_data getBlood_data()
    {
        return blood_data;
    }

    public Sport_data getSport_data()
    {
        return sport_data;
    }

    public Sleep getSleep()
    {
        return sleep;
    }

    public Heart_data getHeart_data()
    {
        return heart_data;
    }






}