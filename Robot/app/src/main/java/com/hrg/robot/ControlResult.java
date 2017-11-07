package com.hrg.robot;
import java.io.Serializable;

/**
 * Created by Wu on 2017/9/21.
 */
public class ControlResult implements Serializable {

    public int type;//1:air 2:monitor
    public int result;//0:open, 1:close

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ControlResult{" +
                "type=" + type +
                ", result=" + result +
                '}';
    }
}