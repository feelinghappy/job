package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class TeamDetail {

    /**
     * seller_name : 舒婷
     * total_amount : 0
     * identify : 兼职
     * team_num : 1
     * invite_code : obuRr2eC
     * team_member_info : [{"member_id":18,"seller_name":"fff"}]
     */

    private String seller_name;
    private int total_amount;
    private String identify;
    private int team_num;
    private String invite_code;
    private List<TeamMemberInfoBean> team_member_info;

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public int getTeam_num() {
        return team_num;
    }

    public void setTeam_num(int team_num) {
        this.team_num = team_num;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public List<TeamMemberInfoBean> getTeam_member_info() {
        return team_member_info;
    }

    public void setTeam_member_info(List<TeamMemberInfoBean> team_member_info) {
        this.team_member_info = team_member_info;
    }

    public static class TeamMemberInfoBean {

        /**
         * member_id : 18
         * seller_name : fff
         * order_amount : 0
         */

        private int member_id;
        private String seller_name;
        private int order_amount;

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public String getSeller_name() {
            return seller_name;
        }

        public void setSeller_name(String seller_name) {
            this.seller_name = seller_name;
        }

        public int getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(int order_amount) {
            this.order_amount = order_amount;
        }
    }
}

