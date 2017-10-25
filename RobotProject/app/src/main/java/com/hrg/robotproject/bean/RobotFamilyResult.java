package com.hrg.robotproject.bean;

/**
 * Created by liutao on 2017/9/30.
 */

public class RobotFamilyResult {
    private String family_id;
    private String family_name;
    private String robot_imei;

    @Override
    public String toString() {
        return "RobotFamilyResult{" +
                "family_id='" + family_id + '\'' +
                ", family_name='" + family_name + '\'' +
                ", robot_imei='" + robot_imei + '\'' +
                '}';
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
}
