package com.longcai.medical.ui.activity.family;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jauker.widget.BadgeView;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.AddFamilyBean;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.photo.PictureSelectActivity;
import com.longcai.medical.utils.GlideRoundTransform;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.zxing.activity.CaptureActivity;
import com.zcx.helper.util.UtilSDCard;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/26.
 * 创建家庭
 */
public class CreateFamilyActivity extends PictureSelectActivity{

    private static final String TAG = "CreateFamilyActivity";
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.robot_id)
    TextView robotId;
    @BindView(R.id.scanner_robot_imei)
    AutoRelativeLayout scannerRobotImei;
    @BindView(R.id.iv_familypage0)
    ImageView ivFamilypage0;
    @BindView(R.id.iv_familypage1)
    ImageView ivFamilypage1;
    @BindView(R.id.iv_familypage2)
    ImageView ivFamilypage2;
    @BindView(R.id.iv_familypage3)
    ImageView ivFamilypage3;
    @BindView(R.id.addphoto)
    ImageView addphoto;
    @BindView(R.id.ed_inputname)
    EditText edInputname;
    @BindView(R.id.act_create_scanner)
    AutoRelativeLayout actCreateScanner;
    @BindView(R.id.pageGallery)
    LinearLayout pageGallery;
    @BindView(R.id.create_family_save)
    Button createFamilySave;
    @BindView(R.id.fm_iv4)
    ImageView fmIv4;
    @BindView(R.id.fm_robot_name)
    TextView fmRobotName;
    @BindView(R.id.fm_tv2)
    TextView fmTv2;
    @BindView(R.id.tv_family_name)
    TextView tvFamilyName;
    @BindView(R.id.cf_iv1)
    ImageView cfIv1;

    private HashMap<String, String> map = new HashMap<String, String>();
    private LayoutInflater mInflater;
    private PopupWindow popWindow;
    private TextView pop1, pop2;
    private View view_pop;
    private int tag = -1;
    boolean isAddRobot = false;
