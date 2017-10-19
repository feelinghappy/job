package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by apple1 on 2017/5/21.
 */

public class SearchMemberBean implements Serializable {

    /**
     * code : 1
     * msg : 正常获取
     * result : {"member_id":"29DDE30B1E90AB027FC72EDF4631F036","avatar":"http://v2.healthywo.com/Public/Avatar/29DDE30B1E90AB027FC72EDF4631F036586b06c17d0a1.jpg","nickname":"测试","mobile":"18234049083"}
     * member_id : 29DDE30B1E90AB027FC72EDF4631F036
     * avatar : http://v2.healthywo.com/Public/Avatar/29DDE30B1E90AB027FC72EDF4631F036586b06c17d0a1.jpg
     * nickname : 测试
     * mobile : 18234049083
     */

    private String member_id;
    private String member_avatar;
    private String member_name;
    private String mobile;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getAvatar() {
        return member_avatar;
    }

    public void setAvatar(String avatar) {
        this.member_avatar = avatar;
    }

    public String getNickname() {
        return member_name;
    }

    public void setNickname(String nickname) {
        this.member_name = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
