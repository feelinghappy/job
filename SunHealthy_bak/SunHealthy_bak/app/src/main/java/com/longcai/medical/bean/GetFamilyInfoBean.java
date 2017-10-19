package com.longcai.medical.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class GetFamilyInfoBean {
    /* {"code":"200","msg":"success","result":{"family_info":{"family_id":"2","family_name":"\u4e00\u4e2a\u5bb6\u5ead","family_number":"000002","robot_imei":"","is_mange":"1"
    ,"affix":{"file_path":"2","is_image":0}},"member_list":[{"member_info":{"family_member_id":"3","member_name":"1122","member_avatar":"http:\/\/sun.healthywo.com\/uploads\/avatar\/59891fc265dbd.png","is_mange":1}
    ,"health_info":{"member_sex":1,"member_age":88,"member_height":158,"member_weight":55}}]}}*/

    /**
     * family_info : {"family_id":63,"family_name":"哈哈哈","family_number":"000063","robot_imei":"","is_mange":"0","affix":{"file_path":"2","is_image":"0"}}
     * member_list : [{"member_info":{"family_member_id":"208","nickname":"yang188","avatar":"","is_mange":1},"health_info":{"member_sex":"1","member_age":"30","member_height":"0","member_weight":"0","physique_type":"平和质"}},{"member_info":{"family_member_id":"207","nickname":"jiang133","avatar":"","is_mange":0},"health_info":{"member_sex":"1","member_age":"29","member_height":"0","member_weight":"0","physique_type":null}}]
     */

    private FamilyInfoBean family_info;
    private List<MemberListBean> member_list;

    public FamilyInfoBean getFamily_info() {
        return family_info;
    }

    public void setFamily_info(FamilyInfoBean family_info) {
        this.family_info = family_info;
    }

    public List<MemberListBean> getMember_list() {
        return member_list;
    }

    public void setMember_list(List<MemberListBean> member_list) {
        this.member_list = member_list;
    }

    public static class FamilyInfoBean {
        /**
         * family_id : 63
         * family_name : 哈哈哈
         * family_number : 000063
         * robot_imei :
         * is_mange : 0
         * affix : {"file_path":"2","is_image":"0"}
         */

        private int family_id;
        private String family_name;
        private String member_count;
        private String family_number;
        private String robot_imei;
        private String is_mange;
        private AffixBean affix;

        public int getFamily_id() {
            return family_id;
        }

        public void setFamily_id(int family_id) {
            this.family_id = family_id;
        }

        public String getFamily_name() {
            return family_name;
        }

        public void setFamily_name(String family_name) {
            this.family_name = family_name;
        }

        public String getMember_count() {
            return member_count;
        }

        public void setMember_count(String member_count) {
            this.member_count = member_count;
        }

        public String getFamily_number() {
            return family_number;
        }

        public void setFamily_number(String family_number) {
            this.family_number = family_number;
        }

        public String getRobot_imei() {
            return robot_imei;
        }

        public void setRobot_imei(String robot_imei) {
            this.robot_imei = robot_imei;
        }

        public String getIs_mange() {
            return is_mange;
        }

        public void setIs_mange(String is_mange) {
            this.is_mange = is_mange;
        }

        public AffixBean getAffix() {
            return affix;
        }

        public void setAffix(AffixBean affix) {
            this.affix = affix;
        }

        public static class AffixBean {
            /**
             * file_path : 2
             * is_image : 0
             */

            private String file_path;
            private String is_image;

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getIs_image() {
                return is_image;
            }

            public void setIs_image(String is_image) {
                this.is_image = is_image;
            }
        }
    }

    public static class MemberListBean implements Serializable {
        private static final long serialVersionUID = -7667965938312399967L;
        /**
         * member_info : {"family_member_id":"208","nickname":"yang188","avatar":"","is_mange":1}
         * health_info : {"member_sex":"1","member_age":"30","member_height":"0","member_weight":"0","physique_type":"平和质"}
         */

        private MemberInfoBean member_info;
        private HealthInfoBean health_info;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public MemberInfoBean getMember_info() {
            return member_info;
        }

        public void setMember_info(MemberInfoBean member_info) {
            this.member_info = member_info;
        }

        public HealthInfoBean getHealth_info() {
            return health_info;
        }

        public void setHealth_info(HealthInfoBean health_info) {
            this.health_info = health_info;
        }

        public static class MemberInfoBean {
            /*"member_list":[{"member_info":{"family_member_id":"3","member_name":"1122","member_avatar":"http:\/\/sun.healthywo.com\/uploads\/avatar\/59891fc265dbd.png","is_mange":1}*/
            /**
             * family_member_id : 208
             * nickname : yang188
             * avatar :
             * is_mange : 1
             */

            private String family_member_id;
            private String member_name;
            private String member_avatar;
            private int is_mange;
            private int is_member;// 1 yes

            public String getFamily_member_id() {
                return family_member_id;
            }

            public void setFamily_member_id(String family_member_id) {
                this.family_member_id = family_member_id;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getMember_avatar() {
                return member_avatar;
            }

            public void setMember_avatar(String member_avatar) {
                this.member_avatar = member_avatar;
            }

            public int getIs_mange() {
                return is_mange;
            }

            public void setIs_mange(int is_mange) {
                this.is_mange = is_mange;
            }

            public int getIs_member() {
                return is_member;
            }

            public void setIs_member(int is_member) {
                this.is_member = is_member;
            }
        }

        public static class HealthInfoBean {
            /*"health_info":{"member_sex":1,"member_age":88,"member_height":158,"member_weight":55}*/
            /**
             * member_sex : 1
             * member_age : 30
             * member_height : 0
             * member_weight : 0
             * physique_type : 平和质
             */

            private String member_sex;
            private String member_age;
            private String member_height;
            private String member_weight;
            private String physique_type;

            public String getMember_sex() {
                return member_sex;
            }

            public void setMember_sex(String member_sex) {
                this.member_sex = member_sex;
            }

            public String getMember_age() {
                return member_age;
            }

            public void setMember_age(String member_age) {
                this.member_age = member_age;
            }

            public String getMember_height() {
                return member_height;
            }

            public void setMember_height(String member_height) {
                this.member_height = member_height;
            }

            public String getMember_weight() {
                return member_weight;
            }

            public void setMember_weight(String member_weight) {
                this.member_weight = member_weight;
            }

            public String getPhysique_type() {
                return physique_type;
            }

            public void setPhysique_type(String physique_type) {
                this.physique_type = physique_type;
            }
        }
    }
}
