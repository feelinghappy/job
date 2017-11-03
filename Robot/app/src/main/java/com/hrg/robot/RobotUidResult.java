package com.hrg.robot;

/**
 * Created by liutao on 2017/9/30.
 */

public class RobotUidResult {
    private String wilddog_uid;

    public String getWilddog_uid() {
        return wilddog_uid;
    }

    public void setWilddog_uid(String wilddog_uid) {
        this.wilddog_uid = wilddog_uid;
    }

    @Override
    public String toString() {
        return "RobotUidResult{" +
                "wilddog_uid='" + wilddog_uid + '\'' +
                '}';
    }
}
