package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
/*{
    "status": 1,
    "message": "成功",
    "data": [
        {
            "id": "1",
            "type": "1",
            "imgpath": "http://healthywo.cn/Public/Uploads/20161226/yd_58607151a0e10.jpg",
            "content": "        科学晨练能改善神经系统功能，通过晨练活动可提高中枢神经系统的机能水平，提高机体的强度、均衡性和灵活性，使大脑皮质的兴奋与抑制的转换能力的提高。体育锻炼能使神经细胞获得更充足的能量物质和氧气，使大脑和神经系统在紧张的工作过程中获得充分的能量物质保证，据研究，当脑细胞工作时，它所需的血液量比肌肉细胞多10—20倍，在脑耗氧量占全身耗氧量的20—50%，科学的晨练能使大脑的兴奋与抑制过程合理交替，避免神经系统过程紧张，可以消除疲劳，使头脑清醒思想敏捷",
            "sport_number": 0,
            "jindu": 0
        }
    ]
}*/
public class Sport {
    public int status;
    public String message;
    public List<DataBean> data;
    public  class DataBean {
        public String id;
        public String type;
        public String imgpath;
        public String content;
        public int sport_number;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", type='" + type + '\'' +
                    ", imgpath='" + imgpath + '\'' +
                    ", content='" + content + '\'' +
                    ", sport_number=" + sport_number +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Sport{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
