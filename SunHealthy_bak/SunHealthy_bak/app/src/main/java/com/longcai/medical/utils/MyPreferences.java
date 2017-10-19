package com.longcai.medical.utils;


import android.content.Context;

import com.longcai.medical.utils.helper.AppPreferences;

/**
 * Created by Administrator on 11/18 0018.
 */
public class MyPreferences extends AppPreferences {

    public MyPreferences(Context context, String name) {

        super(context, name);

    }

    public void saveSystolic(String systolic) {
        putString("systolic", systolic);
    }
    public String readSystolic() {
        return getString("systolic", "0");
    }
    public void saveDiastolic(String diastolic) {
        putString("diastolic", diastolic);
    }
    public String readDiastolic() {
        return getString("diastolic", "0");
    }
    public void saveStep_num(String step_num) {
        putString("step_num", step_num);
    }
    public String readStep_num() {
        return getString("step_num", "0");
    }
    public void saveSleep_time(String sleep_time) {
        putString("sleep_time", sleep_time);
    }
    public String readSleep_time() {
        return getString("sleep_time", "0");
    }
    public void saveSleep_grade(String sleep_grade) {
        putString("sleep_grade", sleep_grade);
    }
    public String readSleep_grade() {
        return getString("sleep_grade", "5");
    }
    public void saveMin_heart(String min_heart) {
        putString("min_heart", min_heart);
    }
    public String readMin_heart() {
        return getString("min_heart", "0");
    }
    public void saveAvg_heart(String avg_heart) {
        putString("avg_heart", avg_heart);
    }
    public String readAvg_heart() {
        return getString("avg_heart", "0");
    }
    public void saveMax_heart(String max_heart) {
        putString("max_heart", max_heart);
    }
    public String readMax_heart() {
        return getString("max_heart", "0");
    }
    //删除
    public void saveUID(String UID) {
        putString("UID", UID);
    }
    public String readUID() {
        return getString("UID", "-1");
    }
    public void saveToken(String token) {

        putString("Token", token);

    }

    public String readToken() {

        return getString("Token", "-1");

    }
    public void saveUnpayOrder(String state) {

        putString("UnpayOrder", state);

    }

    public String readUnpayOrder() {

        return getString("UnpayOrder", "-1");

    }
    public void saveApplySaler(String state) {

        putString("ApplySaler", state);

    }

    public String readApplySaler() {

        return getString("ApplySaler", "-1");

    }
    //家庭消息是否已加入
    public void saveAgree(boolean isAgree){
        putBoolean("IsAgree",isAgree);
    }
    public boolean readAgree(){
        return getBoolean("IsAgree",false);
    }
    public void saveHavaWatch(boolean result) {
        putBoolean("HavaWatch", result);
    }
    public boolean readHavaWatch() {
        return getBoolean("HavaWatch", false);
    }
    public void saveStep(String Step) {

        putString("Step", Step);

    }

    public String readStep() {

        return getString("Step", "1000");

    }

    public void saveGrade(String grade) {

        putString("grade", grade);

    }

    public String readGrade() {

        return getString("grade", "-1");

    }

    public void saveTJ(String tj) {

        putString("tj", tj);

    }

    public String readTJ() {

        return getString("tj", "-1");

    }

    public void saveUserName(String UserName) {

        putString("UserName", UserName);

    }

    public String readUserName() {

        return getString("UserName", "-1");

    }

    public void savePhone(String phone) {

        putString("PHONE", phone);

    }

    public String readPhone() {

        return getString("PHONE", "-1");

    }

    public void saveavatar(String avatar) {

        putString("avatar", avatar);
    }

    public String readavatar() {
        return getString("avatar", "-1");
    }

    public void saveFristHeadImg(String img) {
        putString("IMG", img);
    }

    public String readFristHeadImg() {
        return getString("IMG", "");
    }

    //保存定位城市
    public void saveLocationCity(String locationCity) {
        putString("LCITY", locationCity);
    }

    public String readLocationCity() {
        return getString("LCITY", "太原市");
    }

    //保存当前选择城市
    public void saveCity(String City) {
        putString("City", City);
    }

    public String readCity() {
        return getString("City", "太原市");
    }

    //昵称
    public void saveNickName(String NickName) {
        putString("NickName", NickName);
    }

    public String readNickName() {
        return getString("NickName", "");
    }

    //昵称
    public void saveInviteCode(String InviteCode) {
        putString("InviteCode", InviteCode);
    }

    public String readInviteCode() {
        return getString("InviteCode", "");
    }
    //二期  体制测试的值 ---------------------


    public void saveIs_Tz(String isTz) {
        putString("isTz", isTz);
    }

    public String readIs_Tz() {
        return getString("isTz", "");
    }

    public void savePhysica(String physica) {
        putString("physica", physica);
    }

    public String readPhysica() {
        return getString("physica", "");
    }

    public void saveSex(String sex) {
        putString("sex", sex);
    }

    public String readSex() {
        return getString("sex", "");
    }

    public void saveAge(String age) {
        putString("age", age);
    }

    public String readAge() {
        return getString("age", "");
    }

    public void saveProvince(String province) {
        putString("province", province);
    }

    public void saveProvinceId(String provinceId) {
        putString("provinceId", provinceId);
    }

    public String readProvinceId() {
        return getString("provinceId", "");
    }

    public String readProvince() {
        return getString("province", "");
    }

    public void saveShi(String shi) {
        putString("shi", shi);
    }

    public String readShi() {
        return getString("shi", "");
    }

    public void saveShiId(String shiId) {
        putString("shiId", shiId);
    }

    public String readShiId() {
        return getString("shiId", "");
    }


    public void saveXian(String xian) {
        putString("xian", xian);
    }

    public String readXian() {
        return getString("xian", "");
    }

    public void saveXianId(String xianId) {
        putString("xianId", xianId);
    }

    public String readXianId() {
        return getString("xianId", "");
    }

    public void saveHeight(String height) {
        putString("height", height);
    }

    public String readHeight() {
        return getString("height", "");
    }

    public void saveWeight(String weight) {
        putString("weight", weight);
    }

    public String readWeight() {
        return getString("weight", "");
    }

    public void saveSelectPosition(String pos) {
        putString("pos", pos);
    }

    public String readSelectPosition() {
        return getString("pos", "0");
    }
    public void saveKeFuPhone(String phone) {
        putString("phone", phone);
    }

    public String readKeFuPhone() {
        return getString("phone", "0");
    }
    /*
    * 会员角色*/
    public void saveRole(String role) {
        putString("role", role);
    }

    public String readRole() {
        return getString("role", "-1");
    }
}
