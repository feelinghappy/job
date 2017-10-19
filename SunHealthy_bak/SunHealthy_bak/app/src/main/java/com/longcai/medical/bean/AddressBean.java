package com.longcai.medical.bean;


import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class AddressBean {

    /**
     * code : 200
     * msg : success
     * result : [{"area_id":1,"area_name":"北京","parent_id":0},{"area_id":2,"area_name":"天津","parent_id":0},{"area_id":3,"area_name":"河北","parent_id":0},{"area_id":4,"area_name":"山西","parent_id":0},{"area_id":5,"area_name":"内蒙古","parent_id":0},{"area_id":6,"area_name":"辽宁","parent_id":0},{"area_id":7,"area_name":"吉林","parent_id":0},{"area_id":8,"area_name":"黑龙江","parent_id":0},{"area_id":9,"area_name":"上海","parent_id":0},{"area_id":10,"area_name":"江苏","parent_id":0},{"area_id":11,"area_name":"浙江","parent_id":0},{"area_id":12,"area_name":"安徽","parent_id":0},{"area_id":13,"area_name":"福建","parent_id":0},{"area_id":14,"area_name":"江西","parent_id":0},{"area_id":15,"area_name":"山东","parent_id":0},{"area_id":16,"area_name":"河南","parent_id":0},{"area_id":17,"area_name":"湖北","parent_id":0},{"area_id":18,"area_name":"湖南","parent_id":0},{"area_id":19,"area_name":"广东","parent_id":0},{"area_id":20,"area_name":"广西","parent_id":0},{"area_id":21,"area_name":"海南","parent_id":0},{"area_id":22,"area_name":"重庆","parent_id":0},{"area_id":23,"area_name":"四川","parent_id":0},{"area_id":24,"area_name":"贵州","parent_id":0},{"area_id":25,"area_name":"云南","parent_id":0},{"area_id":26,"area_name":"西藏","parent_id":0},{"area_id":27,"area_name":"陕西","parent_id":0},{"area_id":28,"area_name":"甘肃","parent_id":0},{"area_id":29,"area_name":"青海","parent_id":0},{"area_id":30,"area_name":"宁夏","parent_id":0},{"area_id":31,"area_name":"新疆","parent_id":0},{"area_id":32,"area_name":"台湾","parent_id":0},{"area_id":33,"area_name":"香港","parent_id":0},{"area_id":34,"area_name":"澳门","parent_id":0},{"area_id":35,"area_name":"海外","parent_id":0}]
     */

    private String code;
    private String msg;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements IPickerViewData {
        /**
         * area_id : 1
         * area_name : 北京
         * parent_id : 0
         */

        private int area_id;
        private String area_name;
        private int parent_id;

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        // 实现 IPickerViewData 接口，
        // 这个用来显示在PickerView上面的字符串，
        // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
        @Override
        public String getPickerViewText() {
            return area_name;
        }
    }
}
