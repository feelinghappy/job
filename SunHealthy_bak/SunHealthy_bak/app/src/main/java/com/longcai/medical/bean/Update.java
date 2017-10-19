package com.longcai.medical.bean;

public class Update {
/*{
    "code": -2,
    "msg": "当前版本为最新版本",
    "result": ""
}*/
/*{
     "code" : "1",
     "msg" : "正常获取",
     "result" : {
          "version" : "1", //版本
          "version_code" : "1.0.1",//版本号
          "download_url" : "http://www.chang.com/index.php/Console/version/add.html" //应用下载地址
     },
          "is_compulsion":"1",   //1:强制更新 0:非强制
 }
 }*/

    public String code;
    public String msg;
    public String is_compulsion;
    public UpdateResult result;


    public class UpdateResult {
        public String version;
        public String version_code;
        public String download_url;

        @Override
        public String toString() {
            return "UpdateResult{" +
                    "version='" + version + '\'' +
                    ", version_code='" + version_code + '\'' +
                    ", download_url='" + download_url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Update{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", is_compulsion='" + is_compulsion + '\'' +
                ", result=" + result +
                '}';
    }
}
