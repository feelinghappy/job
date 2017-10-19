package com.longcai.medical.zxing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.ClearEditText;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.ToastUtils;

import java.util.HashMap;


/**
 * Created by Administrator on 2017/6/12.
 */

public class CaptureInputActivity extends BaseActivity implements View.OnClickListener {

    private ClearEditText scannerInputID;
    private EditText scannerInputSerial;
    private Button scannerInputBind;
    private Button scannerInputBack;
    TextView title;
    ImageView back;
    private String scanMark;
    private int isMark = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题

        initView();
        initData();
    }

    public void initView() {
        setContentView(R.layout.activity_scanner_input);
        scannerInputID = (ClearEditText) findViewById(R.id.scanner_input_ID);
        scannerInputSerial = (EditText) findViewById(R.id.scanner_input_serial);
        scannerInputBind = (Button) findViewById(R.id.scanner_input_bind);
        scannerInputBack = (Button) findViewById(R.id.scanner_input_back);
        title = (TextView) findViewById(R.id.title_tv);
        back = (ImageView) findViewById(R.id.iv_return_capter);
        scannerInputBind.setOnClickListener(this);
        scannerInputBack.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    boolean isRobot;
    public void initData() {
        Intent intent = getIntent();
//        isRobot = intent.getBooleanExtra("isRobot", false);
        scanMark = intent.getStringExtra(Constant.SCAN_MARK);

        if (scanMark.equals(Constant.SCAN_MARK_robot)) {//一版 绑定机器人
            isMark = 1;
            scannerInputBind.setText("绑定");
        } else if (scanMark.equals(Constant.SCAN_MARK_watch)) {//一版 绑定手表
            isMark = 2;
            scannerInputID.setHint("请输入手表编码");
            scannerInputBind.setText("绑定");
        } else if (scanMark.equals(Constant.SCAN_MARK_device)) {//二版 绑定设备
            isMark = 3;
            scannerInputID.setHint("请输入设备编码");
            scannerInputBind.setText("绑定");
        } else if (scanMark.equals(Constant.SCAN_MARK_Ruku_Sn)) {//入库 扫描机器人sn码
            isMark = 4;
            scannerInputBind.setText("提交");
        }
        /*if (isRobot) {
            scannerInputSerial.setVisibility(View.VISIBLE);
            title.setText("手动输入机器人编码");
        } else {
            title.setText("手动输入穿戴设备编码");
            scannerInputSerial.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击绑定
            case R.id.scanner_input_bind:
                String id = scannerInputID.getText().toString().trim();
//                String Serial = scannerInputSerial.getText().toString().trim();
                if (TextUtils.isEmpty(id)) {
                    ToastUtils.showToast(this, "请输入编码");
                    return;
                }
                if (isMark == 1){//机器人
                    if (id.length() != 23){
                        ToastUtils.showToast(this,"请输入正确的机器人编码");
                        return;
                    }
                }else if (isMark == 2){//手表
                    if (!(id.length() == 14) || id.length() == 15){
                        ToastUtils.showToast(this,"请输入正确的手表编码");
                        return;
                    }
                }else if(isMark == 3){//机器人/手表
                    if (!(id.length() == 23 || id.length() == 14) || id.length() == 15){
                        ToastUtils.showToast(this,"请输入正确的设备编码");
                        return;
                    }
                }else if (isMark == 4){//机器人sn码
                    if (!id.substring(0,3).toUpperCase().equals("YYD")){
                        ToastUtils.showToast(this,"请输入正确的机器人sn码");
                        return;
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("id", id.toUpperCase());
                /*if (isRobot) {
                    intent.putExtra("Serial", Serial);
                }*/
                setResult(Constant.WhatchBudlingSuccessToInput_CODE, intent);
                finish();
                break;
            case R.id.iv_return_capter:
                finish();
                break;
            case R.id.scanner_input_back:
                finish();
                break;
        }
    }
    private void isCorrectWatch(String id){
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("watch_imei", id);
        HttpUtils.xOverallHttpPost(this, MyUrl.WATCH_IMEI, map, String.class, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }
}
 /*if (isRobot) {
                    //机器人
                    if (TextUtils.isEmpty(Serial)) {
                        ToastUtils.show(this, "请输入序列号");
                        return;
                    }
                    if (id.length() < 18) {
                        ToastUtils.show(this, "请输入正确的机器人编码");
                    }
                   /* if (Serial.length() < 4) {
                        ToastUtils.show(this, "请输入正确的序列号");
                    }
                } else {
                    //手表穿戴设备
                }*/
