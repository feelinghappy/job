package com.longcai.medical.bean;

public class Robot {
    /*"result": [{"family_id": "4","family_name": "幸福一家人","robot_imei": "ZK20C1494818532823"},*/
    public String family_id;// 家庭ID
    public String family_name; // 家庭名称
    public String robot_imei;// 机器人编码
    private String rname = "";
    public String robotState ="0";// 机器人离线在线状态（机器人服务器获取）

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
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

    public String getRobotState() {
        return robotState;
    }

    public void setRobotState(String robotState) {
        this.robotState = robotState;
    }
}
