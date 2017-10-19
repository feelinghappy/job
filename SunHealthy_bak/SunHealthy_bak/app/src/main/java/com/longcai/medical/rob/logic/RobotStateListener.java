package com.longcai.medical.rob.logic;

import com.longcai.medical.rob.bean.Robot;

/**
 * Created by liutao on 2017/9/24.
 */

public interface RobotStateListener {
    void onSuccess(Robot robot);
    void onFailed(String msg);
}
