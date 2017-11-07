package com.hrg.robot;

/**
 * Created by Wu on 2017/9/21.
 */
public interface ZKCallback {

    void resultMsg(ResultData resultData);

    void error(String msg);
}
