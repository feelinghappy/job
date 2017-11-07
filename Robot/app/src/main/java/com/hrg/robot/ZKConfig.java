package com.hrg.robot;
/**
 * Created by Wu on 2017/9/21.
 */
public class ZKConfig {

    public static final String permission = "com.zhikang.status.permission";

    public static final String ACTION_RECEIVE_RESPONSE_MSG = "com.launcher1.zhikang.action.RESPONSE_MSG";
    public static final String ACTION_ZK_REQUEST = "com.launcher1.zhikang.action.REQ_TO_LAUNCHER";

    public static final String ACTION_SERIAL_PORT = "com.launcher1.zhikang.action.serialport";

    /**
     * GET_ROBOT_MSG:获取机器人基本信息
     * GET_AIR_MSG:获取空气盒子信息
     * GET_MONITOR_MSG:获取监控信息
     * REQ_CONTROL_ANION:请求控制负离子开关
     * REQ_CONTROL_FAN:请求控制风扇开关
     * REQ_CONTROL_MONITOR:请求控制监控开关
     * REQ_CONTROL_MOVE:请求移动控制
     */
    public static final int GET_ROBOT_MSG = 1;
    public static final int GET_AIR_MSG = 2;
    public static final int GET_MONITOR_MSG = 3;
    public static final int REQ_CONTROL_ANION = 4;
    public static final int REQ_CONTROL_FAN = 5;
    public static final int REQ_CONTROL_MONITOR = 6;
    public static final int REQ_CONTROL_MOVE = 7;

    /**
     * ANION、FAN、MONITOR开关请求的Value
     */
    public static final int ON = 1;
    public static final int OFF = 0;
    //操作失败返回值
    public static final int fail = 3;


    /**
     * 请求类型和结果的KEY值
     */
    public static final String KEY_COMMAND = "ZKCommand";
    public static final String KEY_VALUE = "ZKValue";

    /**
     * 获取返回结果的KEY值
     */
    public static final String ACTION_MSG_RESPONSE = "KEY_RESPONSE";
}