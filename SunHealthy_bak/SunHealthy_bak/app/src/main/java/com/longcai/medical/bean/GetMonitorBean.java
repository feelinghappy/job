package com.longcai.medical.bean;

import java.io.Serializable;
/**
 * Created by Administrator on 2017/5/26.
 */

public class GetMonitorBean implements Serializable {


    private static final long serialVersionUID = -27333422620937336L;
    /**
     * code : 1
     * msg : 获取成功
     * result : {"sleep_data":{"sleep_time":"24468","sleep_grade":"4"},"heart_data":{"avg_heart":"77","min_heart":"64","max_heart":"98"},"blood_data":{"systolic":"115","diastolic":"75","create_time":"02 :08pm"},"sport_data":{"step_num":"1925","calory":"81"}}
     /**
     * sleep_data : {"sleep_time":"24468","sleep_grade":"4"}
     * heart_data : {"avg_heart":"77","min_heart":"64","max_heart":"98"}
     * blood_data : {"systolic":"115","diastolic":"75","create_time":"02 :08pm"}
     * sport_data : {"step_num":"1925","calory":"81"}
     */

    private SleepDataBean sleep_data;
    private HeartDataBean heart_data;
    private BloodDataBean blood_data;
    private SportDataBean sport_data;

    public SleepDataBean getSleep_data() {
        return sleep_data;
    }

    public void setSleep_data(SleepDataBean sleep_data) {
        this.sleep_data = sleep_data;
    }

    public HeartDataBean getHeart_data() {
        return heart_data;
    }

    public void setHeart_data(HeartDataBean heart_data) {
        this.heart_data = heart_data;
    }

    public BloodDataBean getBlood_data() {
        return blood_data;
    }

    public void setBlood_data(BloodDataBean blood_data) {
        this.blood_data = blood_data;
    }

    public SportDataBean getSport_data() {
        return sport_data;
    }

    public void setSport_data(SportDataBean sport_data) {
        this.sport_data = sport_data;
    }

    public class SleepDataBean implements Serializable {
        /**
         * sleep_time : 24468
         * sleep_grade : 4
         */

        private String sleep_time = "0";
        private String sleep_grade = "5";

        public String getSleep_time() {
            return sleep_time;
        }

        public void setSleep_time(String sleep_time) {
            this.sleep_time = sleep_time;
        }

        public String getSleep_grade() {
            return sleep_grade;
        }

        public void setSleep_grade(String sleep_grade) {
            this.sleep_grade = sleep_grade;
        }
    }

    public class HeartDataBean implements Serializable {
        /**
         * avg_heart : 77
         * min_heart : 64
         * max_heart : 98
         */

        private String avg_heart = "0";
        private String min_heart = "0";
        private String max_heart = "0";

        public String getAvg_heart() {
            return avg_heart;
        }

        public void setAvg_heart(String avg_heart) {
            this.avg_heart = avg_heart;
        }

        public String getMin_heart() {
            return min_heart;
        }

        public void setMin_heart(String min_heart) {
            this.min_heart = min_heart;
        }

        public String getMax_heart() {
            return max_heart;
        }

        public void setMax_heart(String max_heart) {
            this.max_heart = max_heart;
        }
    }

    public class BloodDataBean implements Serializable {
        /**
         * systolic : 115
         * diastolic : 75
         * create_time : 02 :08pm
         */

        private String systolic = "0";
        private String diastolic = "0";
        private String create_time = "0";

        public String getSystolic() {
            return systolic;
        }

        public void setSystolic(String systolic) {
            this.systolic = systolic;
        }

        public String getDiastolic() {
            return diastolic;
        }

        public void setDiastolic(String diastolic) {
            this.diastolic = diastolic;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public class SportDataBean implements Serializable {
        /**
         * step_num : 1925
         * calory : 81
         */

        private String step_num = "0";
        private String calory = "0";

        public String getStep_num() {
            return step_num;
        }

        public void setStep_num(String step_num) {
            this.step_num = step_num;
        }

        public String getCalory() {
            return calory;
        }

        public void setCalory(String calory) {
            this.calory = calory;
        }
    }
}
