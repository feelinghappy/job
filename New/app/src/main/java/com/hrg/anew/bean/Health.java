package com.hrg.anew.bean;

import java.io.Serializable;

public class Health implements Serializable
{
    public int member_sex;
    public int member_age;
    public double member_height;//时间戳
    public double member_weight;//认证码
    public Health(int member_sex,int member_age,double member_height,double member_weight)
    {
        this.member_sex = member_sex;
        this.member_age = member_age;
        this.member_height = member_height;
        this.member_weight = member_weight;
    }
    public int GetMember_sex()
    {
        return member_sex;
    }
    public int  GetMember_age()
    {
        return member_age;
    }
    public double GetMember_height()
    {
        return member_height;
    }
    public double GetMember_weight()
    {
        return member_weight;
    }

    public void setMember_sex(int member_sex) {
        this.member_sex = member_sex;
    }

    public void setMember_age(int member_age) {
        this.member_age = member_age;
    }

    public void setMember_height(double member_height) {
        this.member_height = member_height;
    }

    public void setMember_weight(double member_weight) {
        this.member_weight = member_weight;
    }
}