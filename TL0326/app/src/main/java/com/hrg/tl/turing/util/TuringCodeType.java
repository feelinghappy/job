package com.hrg.tl.turing.util;

public enum TuringCodeType {

	NORMAL_TEXT(100000, "�ı���"), // �ı���
	NORMAL_LINK(200000, "������"), // ������
	NORMAL_NEWS(302000, "������"), // ������
	NORMAL_COOK(308000, "������"), // ������
	NORMAL_CHILD_SONG(313000, "(��ͯ��)������"), // ����ͯ�棩 ������
	NORMAL_CHILD_POEM(314000, "(��ͯ��)ʫ����"), // ����ͯ�棩ʫ����

	ERROR_KEY(40001, "����key����"), // ����key����
	ERROR_INFO(40002, "��������infoΪ��"), // ��������infoΪ��
	ERROR_TOO_MANY_TIMES(40004, "�������������ʹ����"), // �������������ʹ����
	ERROR_DATA_TYPE_EXCEPTION(40007, "���ݸ�ʽ�쳣");// ���ݸ�ʽ�쳣

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
