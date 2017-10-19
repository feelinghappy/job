package com.longcai.medical.gloabe;

public class Constant {
    public static final String SINA_APP_KEY      = "";
    public static final String SINA_REDIRECT_URL = "";
//    public static final String SINA_REDIRECT_URL = "http://www.sina.com";
    public static final String SINA_SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    public static final String QQ_APP_ID = "";
    /**
     * 获取qq哪些信息
     * all获取所有
     * get_user_info访问基础资料
     * add_t分享内容到微博
     */
    public static final String QQ_Scope = "get_user_info";
    //微信APPID
    public static final String WX_APP_ID = "";
    //微信scr
    public static final String WX_Secret = "";

    /*
   * 测试体质返回的结果 创建家庭成员
   * */
    public static String STAMINA_PHYSICAL_CREATEMEM = "-1";
    /*
    * MainActivity机器人请求码
    * */
    public static final int ROBOT_REQUEST_CODE = 1;
    /*添加手表扫码*/
    public static final int whatch_REQUEST_CODE = 11;
    public static final int whatchReplace_REQUEST_CODE = 12;
    public static final int device_REQUEST_CODE = 13;
    /*结果码  start*/
    public static final int ROBOT_REsult_CODE = 100;
    public static final int Create_family_CODE = 101;
    public static final int Family_List_To_Message_CODE = 106;
    public static final int Manager_family_CODE = 1020;
    public static final int Manager_family_Replace_CODE = 1021;
    public static final int Manager_family_Intent_CODE = 1022;
    public static final int Whatch_Create_family_Replace_CODE = 1030;
    public static final int Whatch_Manager_family_Replace_CODE = 1031;
    public static final int WhatchBudlingSuccessToFamDet_CODE = 104;
    public static final int WhatchBudlingSuccessToManaDet_CODE = 105;
    public static final int WhatchBudlingSuccessToHistory_CODE = 107;
    public static final int WhatchBudlingSuccessToInput_CODE = 106;

    /*结果码  标示 gloabFirstActivity中的robotfg中的手表绑定*/
    public static final int GF_ROBOT_Fg_REsult_CODE_watchScam_suc = 1011;
    public static final int GF_ROBOT_Fg_REsult_CODE_watchScam_fail = 1012;
    public static final int GF_ROBOT_Fg_REsult_CODE_watchScam_unBuild_suc = 1013;
    public static final int GF_ROBOT_Fg_REsult_CODE_watchScam_unBuild_fail = 1014;

    public static final int GF_ROBOT_Fg_REsult_CODE_robotScam_suc = 1021;
    public static final int GF_ROBOT_Fg_REsult_CODE_robotScam_fail = 1022;
    public static final int GF_ROBOT_Fg_REsult_CODE_robotScam_unBuild_suc = 1023;
    public static final int GF_ROBOT_Fg_REsult_CODE_robotScam_unBuild_fail = 1024;
   /*结果码  end*/
    /**
     * 添加fragment标示
     */
    public static final String FRAGMENT_MARK = "fragment_mark";
    /*登录后给mainActivity返回的标示*/
    public static final String LoginAfterToMainMark = "login";
    /*登录后返回登录成功的标示*/
    public static final String LoginSuccess = "success";
    /*fragment 标签*/
    public static String fragmentTag = "";
    public static String FamilyFgTag = "FamilyFg";
    public static String RobotFgTag = "RobotFg";
    public static String MessageFgTag = "MessageFg";
    /*扫描到的手表编码*/
    public static String WatchImei_ScannerResult = "0";
    public static boolean LoginMark = false;//标示用户是否登录
    public static boolean CreateFamilyHaveScaner = false;//创建家庭是否是已经扫完码跳转。
    public static boolean LoginOrRegisterSucToMainFir = false;//标示登录、注册成功后，mainActivity跳转到首页
    public static boolean LoginOrRegisterSucToMainFam = false;//标示登录、注册成功后，mainActivity跳转到首页
    /*
    用户手机号
    */
    // public static String PHONE_NUMBER = "13311023455";
    public static String PHONE_NUMBER = "0";
    public static boolean ifRobotLogin = false;
    public static boolean isMessageHandle = false;
    public static boolean isRobotBudling = false; //判断机器人是否绑定成功，用于返回RobotFg重新获取数据
    public static boolean isHaveInfo = false; //判断是否从个人资料进入编辑会员
    /**
     * 机器人绑定界面
     */
    public static final String ROBOTBINDINGFG = "RobotBindingFg";
    /**
     * 家庭列表界面
     */
    public static final String FAMILY_LIST = "FamilyFg";
    public static final String Watch_Location = "手表定位";

