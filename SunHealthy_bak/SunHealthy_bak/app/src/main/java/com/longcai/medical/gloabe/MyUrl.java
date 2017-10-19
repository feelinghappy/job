package com.longcai.medical.gloabe;

public class MyUrl {
    // 接口请求示例。。。。。。 http://xx.com/index.php/api/family/add_family
   /* {
        "status": 1,
            "message": "登录成功！",
            "data": {
        "uid": "543F1CFE2A63AB434D2FB0B2A02DA286",
                "is_tj": "0",
                "avatar": "http://code.healthywo.com/Public/Console/images/home/2017/06/14984691105950d2f6f1f2a.jpeg",
                "sex": "1",
                "age": "32",
                "province": "",
                "province_id": "140100",
                "city": "",
                "city_id": "140000",
                "area": "",
                "area_id": "",
                "height": "170",
                "weight": "69",
                "nickname": "jiang133",
                "physica": ""
    }
    }*/
    /**
     * 域名
     */
//     private static final String DOMAIN = "http://v2.healthywo.com/";//正式服务器
//    private static final String DOMAIN = "http://code.healthywo.com/";//测试服务器
    private static final String DOMAIN = "http://sun.healthywo.com/";//新版太阳健康测试服务器
//    private static final String DOMAIN = "http://api.healthywo.com/";//新版太阳健康正式服务器

    /**
     * 接口公共地方  http://v2.healthywo.com/index.php/api/
     * http://code.healthywo.com/index.php/api/
     */
//    private static final String START_URL = DOMAIN + "index.php/api/";
    private static final String START_URL = DOMAIN + "api/";
    /**
     * 版本更新
     */
    public static final String Version = START_URL + "Other/version.html";
    //首页获取数据
    public static final String HomeData = START_URL + "Common/infoindex";
    //获取食谱，食材的数据   http://code.healthywo.com/index.php/api/Common/food
    public static final String Food = START_URL + "Common/food";
    //咨询获取标题
    public static final String NewsData = START_URL + "Infomation/infoType.html";
    //生成食谱
    public static final String FoodMenu = START_URL + "Common/search_food";
    //去运动
    public static final String Sport = START_URL + "Motion/recommend.html";
    // 运动列表
    public static final String RecommendList = START_URL + "Motion/recommend_list.html";
    //咨询获取列表数据   http://code.healthywo.com/index.php/api/Infomation/listinfo.html
    public static final String NewsListData = START_URL + "Infomation/listinfo.html";
    /**
     * 活动中心 http://code.healthywo.com/index.php/api/
     */
    public static final String Center = START_URL + "Activity/listacti.html";//咨询获取列表数据   http://code.healthywo.com/index.php/api/Infomation/listinfo.html
    /**
     * 我的活动列表
     */
    public static final String MyActivity = START_URL + "Activity/myactivity.html";
    /**
     * 运动记录
     */
    public static final String SprotRecord = START_URL + "Motion/recommend_jladd.html";

    /**
     * 体质测试题目获取
     */
    public static final String getPhysical = START_URL + "Common/getPhysical.html";

    /**
     * 体质测试保存
     */
    public static final String Physical = START_URL + "User/physical.html";

    /**
     * 已加入家庭列表。。。 http://xx.com/index.php/api/family/family_list
     */
    public static final String FAMILY_LIST = START_URL + "family/family_list";

    /**
     * 消息列表接口。。 http://xx.com/index.php/api/message/get_list
     */
    public static final String MESSAGE_LIST = START_URL + "message/get_list";

    /**
     * 搜索家庭接口。。http://xx.com/index.php/api/family/search_family
     */
    public static final String SEARCH_FAMILY = START_URL
            + "family/search_family";

    /**
     * 申请加入家庭接口。。http://xx.com/index.php/api/family/apply_family
     */

    public static final String APPLY_ADD_FAMILY = START_URL
            + "family/apply_family";

    /**
     * 机器人解绑接口 http://xx.com/index.php/api/device/robot_unbind
     */
    public static final String ROBOT_UNBUNDLING = START_URL
            + "device/robot_unbind";
    /**
     * 机器人绑定接口 ...http://xx.com/index.php/api/device/robot_bind
     */
    public static final String ROBOT_BUNDLING = START_URL + "device/robot_bind";

    /**
     * 用户同意家庭信息接口 http://xx.com/index.php/api/message/join_family
     */
    public static final String MESSAGE_AGREE = START_URL
            + "message/join_family";

