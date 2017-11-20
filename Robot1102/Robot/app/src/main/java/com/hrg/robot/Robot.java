package com.hrg.robot;
import android.os.Parcelable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liutao on 2017/9/24.
 */

public class Robot implements Parcelable {
    private String uid;
    private String name;
    private String status;//<online/offline> 当前离在线状态, 字符串串
    private String type;//<desktop/ground> 机器器⼈人类型,字符串串
    private String electricity;//<0~1>当前电量量, 浮点型
    private String currentUser;//<uid> 当前正在与机器器⼈人交互的⽤用户, 字符串串
    private String monitor;//<true/false>机器器⼈人监控状态, bool值true可被监控, false不不⾏行行
    private String fanState;//<high/low/off>⻛风扇状态, 字符串串
    private String control;//<command> 控制指令, 字符串串, ⻅见下⾯面说明
    private AirBox airBox; //空气盒子

    public Robot() {

    }

    @Override
    public String toString() {
        return "Robot{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", electricity='" + electricity + '\'' +
                ", currentUser='" + currentUser + '\'' +
                ", monitor='" + monitor + '\'' +
                ", fanState='" + fanState + '\'' +
                ", control='" + control + '\'' +
                ", airBox=" + airBox +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }


    public String getFanState() {
        return fanState;
    }

    public void setFanState(String fanState) {
        this.fanState = fanState;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public AirBox getAirBox() {
        return airBox;
    }

    public void setAirBox(AirBox airBox) {
        this.airBox = airBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.status);
        dest.writeString(this.type);
        dest.writeString(this.electricity);
        dest.writeString(this.currentUser);
        dest.writeString(this.monitor);
        dest.writeString(this.fanState);
        dest.writeString(this.control);
        dest.writeParcelable(this.airBox, flags);
    }

    protected Robot(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.status = in.readString();
        this.type = in.readString();
        this.electricity = in.readString();
        this.currentUser = in.readString();
        this.monitor = in.readString();
        this.fanState = in.readString();
        this.control = in.readString();
        this.airBox = in.readParcelable(AirBox.class.getClassLoader());
    }

    public static final Creator<Robot> CREATOR = new Creator<Robot>() {
        @Override
        public Robot createFromParcel(Parcel source) {
            return new Robot(source);
        }

        @Override
        public Robot[] newArray(int size) {
            return new Robot[size];
        }
    };
}
