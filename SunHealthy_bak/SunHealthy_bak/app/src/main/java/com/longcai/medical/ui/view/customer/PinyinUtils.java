package com.longcai.medical.ui.view.customer;

import com.longcai.medical.bean.PersonBean;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

public class PinyinUtils {
    /**
     * 汉字转拼音
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = inputString.trim().toCharArray();
        String output = "";
        try {
            for (char curchar : input) {
                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(curchar);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }
    /*
    * 得到集合每个字符串的拼音和首字母
    * */
    public static List<PersonBean> getData(List<PersonBean> data) {
        List<PersonBean> listarray = new ArrayList<PersonBean>();
        for (int i = 0; i < data.size(); i++) {
            String name = data.get(i).getCustomer_name();
            String pinyin = PinyinUtils.getPingYin(name);
            String Fpinyin = pinyin.substring(0, 1).toUpperCase();
            PersonBean person = new PersonBean();
            person.setCustomer_name(name);
            person.setPinYin(pinyin);
            // 正则表达式，判断首字母是否是英文字母
            if (Fpinyin.matches("[A-Z]")) {
                person.setFirstPinYin(Fpinyin);
            } else {
                person.setFirstPinYin("#");
            }
            listarray.add(person);
        }
        return listarray;
    }
}