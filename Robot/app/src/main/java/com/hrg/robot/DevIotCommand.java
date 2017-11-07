package com.hrg.robot;

/**
 * 设备iot命令,由设备端定义的,部分可以忽略
 * {@see define_head_a4b.h}
 */
public enum DevIotCommand {

    TLIPT_NONE,
    TLIPT_ROBOT_STATE_REQUEST,//上位机请求设备返回状态数据 上位机 -> 设备
    TLIPT_ROBOT_STATE_RESPOND,//设备回复上位机请求状态数据 设备 -> 上位机
    TLIPT_ROBOT_MOVE_ACTION,//上位机控制设备电机运动(通过SLAMWARE Core) 上位机 <-> 设备
    TLIPT_ROBOT_TOUCH_EVENT,//设备报告给上位机发生触摸事件 设备 -> 上位机
    TLIPT_ROBOT_VOICE_WAKE_UP_EVENT,//设备报告上位机发生语音唤醒事件 设备 -> 上位机
    TLIPT_ROBOT_DIRECT_MOVE_ACTION,//上位机控制设备电机运动(直接驱动) 上位机 <-> 设备
    TLIPT_ROBOT_VOICE_CMD_REQUEST,//执行麦克风阵列命令 上位机 -> 设备
    TLIPT_ROBOT_VOICE_CMD_RESPOND,//麦克风阵列命令结果 设备 -> 上位机
    TLIPT_ROBOT_DEVICE_UUID_SET,//设置设备UUID 上位机 -> 设备
    TLIPT_ROBOT_DEVICE_UUID_GET,//获取设备UUID 设备 -> 上位机
    TLIPT_ROBOT_ANION_SET,//开关负离子模块 上位机 <-> 设备
    TLIPT_ROBOT_ANION_GET,//获取负离子模块状态 设备 <-> 上位机
    TLIPT_ROBOT_FAN_SET,//开关风扇模块 上位机 <-> 设备
    TLIPT_ROBOT_FAN_GET,//获取风扇模块状态 设备 <-> 上位机
}