    /**
     * 用户家庭信息拒接接口 http://xx.com/index.php/api/message/unjoin_family
     */
    public static final String MESSAGE_REFUSE = START_URL
            + "message/unjoin_family";
    /**
     * 用户获取可操作机器人接口 .... http://xx.com/index.php/api/device/robot_list
     */
    public static final String ROBOT_OPERATION = START_URL
            + "device/robot_list";
    //http://code.healthywo.com/index.php/api/device/robot_list
    /**
     * 用户获取可管理机器人接口 ....http://xx.com/index.php/api/device/mange_list
     */
    public static final String ROBOT_ADMINISTRATION = START_URL
            + "device/mange_list";
    /**
     * 手表列表.....http://xx.com/index.php/api/device/watch_list
     */
    public static final String WHATCH_LIST = START_URL + "device/watch_list";
    /**
     * 用户解除手表绑定接口....http://xx.com/index.php/api/device/watch_unbind
     */
    public static final String WHATCH_UNBUDLING = START_URL
            + "device/watch_unbind";
    /**
     * 用户绑定手表或者更换绑定。。。http://xx.com/index.php/api/device/watch_bind
     */
    public static final String WHATCH_BUDLING_OR_REPLACE = START_URL
            + "device/watch_bind";
    /**
     * 手表位置。。。。。http://xx.com/index.php/api/device/get_location
     */
    public static final String WHATCH_LOCATION = START_URL
            + "device/get_location";
    /**
     * 已创建家庭列表...http://xx.com/index.php/api/family/get_own_famliy
     */
    public static final String HAVE_FAMILY_LIST = START_URL
            + "family/get_own_famliy";
    /**
     * 用户获取健康数据接口...http://code.healthywo.com/index.php/api/device/get_monitor
     */
    public static final String HEALTH_MONITOR = START_URL
            + "device/get_monitor";
    /**
     * 用户获取健康历史数据接口。。。http://xx.com/index.php/api/device/get_monitor_history
     */
    public static final String HEALTH_MONITOR_HISTORY = START_URL
            + "device/get_monitor_history";

