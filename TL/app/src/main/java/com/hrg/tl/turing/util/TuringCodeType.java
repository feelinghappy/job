package com.hrg.tl.turing.util;

public enum TuringCodeType {

	NORMAL_TEXT(100000, "文本类"), // 文本类
	NORMAL_LINK(200000, "链接类"), // 链接类
	NORMAL_NEWS(302000, "新闻类"), // 新闻类
	NORMAL_COOK(308000, "菜谱类"), // 菜谱类
	NORMAL_CHILD_SONG(313000, "(儿童版)儿歌类"), // （儿童版） 儿歌类
	NORMAL_CHILD_POEM(314000, "(儿童版)诗词类"), // （儿童版）诗词类

	ERROR_KEY(40001, "参数key错误"), // 参数key错误
	ERROR_INFO(40002, "请求内容info为空"), // 请求内容info为空
	ERROR_TOO_MANY_TIMES(40004, "当天请求次数已使用完"), // 当天请求次数已使用完
	ERROR_DATA_TYPE_EXCEPTION(40007, "数据格式异常");// 数据格式异常

	private int mCode;
	private String mText;

	private TuringCodeType(int _code, String _text) {
		this.mCode = _code;
		this.mText = _text;
	}

	public int getValueCode() {
		return mCode;
	}

	public String getValueText() {
		return mText;
	}

	public static TuringCodeType valueOfInt(int value) {
		TuringCodeType turingCodeType = ERROR_DATA_TYPE_EXCEPTION;
		switch (value) {
		case 100000:
			turingCodeType = NORMAL_TEXT;
			break;
		case 200000:
			turingCodeType = NORMAL_LINK;
			break;
		case 302000:
			turingCodeType = NORMAL_NEWS;
			break;
		case 308000:
			turingCodeType = NORMAL_COOK;
			break;
		case 313000:
			turingCodeType = NORMAL_CHILD_SONG;
			break;
		case 314000:
			turingCodeType = NORMAL_CHILD_POEM;
			break;
		case 40001:
			turingCodeType = ERROR_KEY;
			break;
		case 40002:
			turingCodeType = ERROR_INFO;
			break;
		case 40004:
			turingCodeType = ERROR_TOO_MANY_TIMES;
			break;
		case 40007:
			turingCodeType = ERROR_DATA_TYPE_EXCEPTION;
			break;
		default:
			turingCodeType = ERROR_DATA_TYPE_EXCEPTION;
			break;
		}
		return turingCodeType;
	}
}
