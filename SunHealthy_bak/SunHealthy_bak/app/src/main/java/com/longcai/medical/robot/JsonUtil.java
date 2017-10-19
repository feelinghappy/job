package com.longcai.medical.robot;

import com.longcai.medical.bean.QuestionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class JsonUtil {

    private static ArrayList<QuestionInfo> list;
    private static ArrayList<String> picpath;
    private static JSONObject jo;
    private static JSONArray jaPic;
    private static ArrayList<HashMap<String, String>> list1;
    private static HashMap<String, String> picMap;

    public static ArrayList<QuestionInfo> getData(String json) {
        //{"y":[{"a":"����ʡ��Ǩ�����������Ǵ��?","id":1,"is":1,"r":{"j":"http://www.laodao.co/static/upload/image/2015/6/13/82347394759776649.jpg"},"t":"����ʲô�棿"},{"a":"ɽ��ʡ�ĳ���","id":2,"is":1,"r":{"j":"http://att.191.cn/attachment/Mon_0708/63_6751_31f316a039d8871.jpg"},"t":"���Ϸ���ʲôҩ�����Ƹ��������?"}]}
        try {
            list = new ArrayList<QuestionInfo>();
            //Map<String,ArrayList<String>> map=new HashMap<String, ArrayList<String>>();

            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("y");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jo = array.getJSONObject(i);
                QuestionInfo info = new QuestionInfo();
                info.setId(jo.getInt("id"));
                info.setText(jo.getString("t"));
                info.setPubAddress((jo.getString("a")));
                //������Դ
                JSONObject path = jo.getJSONObject("r");
                picpath = new ArrayList<String>();
                for (int j = 0; j < path.length(); j++) {
                    picpath.add(path.getString(j + ""));
                }
                info.setPicpath(picpath);

                info.setIsWarn(jo.getInt("is"));
                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    public static List<HashMap<String, String>> JsonToMap1(String str) {
        try {
            jo = new JSONObject(str);
            jaPic = new JSONArray();
            list1 = new ArrayList<HashMap<String, String>>();
            jaPic = jo.getJSONArray("pic");
            for (int i = 0; i < jaPic.length(); i++) {
                picMap = new HashMap<String, String>();
                JSONObject object = (JSONObject) jaPic.get(i);
                String name = (String) object.get("name");
                String url = object.getString("url");
                picMap.put(name, url);
                list1.add(picMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list1;
    }


    /**
     * json���ݽ���
     *
     * @param json
     * @return
     */
    public static String parseIatResult(String json) {
        // {"sn":1,"ls":true,"bg":0,"ed":0,
        // "ws":[
        // {"bg":0,"cw":[{"w":"����","sc":0}]},
        // {"bg":0,"cw":{"w":"��","sc":0}]},
        // {"bg":0,"cw":[{"w":"����","sc":0}]},
        // {"bg":0,"cw":[{"w":"��ô��","sc":0}]},
        // {"bg":0,"cw":[{"w":"��","sc":0}]}
        // ]}
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // תд����ʣ�Ĭ��ʹ�õ�һ�����
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
                // �����Ҫ���ѡ������������������ֶ�
                // for(int j = 0; j < items.length(); j++)
                // {
                // JSONObject obj = items.getJSONObject(j);
                // ret.append(obj.getString("w"));
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    public static String parseGrammarResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                for (int j = 0; j < items.length(); j++) {
                    JSONObject obj = items.getJSONObject(j);
                    if (obj.getString("w").contains("nomatch")) {
                        ret.append("û��ƥ����.");
                        return ret.toString();
                    }
                    ret.append(obj.getString("w"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.append("û��ƥ����.");
        }
        return ret.toString();
    }
}