    /**
     * 绑定已有家庭 界面
     */
    public static final String BINDING_HAVE_FAM = "BindingHaveFamFg";

    /**
     * 消息列表界面
     */
    public static final String MESSAGE_LIST = "MessageFg";

    /**
     * 家庭搜索列表界面
     */
    public static final String FAM_SEARCH_LIST = "SearchFg";

    /**
     * 机器人界面
     */
    public static final String ROBOT_FG = "Robot_Fg";

    /**
     * 历史记录界面
     */
    public static final String HISTORY_FG = "HistoryFg";
    /**
     * 设置提醒界面
     */
    public static final String REMIND_FG = "RemindFg";

    /**
     * 设备管理 界面
     */
    public static final String DEVICE_MANAGMENT = "DeviceManagmentFg";
    public static final int zero = 0;
    public static final int one = 1;
    public static final int noOne = -1;
    public static final int two = 2;
    public static final int three = 3;
    public static final int four = 4;
    public static final String NegativeOne = "-1";

    /**
     * 经度
     */
    public static final String LONGITUDE = "longitude";
    public static final String Robit_rid = "rid";
    /**
     * 纬度
     */
    public static final String LATITUDE = "latitude";
    /**
     * 名字
     */
    public static final String NAME = "manor_name";
    /**
     * 会员id
     */
//      public static String UID = "29DDE30B1E90AB027FC72EDF4631F036";
    public static String UID = "543F1CFE2A63AB434D2FB0B2A02DA286";
    /**
     * 家庭id
     */
//    public static final String Family_id = "2";
    public static final String Family_id = "-1";
    public static final String osType = "android";
    /**
     * 家庭成员id
     */
//    public static final String FAMILY_MEMBER_ID = "12";
    public static final String FAMILY_MEMBER_ID = "-1";
    // ZK20C1494818532823
    /**
     * 扫描二维码结果 >>机器人编码
     * <p>
     * 此为全局变量，静态的，只有在扫码成功后赋值，在使用完之后，要再次赋值为0
     */
    public static String RobotId = "0"; //机器人id
    public static String RobotSerial = "0"; //机器人出厂编码
    public static String Robot_imeiResult = "0"; //扫描机器人的结果
    /**
     * 标示手表绑定或者是更换
     */
    public static final String WHATCH_MARK = "whatch_mark";
    /*标示绑定手表由哪个界面跳入*/
    public static final String WHATCH_Budling_From_markF = " WHATCH_Budling_From_markF";
    /*标示绑定手表由机器人界面跳入*/
    public static final String WHATCH_Budling_From_Robot = " WHATCH_Budling_From_Robot";
    public static final String WHATCH_Budling_From_History = " WHATCH_Budling_From_History";
    /*标示绑定手表由创建家庭成员界面跳入*/
    public static final String WHATCH_Budling_From_CreateFamilyMun = " WHATCH_Budling_From_CreateFamily";
    /*标示绑定手表由管理家庭成员界面跳入*/
    public static final String WHATCH_Budling_From_ManagerFamilyMun = " WHATCH_Budling_From_ManagerFamilyMun";
    public static final String WHATCH_Budling_From_FamilyDetailMun = " WHATCH_Budling_From_FamilyDetailMun";
    /**
     * 标示机器人绑定或者是更换
     */
    public static final String DEVICE_MARK = "device_mark";
    public static final String Robot_MARK = "robot_mark";
    public static final String Robot_create_MARK = "Robot_create_MARK";
    public static final String Device_create_MARK = "Device_create_MARK";
    /**
     * 扫描标示
     */
    public static final String SCAN_MARK = "scan_mark";
    /**
     * 标示扫描机器人
     */
    public static final String SCAN_MARK_robot = "robot";
    /**
     * 标示扫描手表
     */
    public static final String SCAN_MARK_watch = "watch";
    public static final String SCAN_MARK_device = "device";
    /**
     * 手表更换
     */
    public static final String WHATCH_REPLACE = "WHATCH_replace";
    /**
     * 手表绑定
     */
    public static final String WHATCH_BUDLING = "WHATCH_budling";


