package com.hrg.anew.bean;
public  class Heart_data
{
    public int avg_heart;
    public int min_heart;
    public int max_heart;

    public int getAvg_heart ()
    {
        return avg_heart;
    }

    public void setAvg_heart(int avg_heart )
    {
        this.avg_heart = avg_heart;
    }

    public int getMin_heart ()
    {
        return this.min_heart;
    }

    public void setMin_heart(int min_heart )
    {
        this.min_heart = min_heart;
    }

    public int getMax_heart ()
    {
        return this.max_heart;
    }

    public void setMax_heart(int max_heart )
    {
        this.max_heart = max_heart;
    }
}