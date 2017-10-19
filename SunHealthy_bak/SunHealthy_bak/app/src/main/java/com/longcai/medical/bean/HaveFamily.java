package com.longcai.medical.bean;

import java.util.List;

public class HaveFamily {

	/*
	{
    "code": 1,
    "msg": "获取成功",
    "result": [
        {
            "family_id": "1",
            "family_name": "幸福一家人",
            "member_count": "1",
            "family_number": "000001",
            "thumb": "3",
            "is_image": "0"
        }
    ]
}
	 */
	public int code;
	public String msg;
	public List<HaveFamilyResultList> result;

	public class HaveFamilyResultList {
		public String family_id;
		public String family_name;
		public String member_count;
		public String family_number;
		public String thumb;
		public String is_image;
		@Override
		public String toString() {
			return "HaveFamilyResultList [family_id=" + family_id
					+ ", family_name=" + family_name + ", member_count="
					+ member_count + ", family_number=" + family_number
					+ ", thumb=" + thumb + ", is_image=" + is_image + "]";
		}



	}

	@Override
	public String toString() {
		return "HaveFamily [code=" + code + ", msg=" + msg + ", result="
				+ result + "]";
	}

}
