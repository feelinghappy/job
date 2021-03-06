package com.longcai.medical.bean;

import java.util.List;

public class WatchLocation {
	/*
	 * { "code": 1, "msg": "获取成功", "result": [ { "family_id": "1",
	 * "family_name": "幸福一家人", "robot_imei": "ZK20C1494818532823" } ] }
	 */

	public int code;
	public String msg;
	public List<RobotResult> result;

	public class RobotResult {
		public String family_id;// 家庭ID
		public String family_name; // 家庭名称
		public String robot_imei;// 机器人编码

		@Override
		public String toString() {
			return "RobotResult [family_id=" + family_id + ", family_name="
					+ family_name + ", robot_imei=" + robot_imei + "]";
		}

	}

	@Override
	public String toString() {
		return "Robot [code=" + code + ", msg=" + msg + ", result=" + result
				+ "]";
	}

}
