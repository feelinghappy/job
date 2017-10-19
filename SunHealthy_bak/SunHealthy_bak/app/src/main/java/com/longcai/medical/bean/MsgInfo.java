package com.longcai.medical.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class MsgInfo implements Serializable{

    private List<MsgInfoBean> family_info;
    private List<MsgInfoBean> system_info;

    public List<MsgInfoBean> getFamily_info() {
        return family_info;
    }

    public void setFamily_info(List<MsgInfoBean> family_info) {
        this.family_info = family_info;
    }

    public List<MsgInfoBean> getSystem_info() {
        return system_info;
    }

    public void setSystem_info(List<MsgInfoBean> system_info) {
        this.system_info = system_info;
    }

    public static class MsgInfoBean implements Parcelable {
        /**
         * message_id : 4
         * message_type : 2
         * message_title : 会员邀请消息
         * is_invite : 1
         * notice : 0
         */

        private String message_id;
        private String message_type;
        private String message_title;
        private int is_invite;
        private String notice;

        public String getMessage_id() {
            return message_id;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        public int getIs_invite() {
            return is_invite;
        }

        public void setIs_invite(int is_invite) {
            this.is_invite = is_invite;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.message_id);
            dest.writeString(this.message_type);
            dest.writeString(this.message_title);
            dest.writeInt(this.is_invite);
            dest.writeString(this.notice);
        }

        public MsgInfoBean() {
        }

        protected MsgInfoBean(Parcel in) {
            this.message_id = in.readString();
            this.message_type = in.readString();
            this.message_title = in.readString();
            this.is_invite = in.readInt();
            this.notice = in.readString();
        }

        public static final Parcelable.Creator<MsgInfoBean> CREATOR = new Parcelable.Creator<MsgInfoBean>() {
            @Override
            public MsgInfoBean createFromParcel(Parcel source) {
                return new MsgInfoBean(source);
            }

            @Override
            public MsgInfoBean[] newArray(int size) {
                return new MsgInfoBean[size];
            }
        };
    }
}
