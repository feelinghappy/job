package com.longcai.medical.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by Administrator on 2016/11/26.
 */
@Table("minerest")
public class MineRest {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    //所有数据
    @Column("str")
    private String str;
    //总时间
    @Column("resttime")
    private String time;
    @Column("hour")
    //小时
    private String hour;
    @Column("minute")
    //分钟
    private String minute;
    //是否开启
    @Column("restboolean")
    private boolean restboolean;
    //话语
    @Column("title")
    private String title;
    //标签
    @Column("content")
    private String Content;
    //星期
    @Column("week")
    private String week;
    //振动还是闹铃
    @Column("type")
    private String type;
    //flag
    @Column("flag")
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRestboolean() {
        return restboolean;
    }

    public void setRestboolean(boolean restboolean) {
        this.restboolean = restboolean;
    }
}
