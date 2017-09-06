package com.hrg.test;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.DisplayUtils;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by fan on 2016/5/25.
 */
public class GenerateActivity extends Activity implements View.OnClickListener {

    private ImageView ivqr;
    private TextView tvread;
    private android.widget.EditText etinput;
    private TextView tvcreate;
    private TextView tvcreatelogo;
    private ImageView ivlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        this.ivlogo = (ImageView) findViewById(R.id.iv_logo);
        this.tvcreatelogo = (TextView) findViewById(R.id.tv_createlogo);
        this.tvcreate = (TextView) findViewById(R.id.tv_create);
        this.etinput = (EditText) findViewById(R.id.et_input);

        this.tvread = (TextView) findViewById(R.id.tv_read);
        this.ivqr = (ImageView) findViewById(R.id.iv_qr);

        tvcreatelogo.setOnClickListener(this);
        tvcreate.setOnClickListener(this);
        tvread.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_create:            //创建二维码

                if(!checkIsEmpty())
                    createQRCode();
                else
                    Toast.makeText(GenerateActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();

                break;

            case R.id.tv_createlogo:       //创建带logo

                if(!checkIsEmpty())
                    createQRCodeWithLogo();
                else
                    Toast.makeText(GenerateActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();

                break;

            case R.id.tv_read:      //识别

                decodeQRCode();

                break;
        }
    }


    /**
     * 校验输入框是否有内容
     * 没有内容返回true，有内容返回false
     * @return
     */
    private boolean checkIsEmpty(){

        return TextUtils.isEmpty(etinput.getText().toString().trim());

    }


    /**
     * 创建二维码
     */
    private void createQRCode() {

        //生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成回调
        QRCodeEncoder.encodeQRCode(etinput.getText().toString().trim(), DisplayUtils.dp2px(GenerateActivity.this, 160), new QRCodeEncoder.Delegate() {
            /**
             * 生成成功
             * @param bitmap
             */
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                ivqr.setImageBitmap(bitmap);
            }

            /**
             * 生成失败
             */
            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(GenerateActivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 创建带logo二维码
     */
    private void createQRCodeWithLogo() {

        //生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成二维码颜色，第四个参数为logo资源，第五个参数为生成回调
        QRCodeEncoder.encodeQRCode(etinput.getText().toString().trim(), DisplayUtils.dp2px(GenerateActivity.this, 160), Color.parseColor("#000000"), ((BitmapDrawable)ivlogo.getDrawable()).getBitmap(), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                ivqr.setImageBitmap(bitmap);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(GenerateActivity.this, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 解析
     */
    public void decodeQRCode() {
        Bitmap bitmap = ((BitmapDrawable)ivqr.getDrawable()).getBitmap();
        decode(bitmap, "解析二维码失败");
    }

    /**
     * 解析二维码,可以解析二维码、带logo二维码、条形码
     * @param bitmap
     * @param err
     */
    private void decode(Bitmap bitmap, final String err) {
        QRCodeDecoder.decodeQRCode(bitmap, new QRCodeDecoder.Delegate() {
            @Override
            public void onDecodeQRCodeSuccess(String result) {
                Toast.makeText(GenerateActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDecodeQRCodeFailure() {
                Toast.makeText(GenerateActivity.this, err, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
