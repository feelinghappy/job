package com.longcai.medical.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.BasicInfoResult;
import com.longcai.medical.bean.FeedBackResult;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 二期意见反馈界面
 */
public class ErQiOpinionActivity extends BaseActivity {

    private static final String TAG = "ErQiOpinionActivity";
    @BindView(R.id.erqi_opinion_et)
    EditText erqiOpinionEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_qi_opinion);
        ButterKnife.bind(this);
    }

    private void test() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("title", "1");
//        map.put("content", encodeHanzi("APPTestTest"));
//        map.put("content", stringToUnicode("APP测试内容测试内容"));
        map.put("content", "APP测试内容测试内容");
        map.put("contact", "15034090496");

        HttpUtils.xOverallHttpPost(this, MyUrl.FEED_BACK, map, FeedBackResult.class, new HttpUtils.OnxHttpCallBack<FeedBackResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(FeedBackResult feedBackResult) {
//                ToastUtils.showToast(ErQiOpinionActivity.this, decodeUnicode(feedBackResult.getContent().replace("0x", "\\")));
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    private void testHelpInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
//        map.put("true_name", "Jing");
//        map.put("member_sex", "1");
//        map.put("birthday", "1990-01-01");
//        map.put("member_height", "180");
//        map.put("member_weight", "66");
//        map.put("profession", "2");
//        map.put("medical_type", "1");

        HttpUtils.xOverallHttpPost(this, MyUrl.HEALTH_GET_BASIC_INFO, map, BasicInfoResult.class, new HttpUtils.OnxHttpCallBack<BasicInfoResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(BasicInfoResult basicInfoResult) {
                LogUtils.d("health/get_basic_info", basicInfoResult.toString());
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });

//        HttpUtils.xOverallHttpPost(this, MyUrl.HEALTH_BASIC_INFO, map, BasicInfoResult.class, new HttpUtils.OnxHttpCallBack<BasicInfoResult>() {
//            @Override
//            public void onSuccessMsg(String successMsg) {
//
//            }
//
//            @Override
//            public void onSuccess(BasicInfoResult basicInfoResult) {
//                LogUtils.d("health/basic_info", basicInfoResult.toString());
//            }
//
//            @Override
//            public void onFail(int code, String msg) {
//
//            }
//        });

//        HttpUtils.xOverallHttpPost(this, MyUrl.HELP_INFO, map, new HttpUtils.OnxHttpCallBack<Object>() {
//            @Override
//            public void onSuccessMsg(String successMsg) {
//
//            }
//
//            @Override
//            public void onSuccess(Object o) {
//
//            }
//
//            @Override
//            public void onFail(int code, String msg) {
//
//            }
//        });
    }

    @OnClick({R.id.opinion_back_btn, R.id.tijiao_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.opinion_back_btn:
                this.finish();
                break;
            case R.id.tijiao_btn:
                testHelpInfo();
//                if (!TextUtils.isEmpty(erqiOpinionEt.getText().toString())) {
//                    RequestParams params = new RequestParams(MyUrl.FEED_BACK);
//                    params.addParameter("uid", MyApplication.myPreferences.readUID());
//                    params.addParameter("content", erqiOpinionEt.getText().toString());
//
//                    x.http().post(params, new Callback.CommonCallback<String>() {
//                        @Override
//                        public void onSuccess(String result) {
//                            Log.i(TAG, "getData: onSuccess   " + result);
//                            Gson gson = new Gson();
//                            FeedBackBean info = gson.fromJson(result, FeedBackBean.class);
//                            if (info.getStatus().equals("1")) {
//                                ErQiOpinionActivity.this.finish();
//                                ToastUtils.showToast(ErQiOpinionActivity.this, info.getMessage());
//                                erqiOpinionEt.setText("");
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable ex, boolean isOnCallback) {
//                            Log.i(TAG, "getData: onError   " + ex.getMessage());
//                        }
//
//                        @Override
//                        public void onCancelled(CancelledException cex) {
//                            Log.i(TAG, "getData: onCancelled   ");
//                        }
//
//                        @Override
//                        public void onFinished() {
//                            Log.i(TAG, "getData: onFinished   ");
//
//                        }
//                    });
//                } else {
//                    ToastUtils.showToast(ErQiOpinionActivity.this, "请输入您的意见");
//                }
                break;
        }
    }


}
