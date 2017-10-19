package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/6.
 */

public class LoginBean {

    /**
     * code : 200
     * msg : success
     * result : {"token":"13ba3c5dd2fca58196a4273bd76ed4bc","has_info":"0"}
     */

    private String code;
    private String msg;
    private ResultBean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * token : 13ba3c5dd2fca58196a4273bd76ed4bc
         * has_info : 0
         */

        private String token;
        private String has_info;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHas_info() {
            return has_info;
        }

        public void setHas_info(String has_info) {
            this.has_info = has_info;
        }
    }
}

