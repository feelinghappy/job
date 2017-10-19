package com.longcai.medical.rob.logic;

import com.longcai.medical.rob.bean.Robot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liutao on 2017/9/19.
 */

public class RobotData {

    public static Map<String, Robot> robotMap = null;

    private static class SingletonHolder{
        private static final RobotData instance = new RobotData();
    }

    private RobotData(){

    }

    public static RobotData getInstance(){
        return SingletonHolder.instance;
    }


    public Map<String, Robot> getRobotMap() {
        return robotMap;
    }

    public void setRobotMap(Map<String, Robot> robotMap) {
        robotMap = new HashMap<>();
        this.robotMap = robotMap;
    }
}
