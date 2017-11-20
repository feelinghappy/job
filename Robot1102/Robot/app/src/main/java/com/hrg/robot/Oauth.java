package com.hrg.robot;
public class Oauth
{
    public String robot_sn;//机器人sn码
    public String random_str; //随机数
    public String _timestamp;//时间戳
    public String secret_code;//认证码
    public String Getrobot_sn()
    {
        return robot_sn;
    }
    public String Getrandom_str()
    {
        return random_str;
    }
    public String Gettimestamp()
    {
        return _timestamp;
    }
    String Getsecretcode()
    {
        return secret_code;
    }
}