//    boolean isLocalAddRobot = false;
    private String family_id;

    private BadgeView selectedBadge0;
    private BadgeView selectedBadge1;
    private BadgeView selectedBadge2;
    private BadgeView selectedBadge3;
    private boolean haveScanner;
    private boolean fromDevices;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        initData();
        haveScanner = getIntent().getBooleanExtra("haveScanner", false);
        fromDevices = getIntent().getBooleanExtra("from_devices", false);
        if (haveScanner) {
//        if (Constant.CreateFamilyHaveScaner) {
//            Constant.CreateFamilyHaveScaner = false;
            //得到机器人编码后的一些操作
            haveScaner();
        }
    }

    private void initData() {
        titleTv.setText("创建家庭");
        titleRightTv.setVisibility(View.GONE);
        Glide.with(this).load(R.mipmap.family_list1).transform(new GlideRoundTransform(this)).into(ivFamilypage1);
        Glide.with(this).load(R.mipmap.family_list2).transform(new GlideRoundTransform(this)).into(ivFamilypage2);
        Glide.with(this).load(R.mipmap.family_list3).transform(new GlideRoundTransform(this)).into(ivFamilypage3);

        view_pop = mInflater.inflate(R.layout.addphoto_popupwindow, null);
        pop1 = (TextView) view_pop.findViewById(R.id.tv_takephoto);//拍照
        pop2 = (TextView) view_pop.findViewById(R.id.tv_choosephoto);//选择图库

        initBadge();
        initEvents();

    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @OnClick({R.id.title_back, R.id.iv_familypage0, R.id.iv_familypage1, R.id.iv_familypage2, R.id.iv_familypage3, R.id.addphoto
            , R.id.act_create_scanner, R.id.scanner_robot_imei, R.id.create_family_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.iv_familypage0://点击上传图
                tag = 0;
                setSelectedPhoto();
//                map.put("check_type", "0");
                map.put("check_type", "0");
                break;
            case R.id.iv_familypage1://点击第一张默认图
                tag = 1;
                setSelectedPhoto();
                String path = StringUtils.getResPath(this, R.mipmap.family_list1, "family_list1.png");
                if (!TextUtils.isEmpty(path)) {
                    map.put("check_type", "0");
                    imagePath = path;
                }
                break;
            case R.id.iv_familypage2://点击第二张默认图
                tag = 2;
                setSelectedPhoto();
                String path2 = StringUtils.getResPath(this, R.mipmap.family_list2, "family_list2.png");
                if (!TextUtils.isEmpty(path2)) {
                    map.put("check_type", "0");
                    imagePath = path2;
                }
                break;
            case R.id.iv_familypage3://点击第三张默认图
                tag = 3;
                setSelectedPhoto();
                String path3 = StringUtils.getResPath(this, R.mipmap.family_list3, "family_list3.png");
                if (!TextUtils.isEmpty(path3)) {
                    map.put("check_type", "0");
                    imagePath = path3;
                }
                break;
            case R.id.addphoto:
                /*if (popWindow == null) {
                }
                //设置popwindow显示位置
                popWindow = new ShowPopupWindow(CreateFamilyActivity.this).showPopup(view_pop);
                //设置动画效果
//                popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
                //设置popwindow显示位置
                popWindow.showAsDropDown(addphoto);
                popWindow.update();*/
                selectPicture();
//                initPop();
                break;
            case R.id.act_create_scanner://点击扫码绑定机器人
                addRobot();
                break;
            case R.id.create_family_save://下一步
                if (StringUtils.isEmpty(edInputname.getText().toString())) {
                    ToastUtils.showToast(this, "请输入家庭名称 2-10个字符");
                } else {
                    if (edInputname.getText().length() <= 10 && edInputname.getText().length() >= 2) {
                        createFamily();//创建家庭
                    } else {
                        ToastUtils.showToast(this, "请输入家庭名称 2-10个字符");
                        return;
                    }
                }
        }
    }
    public void initEvents() {
        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(CreateFamilyActivity.this).load(stream.toByteArray()).placeholder(R.mipmap.test_zhanweitu)
                        .transform(new GlideRoundTransform(CreateFamilyActivity.this))
                        .into(ivFamilypage0);
                String filePath = fileUri.getEncodedPath();
                imagePath = Uri.decode(filePath);
                ivFamilypage0.setVisibility(View.VISIBLE);
                selectedBadge0.setTargetView(ivFamilypage0);
                LogUtils.d("图片已经保存到:" + imagePath);
            }
        });
    }
    //创建家庭
    private void createFamily() {
        if (tag == -1) {
            ToastUtils.showToast(this, "请选择一张图片");
            return;
        }
        initMap();
//        if (tag == 0) {
            PicData();
//        } else {
//            noPicData();
//        }
    }

    /*（创建家庭）没有图片的网络请求*/
    private void noPicData() {
        HttpUtils.xOverallHttpPost(this, MyUrl.ADD_FAMILY, map, AddFamilyBean.class, new HttpUtils.OnxHttpCallBack<AddFamilyBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(AddFamilyBean addFamilyBean) {
                family_id = addFamilyBean.getFamily_id();
                if (isAddRobot) {
                    setBudlingRobotService();//执行绑定机器人到机器人服务器后，绑定到本地服务器
                } else {
                    startActivity(new Intent(CreateFamilyActivity.this, InviteFamilyMemActivity.class)
                            .putExtra("family_id", family_id).putExtra("haveScanner", haveScanner).putExtra("from_devices", fromDevices));
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 11003) {
                    ToastUtils.showToast(CreateFamilyActivity.this, "该机器人已被绑定");
                }
                Constant.isRobotBudling = false;
//                ToastUtils.showToast(CreateFamilyActivity.this, code + msg);
            }
        });
    }

    //创建家庭有图片的请求
    private void PicData() {
        HashMap<String, String> filemap = new HashMap<>();
        filemap.put("photo", imagePath);
        HttpUtils.xOverallHttpPost(this, MyUrl.ADD_FAMILY, filemap, map, AddFamilyBean.class, new HttpUtils.OnxHttpCallBack<AddFamilyBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(AddFamilyBean addFamilyBean) {
                family_id = addFamilyBean.getFamily_id();
                if (isAddRobot) {
                    setBudlingRobotService();//执行绑定机器人到机器人服务器后，绑定到本地服务器
                } else {
                    startActivity(new Intent(CreateFamilyActivity.this, InviteFamilyMemActivity.class)
                            .putExtra("family_id", family_id).putExtra("haveScanner", haveScanner).putExtra("from_devices", fromDevices));
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                }
            }

            @Override
            public void onFail(int code, String msg) {
                Constant.isRobotBudling = false;
                if (code == 11003) {
                    ToastUtils.showToast(CreateFamilyActivity.this, "该机器人已被绑定");
                }
            }
        });
    }

    private void initMap() {
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_name", edInputname.getText().toString().trim());
        if (!TextUtils.isEmpty(robotId.getText().toString().trim())) {
            map.put("robot_imei", robotId.getText().toString().trim());
        }
    }

    private void initPop() {
        pop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
//                deleteDir();
                selectPicture();
//                startCamera(addphoto);
            }
        });
        pop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                selectPicture();
