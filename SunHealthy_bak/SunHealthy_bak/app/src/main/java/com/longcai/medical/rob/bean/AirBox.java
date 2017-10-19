package com.longcai.medical.rob.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liutao on 2017/9/24.
 */

public class AirBox implements Parcelable{
    private String co2;//<0~500> 氧化碳含量量，整型
    private String ch2o;//<0~500> 甲醛浓度，整型
    private String temperature;//<0~60> 温度，整型
    private String humidity;//<20~90> 湿度，整型
    private String pm25;//<10> pm2.5指数，整型
    private String gas;//<true/false> 天然⽓气是否超标,bool值，true表示正常,false超标

    public AirBox() {
    }

    @Override
    public String toString() {
        return "AirBox{" +
                "co2='" + co2 + '\'' +
                ", ch2o='" + ch2o + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", gas='" + gas + '\'' +
                '}';
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getCh2o() {
        return ch2o;
    }

    public void setCh2o(String ch2o) {
        this.ch2o = ch2o;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.co2);
        dest.writeString(this.ch2o);
        dest.writeString(this.temperature);
        dest.writeString(this.humidity);
        dest.writeString(this.pm25);
        dest.writeString(this.gas);
    }

    protected AirBox(Parcel in) {
        this.co2 = in.readString();
        this.ch2o = in.readString();
        this.temperature = in.readString();
        this.humidity = in.readString();
        this.pm25 = in.readString();
        this.gas = in.readString();
    }

    public static final Creator<AirBox> CREATOR = new Creator<AirBox>() {
        @Override
        public AirBox createFromParcel(Parcel source) {
            return new AirBox(source);
        }

        @Override
        public AirBox[] newArray(int size) {
            return new AirBox[size];
        }
    };
}