    /**
     * 手表编码标示
     */
    public static final String WHATCH_imei = "WHATCH_imei";
    /**
     * 机器人绑定
     */
    public static final String ROBOT_BUDLING = "ROBOT_budling";
    public static final String DEVICE_BUDLING = "DEVICE_budling";
    /*标示从机器人界面跳入*/
    public static final String ROBOT_Add = "ROBOT_Add";
    /*标示从创建家庭跳入*/
    public static final String ROBOT_Create = "ROBOT_Create";
    /*标示从家庭管理跳入*/
    public static final String ROBOT_manager = "ROBOT_manager";
    public static final String DEVICE_manager = "DEVICE_manager";
    /**
     * 机器人更换
     */
    public static final String ROBOT_REPLACE = "ROBOT_replace";
    /**
     * 机器人编码标示
     */
    public static final String ROBOT_imei = "ROBOT_imei";
    public static String old_ROBOT_imei = "0";
    /**
     * 机器人编码标示
     */
    public static final String ROBOT_Replace_familyId = "ROBOT_Replace_familyId";

    /**
     * 手表编码
     */
    public static final String WHATCH_CODE = "8D3310ACA7F38E";
    //public static final String WHATCH_CODE = "0";
    /**
     * 绑定成功
     */
    public static final String BUDLING_SUCCESS = "绑定成功";

    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVER = 1;
    public static final int REQUEST_SERVER = 2;
    //系统消息界面
    public static final String SYSTEM_MESSAGE = "system_message";
    public static final String FAMILY_MESSAGE = "family_message";
    //消息页面，是否已读 true已读
    public static boolean isReadNotice = false;
//    public static List<MsgSystemInfo> systemInfoList = null;
//    public static List<MsgFamilyInfo> familyInfoList = null;
//    public static List<List<MsgSystemInfo>> msgInfoList = null;
    //判断都是已读
    public static boolean isAllRead = false;
    //读取消息返回
    public static final int RESULT_READ_NOTICE = 2;
    //个人信息 是否修改昵称
    public static boolean isEditName = false;
    public static final int PERSON_NAME = 2;
    //CreateMEM 和 FamillyMem
    public static boolean isCreateMem = false;//true创建家庭成员 false 家庭成员详情
    //仓库管理
    public static final String CHUKU = "chuku";
    public static final String RUKU = "ruku";
    public static final String DIAOHUO = "diaohuo";
    //判断仓库 数据是否已经上传到服务器
    public static boolean Send_Order_ChuKu_Success = false;
    public static boolean Send_Order_Ruku_Success = false;
    public static boolean Send_Order_DiaoHuo_Success = false;
    //判断仓库 出库-收货方式 1.面交 2.物流
    public static int ChuKu_ShouHuo = -1;
    //判断 仓库 是否提交了数据
    public static boolean isCommit = false;
    //仓库 结果码
    public static final int STORE_CODE = 201;
    public static String ROBOT_diaohuo = "robot_diaohuo";
    public static String Address_type = "address_type";
    //入库 sn码
    public static final String SCAN_MARK_Ruku_Sn = "ruku_sn";
    public static final String Channels = "channels";
}
