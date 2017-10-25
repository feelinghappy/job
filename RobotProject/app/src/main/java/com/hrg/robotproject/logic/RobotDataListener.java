package com.hrg.robotproject.logic;

import com.hrg.robotproject.bean.Robot;

import java.util.List;
import java.util.Map;

/**
 * Created by liutao on 2017/9/24.
 */

public interface RobotDataListener {
    void onRobotSuccess(Map<String,Robot> robots);
   // void onRobotSuccess(List<Robot> robots);
    void onIdsSuccess(List<String> ids);
    void onFailed(int code,String msg);
}