//                deleteDir();
//                startAlbum(addphoto);
            }
        });
    }

    private void deleteDir() {
        if (UtilSDCard.isSDCardEnable()) {
            File file = new File(Environment.getExternalStorageDirectory() + "/medical/");
            if (file.exists()) {
                StringUtils.recursionDeleteFile(file);
            }
        } else {
            File dir = new File(getApplicationInfo().dataDir + "/medical/");
            if (dir.exists())
                StringUtils.recursionDeleteFile(dir);
        }
    }

    /*
     添加机器人
     */
    private void addRobot() {
        Intent intent = new Intent(this, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_robot);
        //绑定
        intent.putExtra(Constant.Robot_MARK, Constant.ROBOT_BUDLING);
        intent.putExtra(Constant.Robot_create_MARK, Constant.ROBOT_Create);
        startActivityForResult(intent, 13);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void initBadge() {
        selectedBadge0 = new BadgeView(this);
        selectedBadge0.setBackgroundResource(R.mipmap.photo_selected);
        selectedBadge0.setText("");
        selectedBadge0.setWidth(18);
        selectedBadge0.setHeight(18);
        selectedBadge0.setBadgeGravity(Gravity.BOTTOM | Gravity.RIGHT);
        selectedBadge0.setBadgeMargin(0, 0, 3, 3);

        selectedBadge1 = new BadgeView(this);
        selectedBadge1.setBackgroundResource(R.mipmap.photo_selected);
        selectedBadge1.setText("");
        selectedBadge1.setWidth(18);
        selectedBadge1.setHeight(18);
        selectedBadge1.setBadgeGravity(Gravity.BOTTOM | Gravity.RIGHT);
        selectedBadge1.setBadgeMargin(0, 0, 3, 3);
        selectedBadge1.setTargetView(ivFamilypage1);

        selectedBadge2 = new BadgeView(this);
        selectedBadge2.setBackgroundResource(R.mipmap.photo_selected);
        selectedBadge2.setText("");
        selectedBadge2.setWidth(18);
        selectedBadge2.setHeight(18);
        selectedBadge2.setBadgeGravity(Gravity.BOTTOM | Gravity.RIGHT);
        selectedBadge2.setBadgeMargin(0, 0, 3, 3);
        selectedBadge2.setTargetView(ivFamilypage2);

        selectedBadge3 = new BadgeView(this);
        selectedBadge3.setBackgroundResource(R.mipmap.photo_selected);
        selectedBadge3.setText("");
        selectedBadge3.setWidth(18);
        selectedBadge3.setHeight(18);
        selectedBadge3.setBadgeGravity(Gravity.BOTTOM | Gravity.RIGHT);
        selectedBadge3.setBadgeMargin(0, 0, 3, 3);
        selectedBadge3.setTargetView(ivFamilypage3);

        resetBadge();
    }

    private void resetBadge() {
        selectedBadge0.setVisibility(View.GONE);
        selectedBadge1.setVisibility(View.GONE);
        selectedBadge2.setVisibility(View.GONE);
        selectedBadge3.setVisibility(View.GONE);
    }

    /*设置选中图片缩放*/
    private void setSelectedPhoto() {
        resetBadge();
        if (tag == 0) {
            selectedBadge0.setVisibility(View.VISIBLE);
        }
        if (tag == 1) {
            selectedBadge1.setVisibility(View.VISIBLE);
        }
        if (tag == 2) {
            selectedBadge2.setVisibility(View.VISIBLE);
        }
        if (tag == 3) {
            selectedBadge3.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 机器人绑定到机器人厂家服务器
     */
    private void setBudlingRobotService() {
        Log.e(TAG, "setBudlingRobotService: phone  " + Constant.PHONE_NUMBER + "  robotId  " +
                Constant.RobotId + "  robotSerial  " + Constant.RobotSerial);
        //(robotId - 机器人的id    robotSerial - 机器人序列号)
    }

    //得到机器人编码后的操作
    private void haveScaner() {
        isAddRobot = true;
        actCreateScanner.setVisibility(View.GONE);
        scannerRobotImei.setVisibility(View.VISIBLE);
        robotId.setText(Constant.Robot_imeiResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 结果码不等于取消时候
        if (resultCode != this.RESULT_CANCELED) {
            switch (resultCode) {
                case 10: //邀请页面传递的数据
                    break;
            }
        }
        if (resultCode == Constant.Create_family_CODE) {
            //TODO 添加标示是否扫到机器人
//            isLocalAddRobot = true;
            haveScaner();//得到机器人编码后的操作
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            this.finish();  //finish当前activity
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
