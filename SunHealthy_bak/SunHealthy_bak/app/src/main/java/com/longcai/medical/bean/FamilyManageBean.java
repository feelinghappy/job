package com.longcai.medical.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class FamilyManageBean {
/*{"code":"200","msg":"success","result":{"affix_list":[{"affix_id":2,"file_path":"2","is_image":0,"is_use":1}],"family_name":"\u4e00\u4e2a\u5bb6\u5ead","robot_imei":"","member_list":[{"family_member_id":2,"is_member":1,"member_name":"1122","member_avatar":"http:\/\/sun.healthywo.com\/uploads\/avatar\/59891fc265dbd.png"}]}}*/
    /**
     * code : 1
     * msg : 获取成功
     * result : {"affix_list":[{"affix_id":"82","file_path":"http://v2.healthywo.com/Public/family/5932d92deb487.jpg","is_image":"1","is_use":"0"},{"affix_id":"59","file_path":"http://v2.healthywo.com/Public/family/5932a70777a57.jpg","is_image":"1","is_use":"0"},{"affix_id":"58","file_path":"http://v2.healthywo.com/Public/family/5932a6fe93cfb.jpg","is_image":"1","is_use":"0"},{"affix_id":"57","file_path":"http://v2.healthywo.com/Public/family/5932a6d11a82f.jpg","is_image":"1","is_use":"0"},{"affix_id":"54","file_path":"http://v2.healthywo.com/Public/family/5930268aa61f2.jpg","is_image":"1","is_use":"0"},{"affix_id":"1","file_path":"2","is_image":"0","is_use":"1"}],"family_name":"家庭名称2","robot_imei":"","member_list":[{"family_member_id":"1","is_member":"1","nickname":"测试","avatar":"http://v2.healthywo.com/Avatar/29DDE30B1E90AB027FC72EDF4631F036586b06c17d0a1.jpg"},{"family_member_id":"13","is_member":"0","nickname":"hahah","avatar":""},{"family_member_id":"14","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"15","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"16","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"17","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"18","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"19","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"20","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"21","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"22","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"24","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"23","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"25","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"26","is_member":"0","nickname":"ed_name.getText().toString().trim()","avatar":""},{"family_member_id":"27","is_member":"0","nickname":"1","avatar":""},{"family_member_id":"33","is_member":"0","nickname":"拼音","avatar":""},{"family_member_id":"44","is_member":"0","nickname":"测试","avatar":""},{"family_member_id":"45","is_member":"0","nickname":"111","avatar":""},{"family_member_id":"66","is_member":"0","nickname":"测试63","avatar":""},{"family_member_id":"67","is_member":"0","nickname":"789","avatar":""},{"family_member_id":"68","is_member":"0","nickname":"萌萌","avatar":""}]}
     */
    private String family_name;
    private String robot_imei;
    private List<AffixListBean> affix_list;
    private List<MemberListBean> member_list;

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getRobot_imei() {
        return robot_imei;
    }

    public void setRobot_imei(String robot_imei) {
        this.robot_imei = robot_imei;
    }

    public List<AffixListBean> getAffix_list() {
        return affix_list;
    }

    public void setAffix_list(List<AffixListBean> affix_list) {
        this.affix_list = affix_list;
    }

    public List<MemberListBean> getMember_list() {
        return member_list;
    }

    public void setMember_list(List<MemberListBean> member_list) {
        this.member_list = member_list;
    }

    public static class AffixListBean {
        /**
         * affix_id : 82
         * file_path : http://v2.healthywo.com/Public/family/5932d92deb487.jpg
         * is_image : 1
         * is_use : 0
         */

        private String affix_id;
        private String file_path;
        private String is_image;
        private String is_use;

        public String getAffix_id() {
            return affix_id;
        }

        public void setAffix_id(String affix_id) {
            this.affix_id = affix_id;
        }

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

        public String getIs_use() {
            return is_use;
        }

        public void setIs_use(String is_use) {
            this.is_use = is_use;
        }
    }

    public static class MemberListBean implements Serializable {
        /**
         * family_member_id : 1
         * is_member : 1
         * nickname : 测试
         * avatar : http://v2.healthywo.com/Avatar/29DDE30B1E90AB027FC72EDF4631F036586b06c17d0a1.jpg
         */

        private String family_member_id;
        private String is_member;
        private String member_name;
        private String member_avatar;

        public String getFamily_member_id() {
            return family_member_id;
        }

        public void setFamily_member_id(String family_member_id) {
            this.family_member_id = family_member_id;
        }

        public String getIs_member() {
            return is_member;
        }

        public void setIs_member(String is_member) {
            this.is_member = is_member;
        }

        public String getNickname() {
            return member_name;
        }

        public void setNickname(String nickname) {
            this.member_name = nickname;
        }

        public String getAvatar() {
            return member_avatar;
        }

        public void setAvatar(String avatar) {
            this.member_avatar = avatar;
        }
    }
}