    public static final String Simpie_Test = START_URL
            + "open/index";
    /** sunhealth3.0 **/
    /**
     * 登录 TODO
     */
    public static final String LOGIN = START_URL + "open/login";
    /**
     * 第三方登录
     */
//    public static final String Loginqq = "User/loginqq.html";
    public static final String Loginqq = START_URL + "open/login_tencent";
    public static final String LoginWechat = START_URL + "open/login_weichat";
    public static final String LoginWeibo = START_URL + "open/login_weibo";
    /**
     * 微信获取openid
     */
    public static final String WXOPenid = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /*
     * 首页
     */
    public static final String INDEX = START_URL + "index/index";
    /**
     * 验证码
     */
//    public static final String SMS = START_URL + "Common/smsCode.html";
    public static final String SMS = START_URL + "open/send_sms";//新服验证码
    /**
     * 获取版本信息
     */
    public static final String VERSION_INFO = START_URL + "open/version_info";
    /**
     * 二期   注册填写个人信息和编辑会员信息
     */
//    public static final String USER_REGISTER = START_URL + "User/register";
    public static final String USER_REGISTER = START_URL + "member/edit_info";
    /*
   * 获取个人资料
   * */
    public static final String USER_GET_INFO = START_URL + "member/get_info";
    /*
    * 用户获取地址
    * */
    public static final String GET_AREA = START_URL + "member/get_area";
    /**
     * 意见反馈
     */
//    public static final String FEED_BACK = "Other/feedback.html";
    public static final String FEED_BACK = START_URL + "help/feedback";
    /**
     * 获取帮助信息
     */
    public static final String HELP_INFO = START_URL + "article/get_help_list";
    /**
     * 编辑健康档案基本资料
     */
    public static final String HEALTH_BASIC_INFO = START_URL + "member/basic_info";
    /**
     * 健康获取基本资料
     */
    public static final String HEALTH_GET_BASIC_INFO = START_URL + "member/get_basic_info";
    /*
    * 用户搜索
    * */
    public static final String SEARCH_MEMBER = START_URL + "family/search_member";
    /*
    * 邀请会员
    * */
    public static final String INVITE_MEMBER = START_URL + "family/invite_member";
    /*
    * 创建家庭
    * */
    public static final String ADD_FAMILY = START_URL + "family/add_family";
    /*
    * 添加家庭成员
    * */
    public static final String ADD_FAMILY_MEMBER = START_URL + "family/add_family_member";
    /*
    * 修改家庭成员
    * */
    public static final String EDIT_FAMILY_MEMBER = START_URL + "family/edit_family_member";
    /*
      * 获取家庭详细
      * */
    public static final String FAMILY_INFO = START_URL + "family/family_info";
    /*
    * 获取健康数据
    * */
    public static final String GET_MONITOR = START_URL + "device/get_monitor";
    /*
    * 退出家庭
    * */
    public static final String QUIT_FAMILY = START_URL + "family/quit_family";
    /*
    * 获取家庭成员详细
    * */
    public static final String GET_MEMBER_INFO = START_URL + "family/get_member_info";
    /*
    * 家庭管理详细
    * */
    public static final String FAMILY_MANAGE = START_URL + "family/family_mange";
    /*
       *修改家庭详细
       * */
    public static final String EDIT_FAMILY_INFO = START_URL + "family/edit_family_info";
    /*
   * 移除家庭成员
   * */
    public static final String DELETE_FAMILY_MEMBER = START_URL + "family/delete_family_member";
    /*
    * 获取系统和家庭消息
    * */
    public static final String MESSAGE_SYSTEM_INFO = START_URL + "message/get_message";
    /*
    * 获取家庭消息
    * */
    public static final String MESSAGE_FAMILY_INFO = START_URL + "message/family_info";
    /*
    *删除消息
     */
    public static final String DELETE_INFO = START_URL + "message/delete_info";
    /*
    * 读取消息
    * */
    public static final String READ_INFO = START_URL + "message/read_info";
    /*
    * 资讯列表
    * */
    public static final String GET_ARTICLE_LIST = START_URL + "article/get_list";
    /*
     * 咨询详情
     */
    public static final String GET_ARTICLE_INFO = START_URL + "article/get_info";
    /*
    * 添加吃药提醒
    */
    public static final String ADD_PILL_WARN = START_URL + "warns/add_pill_warns";
    /*
    * 添加事项提醒
    */
    public static final String ADD_THING_WARNS = START_URL + "warns/add_thing_warns";
    /*
    * 修改事项提醒
    */
    public static final String EDIT_THING_WARNS = START_URL + "warns/edit_thing_warns";
    /*
    * 获取事项提醒
    */
    public static final String GET_THING_WARNS = START_URL + "warns/get_self_list";
    /*
    * 提醒--开启/关闭提醒
    */
    public static final String CLOSE_OPEN_WARNS = START_URL + "warns/close_open_warns";
    /*
    * 提醒--删除提醒
    */
    public static final String DELETE_WARNS = START_URL + "warns/delete_warns";
    /*
     * 提醒--获取家庭提醒列表
     */
    public static final String FAMILY_REMIND_LIST = START_URL + "warns/get_family_list";
    /*
    * 消息--同意加入家庭
    */
    public static final String MESSAGE_JOIN_FAMILY = START_URL + "message/join_family";
    public static final String DEVICE_LIST = START_URL + "device/device_list";
    /*
   * 文章--获取食材一级分类
   */
    public static final String GET_FOOD_CATE = START_URL + "article/get_food_cate";
    /*
   * 文章--获取食材二级分类
   */
    public static final String GET_FOOD_LIST = START_URL + "article/get_food_list";
    /*
    * 文章--获取食材二级分类
    */
    public static final String GET_FOOD_DETAIL = START_URL + "article/get_food_detail";
    /*
     * 产品--产品列表
     */
    public static final String GET_GOODS_LIST = START_URL + "article/goods_list";
    /*
     * 产品--获取产品分类列表
     */
    public static final String GET_CATE_LIST = START_URL + "goods/cate_list";
    /*
     * 产品--获取产品分类列表
     */
    public static final String GET_GOODS_DETAIL = START_URL + "article/goods_detail";
    /*
     * 产品--获取产品分类列表
     */
    public static final String CREATE_ORDER = START_URL + "order/create_order";
    public static final String CUSTOMER_CREATE_ORDER = START_URL + "order/custom_create_order";
    /*
     * 订单--会员获取订单列表
     */
    public static final String GET_ORDER_LIST = START_URL + "order/get_list";
    /*
     * 订单--获取订单详细
     */
    public static final String ORDER_GET_INFO = START_URL + "order/get_info";
    /*
     * 会员--新增收货地址
     */
    public static final String ADDR_ADD_ADDR = START_URL + "address/add_address";
    /*
     * 会员--获取收货人列表
     */
    public static final String ADDR_GET_LIST = START_URL + "address/get_list";
    /*
     * 会员--修改收货地址
     */
    public static final String ADDR_EDIT_ADDR = START_URL + "address/edit_address";
    /*
    * 会员--删除收货地址
    */
    public static final String ADDR_DELETE_ADDR = START_URL + "address/delete_address";
    /*
     * 订单--发起支付
     */
    public static final String PAY_INITIATE = START_URL + "pay/initiate";
    /*
     * 订单--确认订单
     */
    public static final String CONFIRM_ORDER = START_URL + "order/confirm_order";
    /*
     * 库管--获取快递列表
     */
    public static final String EXPRESS_LIST = START_URL + "repertory/express_list";
    /*
     * 业绩--完成订单
     */
    public static final String Order_LIST = START_URL + "achieve/order_list";
    /*
     * 业绩--未完成订单
     */
    public static final String ORDER_LIST = START_URL + "achieve/order_list";
    /*
     * 业绩--未完成订单
     */
    public static final String UnOrder_LIST = START_URL + "achieve/unorder_list";
    /*
     * 业绩--获取订单详情
     */
    public static final String Order_LIST_DETAIL = START_URL + "achieve/order_detail";
    /*
     * 库管--产品入库
     */
    public static final String GOODS_REOERTORY = START_URL + "repertory/goods_repertory";
    /*
     * 会员--修改默认地址
     */
    public static final String ADDR_SET_DEFAULT = START_URL + "address/set_default";
    /*
     * 库管--产品出库
     */
    public static final String GOODS_OUT_REOERTORY = START_URL + "repertory/delivery";
    /*
     * 库管-衍生品出库
     */
    public static final String YISHENGJUN_OUT_REOERTORY = START_URL + "repertory/goods_delivery";
    /*
     * 库管--已入库
     */
    public static final String GOODS_IN_REOERTORY = START_URL + "repertory/have_finished";
    /*
     * 库管--待发货接口
     */
    public static final String GOODS_WAIT_SEND = START_URL + "repertory/wait_send";
    /*
     * 库管--未发货详情
     */
    public static final String GOODS_SEND_DETAIL = START_URL + "repertory/send_detail";
    /*
     * 库管--已发货列表
     */
    public static final String GOODS_WAITEN_SEND = START_URL + "repertory/waiten_send";
    /*
     * 库管--已发货详情
     */
    public static final String GOODS_HAS_SEND_DETAIL = START_URL + "repertory/has_send_detail";
    /*
     * 库管--添加商品数量
     */
    public static final String REPOSITY_SET_NUM = START_URL + "repertory/set_num";
    /*
     * 会员--判断用户权限
     */
    public static final String MEMBER_POWER = START_URL + "member/member_power";
    /*
     * 订单--未支付订单接口
     */
    public static final String UNORDER_MEMBER = START_URL + "order/unorder_member";
    /*
     * 经销商--生成二维码
     */
    public static final String CREATE_QRCODE = START_URL + "seller/create_qrcode";
    /*
     * 销售员--申请成为销售员
     */
    public static final String APPLY_SELLER = START_URL + "seller/apply_seller";
    /*
     * 销售员--潜在客户报备
     */
    public static final String ADD_CUSTOM = START_URL + "seller/add_custom";
    /*
     * 销售员--获取客户列表
     */
    public static final String CUSTOM_LIST = START_URL + "seller/custom_list";
    /*
     * 销售员--获取潜在客户列表
     */
    public static final String POTENTIAL_CUSTOM_LIST = START_URL + "seller/potential_custom_list";
    /*
     * 销售员--查看客户详情
     */
    public static final String CUSTOM_DETAIL = START_URL + "seller/custom_detail";
    /*
     * 销售员--获取团队详情
     */
    public static final String CUSTOM_TEAM = START_URL + "seller/custom_team";
    /*
     * 用户服务协议
     */
    public static final String AGREEMENT_USER = "http://sun.healthywo.com/page/agreement_user.html";
    /*
     * 个人消费商协议
     */
    public static final String AGREEMENT_PERSONAL= "http://sun.healthywo.com/page/agreement_person.html";
    /*
     * 查询手表编码
     */
    public static final String WATCH_IMEI = START_URL + "device/watch_imei";
    /*
     * 用户删除订单接口
     */
    public static final String DEL_ORDER = START_URL + "order/del_order";
	
	
	
	public static final String WILDDOG_TOKEN = "http://auth.healthywo.com/api/auth/app";

    public static final String ROBOT_LIST = "http://sun.healthywo.com/api/device/robot_list";

    public static final String ROBOT_UID = "http://auth.healthywo.com/api/code/robot_info";
}
