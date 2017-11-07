package com.hrg.robot;
import java.io.Serializable;

/**
 * Created by Wu on 2017/9/26.
 */
public class ResultData implements Serializable {

    public int type;//返回数据类型，1：机器人基本信息，2：空气盒子信息，3：监控状态信息，4：控制开关结果信息，5：控制移动
    public Object obj;

    public ResultData(int type, Object obj){
        this.type = type;
        this.obj = obj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "type=" + type +
                ", obj=" + obj +
                '}';
    }
}