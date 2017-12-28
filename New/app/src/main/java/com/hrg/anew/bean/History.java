package com.hrg.anew.bean;

import java.io.Serializable;
import java.util.List;

public class History implements Serializable
{
    public List<Heart_data_history> heart_data_list;
    public List<Sleep_history> sleepList;
    public List<Sport_data_history> sport_dataList;
    public List<Blood_data> blood_dataList;

    public static class Heart_data_history
    {
        public String avg_heart;
        public int min_heart;
        public int max_heart;
        public String create_time;
    }

    public static class Sleep_history
    {
        public int sleep_time;
        public int sleep_grade;
        public String create_time;
    }

    public static class Sport_data_history
    {
        public int step_num;
        public int calory;
        public String create_time;
    }

}