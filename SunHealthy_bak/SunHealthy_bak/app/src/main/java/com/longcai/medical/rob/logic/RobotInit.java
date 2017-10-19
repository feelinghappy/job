package com.longcai.medical.rob.logic;

import android.content.Context;
import android.util.Log;

import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.rob.bean.Constants;
import com.longcai.medical.rob.bean.Robot;
import com.longcai.medical.rob.bean.RobotFamilyResult;
import com.longcai.medical.rob.bean.RobotUidResult;
import com.longcai.medical.rob.bean.WildTokenResult;
import com.longcai.medical.rob.ui.RobotHomeActivity;
import com.longcai.medical.rob.utils.DataUtils;
import com.longcai.medical.rob.utils.SharedPrefUtils;
import com.longcai.medical.MyApplication;
import com.longcai.medical.utils.HttpUtils;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liutao on 2017/10/4.
 */

public class RobotInit {

    public static String customToken = "";
    public static List<RobotFamilyResult> resultList;
    public static List<String> uids = new ArrayList<>();
    public static List<Robot> robots= new ArrayList<>();
    private static Map<String, Robot> robotMap = new HashMap<>();
    public static Context context;
    public static RobotDataListener listener;

    public static RobotDataListener getListener() {
        return listener;
    }

    public static void setListener(RobotDataListener listener) {
        RobotInit.listener = listener;
    }

    /**
     * 第一步，通过app的登录token，获取机器人列表，如果绑定了机器人，则再去获取野狗的登录token。
     */
    public static void getRobotList(Context ctx, RobotDataListener robotDataListener){
        listener = robotDataListener;
        context = ctx;
        String appToken = MyApplication.myPreferences.readToken();
        HashMap<String, String> map = new HashMap<>();
        Log.d("RobotInit", "getWildDogByToken: " + appToken);
        map.put("token", appToken);
        HttpUtils.xListRobotsHttpPost(context, MyUrl.ROBOT_LIST, map, RobotFamilyResult.class, new HttpUtils.OnxHttpWithListResultCallBack<RobotFamilyResult>(){
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(List<RobotFamilyResult> t) {
                Log.d("RobotInit", "xonSuccess: " + t.get(0).toString());
                if(t != null && t.size() > 0){
                    resultList = t;
                    getWildDogByToken();
                }
            }

            @Override
            public void onFail(int code, String msg) {
                listener.onFailed(code, msg);
                context = null;
            }
        });
    }

    /**
     * 第二步，通过app的token，获取野狗的token，然后去登录野狗的服务器
     */
    public static void getWildDogByToken() {
        String appToken = MyApplication.myPreferences.readToken();
        //通过appToken去获取登录wildog的customToken
        HashMap<String, String> map = new HashMap<>();
        map.put("client_type", "app");
        map.put("token", appToken);

        HttpUtils.xTokenHttpPost(context, MyUrl.WILDDOG_TOKEN, map, WildTokenResult.class, new HttpUtils.OnxHttpCallBack<WildTokenResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
                Log.d("RobotInit", "onSuccessMsg: " + successMsg);
            }

            @Override
            public void onSuccess(WildTokenResult wildTokenResult) throws Exception {
                Log.d("RobotInit", "onSuccess: customToken :" + wildTokenResult.toString());
                customToken = wildTokenResult.getCustom_token();
                SharedPrefUtils.saveConfigInfo(context, Constants.WILDDOG_TOKEN, customToken);
                signWilddogByToken(customToken);
            }

            @Override
            public void onFail(int code, String msg) {
                Log.d("RobotInit", "onFail: " + msg);
                listener.onFailed(code, msg);
                context = null;
            }
        });

    }

    /**
     * 第三步，登录野狗服务器，登录成功后，在service中，初始化视频通话的sdk，监听来电。并保存登录野狗的token。
     * @param customToken
     */
    public static void signWilddogByToken(final String customToken) {
        Log.d("RobotInit", "signWilddogByToken: " + customToken);
        WilddogAuth mauth = WilddogAuth.getInstance();
        mauth.signInWithCustomToken(customToken).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                Log.d("RobotInit", "signWilddogByToken: ------" );
                if(task.isSuccessful()){
                    Log.d("result","认证成功");
                    getRobotUid(customToken);
                }else {
                    Log.d("result","认证失败"+task.getException().toString());
                    listener.onFailed(1234, "login wilddog failed.");
                    context = null;

                }
            }
        });
    }

    /**
     * 第四步，获取绑定的每个机器人的uid
     * @param customToken
     */
    public static void getRobotUid(String customToken){
        for(int i = 0; i < resultList.size(); i++ ){
            //Log.d("RobotInit", "getRobotUid token:" + customToken + "sn:" + resultList.get(i).getRobot_imei());
            HashMap<String, String> map = new HashMap<>();
            map.put("client_type", "app");
            map.put("custom_token", customToken);
            //map.put("robot_sn", resultList.get(i).getRobot_imei());
            map.put("robot_sn", "HSRasd0170400001");
            HttpUtils.xTokenHttpPost(context, MyUrl.ROBOT_UID, map, RobotUidResult.class, new HttpUtils.OnxHttpCallBack<RobotUidResult>() {
                @Override
                public void onSuccessMsg(String successMsg) {
                    Log.d("RobotInit", "getRobotUid: " + successMsg);
                }

                @Override
                public void onSuccess(RobotUidResult robotUidResult) throws Exception {
                    Log.d("RobotInit", "getRobotUid: " + robotUidResult.toString() + "######" +SharedPrefUtils.getConfigInfo(context, "currentUid"));
                    uids.add(robotUidResult.getWilddog_uid());
                    if(uids.size() == resultList.size()){
                        listener.onIdsSuccess(uids);
                    }
                    context = null;
                }
                @Override
                public void onFail(int code, String msg) {
                    Log.d("RobotInit", "onFail:getRobotUid  " + msg);
                    listener.onFailed(code, msg);
                    context = null;
                }
            });
        }
    }

    public static void initRobotsData(final List<String> uids, final RobotDataListener listener){
        for(String uid : uids){
            DataUtils.monitorDataByUid(uid, new RobotStateListener() {
                @Override
                public void onSuccess(Robot robot) {
//                    if(robots.size() == 0){
//                        robots.add(robot);
//                    }
//                    else{
//                        for(int i = 0; i < robots.size(); i++){
//                            if(robots.get(i).getUid().equals(robot.getUid())){
//                                robots.set(i, robot);
//                            }
//                            else
//                            {
//                                robots.add(robot);
//                            }
//                        }
//                    }
//                    listener.onRobotSuccess(robots);



                    Log.d("RobotInit---", "onSuccess: "  + robot);
                    robotMap.put(robot.getUid(), robot);
                    listener.onRobotSuccess(robotMap);
                }

                @Override
                public void onFailed(String msg) {

                }
            });
        }
    }


}
