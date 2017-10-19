package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/12.
 */
/*{
    "status": 1,
    "message": "保存成功！",
    "data": {
        "uid": "543F1CFE2A63AB434D2FB0B2A02DA286",
        "physical": "平和质"
    }
}*/
public class ConstitutionResult {
    public int status;
    public String message;
    public DataBean data;
    public static class DataBean {
        public String uid;
        public String physical;

        @Override
        public String toString() {
            return "DataBean{" +
                    "uid='" + uid + '\'' +
                    ", physical='" + physical + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ConstitutionResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
