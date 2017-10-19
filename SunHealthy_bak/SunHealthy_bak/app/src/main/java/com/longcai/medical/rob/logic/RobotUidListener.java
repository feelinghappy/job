package com.longcai.medical.rob.logic;

import java.util.List;

/**
 * Created by liutao on 2017/10/14.
 */

public interface RobotUidListener {
    void onIdsSuccess(List<String> ids);
    void onFailed(int code,String msg);
}
