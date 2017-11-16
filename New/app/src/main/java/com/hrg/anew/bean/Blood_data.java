package com.hrg.anew.bean;
public  class Blood_data
{
    public int systolic;
    public int diastolic;
    public String create_time;

    public int getSystolic ()
    {
        return systolic;
    }

    public void setSystolic(int systolic )
    {
        this.systolic = systolic;
    }

    public int getDiastolic ()
    {
        return diastolic;
    }

    public void setDiastolic(int diastolic )
    {
        this.diastolic = diastolic;
    }

    public String getCreate_time ()
    {
        return create_time;
    }

    public void setCreate_time(String create_time )
    {
        this.create_time = create_time;
    }
}