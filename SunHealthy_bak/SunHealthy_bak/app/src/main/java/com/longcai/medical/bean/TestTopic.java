package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

/*{
    "status": 1,
    "message": "成功",
    "data": [
        {
            "id": "1",
            "title": "您精力充沛吗？（指精神头足，乐于做事)",
            "answer1": "没有(根本不/从来没有)",
            "answer2": "很少(有一点/偶尔)",
            "answer3": "有时(有些/少数时间)",
            "answer4": "经常(相当/多数时间)",
            "answer5": "总是(非常/每天)",
            "habitus": ""
        }
    ]
}*/
public class TestTopic {
    public int status;
    public String message;
    public List<DataBean> data;
    public static class DataBean {
        public String id;
        public String title;
        public String answer1;
        public String answer2;
        public String answer3;
        public String answer4;
        public String answer5;
        public String habitus;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", answer1='" + answer1 + '\'' +
                    ", answer2='" + answer2 + '\'' +
                    ", answer3='" + answer3 + '\'' +
                    ", answer4='" + answer4 + '\'' +
                    ", answer5='" + answer5 + '\'' +
                    ", habitus='" + habitus + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TestTopic{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
