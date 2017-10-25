package com.hrg.robotproject.logic;

import com.hrg.robotproject.bean.Robot;

/**
 * Created by liutao on 2017/9/24.
 */

public interface RobotStateListener {
    void onSuccess(Robot robot);
    void onFailed(String msg);
}
