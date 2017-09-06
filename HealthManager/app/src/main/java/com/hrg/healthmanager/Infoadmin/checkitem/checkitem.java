package com.hrg.healthmanager.Infoadmin.checkitem;
public class checkitem
{
    private int itemid;
    private String name;

    public checkitem(String paramString, int paramInt)
    {
        this.name = paramString;
        this.itemid = paramInt;
    }

    public int getImageId()
    {
        return this.itemid;
    }

    public String getName()
    {
        return this.name;
    }
}