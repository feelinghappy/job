package com.longcai.medical.bean;

import java.util.List;

public class Messages {
/*	{
	    "code": 1,
	    "msg": "正常获取",
	    "result": [
	        {
	            "message_id": "2",
	            "message_body": {
	                "nickname": "在家",
	                "avatar": "http://v2.healthywo.com/Avatar/149A36EB642954AD8729DC97157FC841586b4502af6d9.jpg",
	                "message_type": 2,
	                "family_name": "幸福一家人",
	                "family_id": 1
	            }
	        }
	    ]
	}*/

	public List<MessageResult> result;
	
	public class  MessageResult{
		public String message_id;  //消息id
		public MessageResultBody message_body;
		@Override
		public String toString() {
			return "MessageResult [message_id=" + message_id
					+ ", message_body=" + message_body + "]";
		}
	}
	public class  MessageResultBody{
		public String nickname;  //昵称
		public String avatar;  //头像
		public String message_type;  //消息类型 1：邀请 2：申请
		public String family_name;  //家庭名称
		public String family_id;  //家庭ID
		public String robot_imei;  //家庭机器人编码
		@Override
		public String toString() {
			return "MessageResultBody [nickname=" + nickname + ", avatar="
					+ avatar + ", message_type=" + message_type
					+ ", family_name=" + family_name + ", family_id="
					+ family_id + ", robot_imei="
					+ robot_imei+"]";
		}
	}
}
