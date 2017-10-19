package com.longcai.medical.bean;

import java.util.List;
/**
 * Created by Administrator on 2017/8/23.
 */

public class MyDeviceResult {
    private String watch_imei;
    private List<Robot> robot_imei;

    public String getWatch_imei() {
        return watch_imei;
    }

    public void setWatch_imei(String watch_imei) {
        this.watch_imei = watch_imei;
    }

    public List<Robot> getRobot_imei() {
        return robot_imei;
    }

    public void setRobot_imei(List<Robot> robot_imei) {
        this.robot_imei = robot_imei;
    }
}
