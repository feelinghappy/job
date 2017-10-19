package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DeviceBean {
    private int id;// 1.robot 2.watch
    private String family_id;
    private String family_name;
    private String robot_imei;
    private String watch_imei;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getRobot_imei() {
        return robot_imei;
    }

    public void setRobot_imei(String robot_imei) {
        this.robot_imei = robot_imei;
    }

    public String getWatch_imei() {
        return watch_imei;
    }

    public void setWatch_imei(String watch_imei) {
        this.watch_imei = watch_imei;
    }
}
