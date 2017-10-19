package com.longcai.medical.ui.activity.family;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.AddFamilyMemberBean;
import com.longcai.medical.bean.GetMemberInfoBean;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.photo.PictureSelectActivity;
import com.longcai.medical.ui.view.ClearEditText;
import com.longcai.medical.ui.view.PhoneNumberTextWatcher;
import com.longcai.medical.ui.view.popupwindow.AgePicker;
import com.longcai.medical.ui.view.popupwindow.HeightPicker;
import com.longcai.medical.ui.view.popupwindow.SexPicker;
import com.longcai.medical.ui.view.popupwindow.WeightPicker;
import com.longcai.medical.utils.GlideCircleTransform;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.RegexUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.zxing.activity.CaptureActivity;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.longcai.medical.gloabe.Constant.Whatch_Manager_family_Replace_CODE;

/**
 * Created by Administrator on 2017/5/18.
 * 家庭成员详情
 */

public class FamilyMemberActivity extends PictureSelectActivity implements SexPicker.ISexPicker, AgePicker.IAgePicker,
        HeightPicker.IHeightPicker, WeightPicker.IWeightPicker {
    private static final int FAMILY_MEM_TIZHI = 5;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.family_mem_head)
    ImageView familyMemHead;
    @BindView(R.id.family_mem_name)
    ClearEditText familyMemName;
    @BindView(R.id.family_mem_age)
    TextView familyMemAge;
    @BindView(R.id.family_mem_phone)
    ClearEditText familyMemPhone;
    @BindView(R.id.family_mem_height)
    TextView familyMemHeight;
    @BindView(R.id.family_mem_weight)
    TextView familyMemWeight;
    @BindView(R.id.family_mem_shebei)
    TextView familyMemShebei;
    @BindView(R.id.family_mem_sex)
    TextView familyMemSex;
    @BindView(R.id.family_mem_save)
    Button familyMemSave;
    @BindView(R.id.fmem_layout1)
    AutoRelativeLayout familyLayoutHead;
    private PopupWindow popWindow;
    private LayoutInflater inflater;
    private HashMap<String, String> map = new HashMap<>();
    private TextView pop1, pop2;
    private String sex;
    private String mFamily_member_id;
    private String member_age;
    private String avatar;
    private String nickname;
    private String member_sex;
    private String member_height;
    private String member_weight;
    private String physique_type;
    private String watch_imei;
    boolean isAddWhatch = false;
    boolean isReplaceWhatch = false;
    private String url;
    private int age = -1;
    private int weight;
    private int height;
    private String family_id;
    private String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initView();
        if (!Constant.isCreateMem) {
            //获取家庭成员详细
            initGetData();
        }
        initEvents();
    }

    private void initView() {
        titleTv.setText("家庭成员");
        titleRightTv.setVisibility(View.GONE);
        familyMemPhone.addTextChangedListener(new PhoneNumberTextWatcher(familyMemPhone));
    }

    /*获取家庭成员详情信息*/
    private void initGetData() {
        mFamily_member_id = getIntent().getStringExtra("family_member_id");
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_member_id", mFamily_member_id);
        HttpUtils.xOverallHttpPost(this, MyUrl.GET_MEMBER_INFO, map, GetMemberInfoBean.class, new HttpUtils.OnxHttpCallBack<GetMemberInfoBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetMemberInfoBean getMemberInfoBean) {
                processMemberData(getMemberInfoBean);
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    private void processMemberData(GetMemberInfoBean getMemberInfoBean) {
        avatar = getMemberInfoBean.getAvatar();
        nickname = getMemberInfoBean.getNickname();
        member_sex = getMemberInfoBean.getMember_sex();
        member_age = getMemberInfoBean.getMember_age();
        String mobile = getMemberInfoBean.getMobile();
        member_height = getMemberInfoBean.getMember_height();
        member_weight = getMemberInfoBean.getMember_weight();
        physique_type = getMemberInfoBean.getPhysique_type();
        watch_imei = getMemberInfoBean.getWatch_imei();
        if (getMemberInfoBean != null) {
            if (familyMemName != null){
                familyMemName.setText(nickname);
                familyMemName.setSelection(nickname.length());
                setTextColor(familyMemName);
            }
            if (familyMemAge != null){
                familyMemAge.setText(member_age);
                setTextColor(familyMemAge);
            }
            if (mobile != null) {
                StringBuilder builder = new StringBuilder(mobile);
                builder.insert(3, "-");
                builder.insert(8, "-");
                familyMemPhone.setText(builder.toString());
                setTextColor(familyMemPhone);
            }
            if (avatar != null) {
                Glide.with(FamilyMemberActivity.this).load(avatar).transform(new GlideCircleTransform(this)).placeholder(R.mipmap.head).into(familyMemHead);
            }
            if (member_sex != null) {
                if (member_sex.equals("1")) {
                    familyMemSex.setText("男");
                } else if (member_sex.equals("2")) {
                    familyMemSex.setText("女");
                }
                setTextColor(familyMemSex);
            }
            if (member_height != null) {
                familyMemHeight.setText(member_height + "");
                setTextColor(familyMemHeight);
            }
            if (member_weight != null) {
                familyMemWeight.setText(member_weight + "");
                setTextColor(familyMemWeight);
            }
            if (watch_imei != null) {
                familyMemShebei.setText(watch_imei);
                setTextColor(familyMemShebei);
            }
             /*  if (physique_type.equals("0")) {
                familyMemTizhi.setText("请测试体质");
            } else if (physique_type != null) {
                familyMemTizhi.setText(physique_type);
            }*/
        }
    }
    private void setTextColor(TextView textView){
        textView.setTextColor(getResources().getColor(R.color.black));
    }
    @OnClick({R.id.title_back, R.id.family_mem_save, R.id.fmem_layout1, R.id.family_mem_name, R.id.family_mem_sex,R.id.family_mem_age,
            R.id.family_mem_phone, R.id.family_mem_height, R.id.family_mem_weight, R.id.family_mem_shebei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.family_mem_save://确认
                family_id = getIntent().getStringExtra("family_id");
                if (TextUtils.isEmpty(familyMemName.getText().toString().trim()) || familyMemName.getText().toString().equals("请输入昵称")) {
                    Toast.makeText(FamilyMemberActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                //测试
                sex = "1";
                if (TextUtils.isEmpty(sex) && TextUtils.isEmpty(member_sex)) {
                    Toast.makeText(FamilyMemberActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (familyMemAge.getText().toString().equals("请选择年龄")) {
                    Toast.makeText(FamilyMemberActivity.this, "请选择年龄", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phoneNum = familyMemPhone.getText().toString().trim().replaceAll("-", "");
                if (!TextUtils.isEmpty(phoneNum)) {
                    if (!RegexUtils.checkMobile(phoneNum)) {
                        ToastUtils.showToast(this, "请输入正确的手机号");
                        return;
                    }
                }
                //创建和修改成员数据
                if (Constant.isCreateMem) {
                    url = MyUrl.ADD_FAMILY_MEMBER;
                    addFamilyMemberData(url);
                } else {
                    url = MyUrl.EDIT_FAMILY_MEMBER;
                    editFamilyMemberData(url);
                }
                break;
            case R.id.fmem_layout1:
                selectPicture();
                break;
            case R.id.family_mem_sex:
                SexPicker sexPicker = new SexPicker(this);
                sexPicker.showPopupWindow(familyMemSex);
                sexPicker.setISexPicker(this);
                break;
            case R.id.family_mem_name:
                StringUtils.isNameLimit(familyMemName);
                break;
            case R.id.family_mem_age:
                AgePicker agePicker = new AgePicker(this);
                agePicker.showPopupWindow(familyMemAge);
                agePicker.setIAgePicker(this);
                break;
            case R.id.family_mem_phone:
                familyMemPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                break;
            case R.id.family_mem_height:
                HeightPicker heightPicker = new HeightPicker(this);
                heightPicker.showPopupWindow(familyMemHeight);
                heightPicker.setIHeightPicker(this);
                break;
            case R.id.family_mem_weight:
                WeightPicker weightPicker = new WeightPicker(this);
                weightPicker.showPopupWindow(familyMemWeight);
                weightPicker.setIWeightPicker(this);
                break;
           /* case R.id.family_mem_tizhi://体质
                Intent intent = new Intent(this, StaminaActivity.class);
                intent.putExtra("type", "3");
                startActivityForResult(intent, FAMILY_MEM_TIZHI);
                break;*/
//            case R.id.family_mem_shebei://点击绑定设备
//                addWhatch();
//                break;
        }
    }
    //添加家庭成员 （没有图片）有返回结果
    private void addFamilyMemberData(String url) {
        initMap();
        if (imagePath == null){
            HttpUtils.xOverallHttpPost(this, url, map, AddFamilyMemberBean.class,new HttpUtils.OnxHttpCallBack<AddFamilyMemberBean>() {
                @Override
                public void onSuccessMsg(String successMsg) {
                }

                @Override
                public void onSuccess(AddFamilyMemberBean addFamilyMemberBean) {
                        Intent intent = new Intent();
                        intent.putExtra("member_name",addFamilyMemberBean.getMember_name());
                        intent.putExtra("member_avatar",addFamilyMemberBean.getAvatar());
                        setResult(10,intent);
                        finish();
                }

                @Override
                public void onFail(int code, String msg) {
                    failMsg(code);
                }
            });
        }else {
            hasPicData1(url);
        }
    }
    //添加成员 有图片
    private void hasPicData1(String url) {
        HashMap<String, String> filemap = new HashMap<>();
        filemap.put("avatar", imagePath);
        HttpUtils.xOverallHttpPost(FamilyMemberActivity.this, url, filemap, map, AddFamilyMemberBean.class,new HttpUtils.OnxHttpCallBack<AddFamilyMemberBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(AddFamilyMemberBean addFamilyMemberBean) {
                    Intent intent = new Intent();
                    intent.putExtra("member_name",addFamilyMemberBean.getMember_name());
                    intent.putExtra("member_avatar",addFamilyMemberBean.getAvatar());
                    setResult(10,intent);
                    finish();
            }

            @Override
            public void onFail(int code, String msg) {
                failMsg(code);
            }
        });
    }
    //修改家庭成员
    private void editFamilyMemberData(String url) {//EditFamilyMemberBean.class,
        initMap();
        if (imagePath == null) {
            HttpUtils.xOverallHttpPost(this, url, map, String.class, new HttpUtils.OnxHttpCallBack<String>() {
                @Override
                public void onSuccessMsg(String successMsg) {

                }

                @Override
                public void onSuccess(String s) {
                    Intent intent = new Intent();
                    setResult(10,intent);
                    finish();
                }

                @Override
                public void onFail(int code, String msg) {
                    failMsg(code);
                }
            });
        } else {
            hasPicData(url);
        }
    }

    //修改成员 有图片
    private void hasPicData(String url) {
        HashMap<String, String> filemap = new HashMap<>();
        filemap.put("avatar", imagePath);
        HttpUtils.xOverallHttpPost(FamilyMemberActivity.this, url, filemap, map, String.class,new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String editFamilyMemberBean) {
                Intent intent = new  Intent();
                setResult(10,intent);
                finish();
            }

            @Override
            public void onFail(int code, String msg) {
                failMsg(code);
            }
        });
    }

    private void failMsg(int code) {
        switch (code) {
            case 10130:
                ToastUtils.showToast(FamilyMemberActivity.this, "未填写姓名或者姓名格式不正确");
                break;
            case 10131:
                ToastUtils.showToast(FamilyMemberActivity.this, "未填写性别或者性别格式不正确");
                break;
            case 10132:
                ToastUtils.showToast(FamilyMemberActivity.this, "未填写生日或者年龄格式不正确");
                break;
            case 10133:
                ToastUtils.showToast(FamilyMemberActivity.this, "未填写身高或者身高格式不正确");
                break;
            case 10134:
                ToastUtils.showToast(FamilyMemberActivity.this, "未填写体重或者体重格式不正确");
                break;
            case 10135:
                ToastUtils.showToast(FamilyMemberActivity.this, "数据保存失败");
                break;
        }
    }
    public void initEvents() {
        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(FamilyMemberActivity.this).load(stream.toByteArray()).placeholder(R.mipmap.head)
                        .transform(new GlideCircleTransform(FamilyMemberActivity.this))
                        .into(familyMemHead);
                String filePath = fileUri.getEncodedPath();
                imagePath = Uri.decode(filePath);
                LogUtils.d("图片已经保存到:" + imagePath);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.d("resultCode  " + resultCode + "");
        switch (resultCode) {
            case Whatch_Manager_family_Replace_CODE:
                //TODO  执行绑定手表或者更换操作
                isAddWhatch = true;
                familyMemShebei.setText(Constant.WatchImei_ScannerResult);
                break;
             /*
            * 体质测试的结果
            * */
            case FAMILY_MEM_TIZHI:
               /* if (!Constant.STAMINA_PHYSICAL_CREATEMEM.equals("-1")) {
                    familyMemTizhi.setText(Constant.STAMINA_PHYSICAL_CREATEMEM);
                }*/
                break;
        }
    }

    //修改家庭成员参数
    private void initMap() {
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("member_name", familyMemName.getText().toString().trim());
        if (Constant.isCreateMem) {
            map.put("family_id", family_id);//family_id
        } else {
            map.put("family_member_id", mFamily_member_id);
        }
        if (sex != null) {
            map.put("member_sex", sex);//"18234049083"
        } else {
            map.put("member_sex", member_sex);
        }
        if (age >= 0 && !String.valueOf(age).equals(member_age)) {
            map.put("member_age", String.valueOf(age));
        } else {
            map.put("member_age", member_age);
        }
        String phoneNum = familyMemPhone.getText().toString().replaceAll("-", "");
//        if (!TextUtils.isEmpty(phoneNum) && !(familyMemPhone.getText().toString().contains("手机号"))) {
        if (!TextUtils.isEmpty(phoneNum) && !(phoneNum.contains("手机号"))) {
            map.put("member_mobile", phoneNum);
        }
        if (!familyMemHeight.getText().equals("") && !(familyMemHeight.getText().toString().contains("身高"))) {
            map.put("member_height", familyMemHeight.getText().toString());
        }
        if (!familyMemWeight.getText().equals("") && !(familyMemWeight.getText().toString().contains("体重"))) {
            map.put("member_weight", familyMemWeight.getText().toString());
        }
//        if (!familyMemShebei.getText().equals("") && !familyMemShebei.getText().toString().contains("扫一扫")) {
//            map.put("watch_imei", familyMemShebei.getText().toString());
//        }
    }

    private void initPop(View view) {
        pop1 = (TextView) view.findViewById(R.id.tv_takephoto);//拍照
        pop2 = (TextView) view.findViewById(R.id.tv_choosephoto);//相册
        pop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
//                deleteDir();
//                startCamera(familyMemHead);
            }
        });
        pop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
//                deleteDir();
//                startAlbum(familyMemHead);
            }
        });
    }

   /* private void deleteDir() {
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
    }*/

    // TODO 绑定及更换手表
//    private void setWhatchBudlingOrRelieve() {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("token", MyApplication.myPrefer ences.readToken());
//        map.put("watch_imei", Constant.WatchImei_ScannerResult);
//        HttpUtils.xOverallHttpPost(this, MyUrl.WHATCH_BUDLING_OR_REPLACE, map, new HttpUtils.OnxHttpCallBack<List<?>>() {
//            @Override
//            public void onSuccessMsg(String successMsg) {
//                LogUtils.d(successMsg);
//            }
//
//            @Override
//            public void onSuccess(List<?> objects) {
//                setResult(10);
//                finish();
//            }
//
//            @Override
//            public void onFail(int code, String msg) {
//                ToastUtils.showToast(FamilyMemberActivity.this, msg);
//                setResult(10);
//                finish();
//            }
//        });
//    }

    /*添加与更换手表设备*/
    private void addWhatch() {
        Intent intent = new Intent(FamilyMemberActivity.this, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_watch);

        if (!TextUtils.isEmpty(watch_imei)) {
            //更换手表
            isReplaceWhatch = true;
            intent.putExtra(Constant.WHATCH_imei, watch_imei);
            intent.putExtra(Constant.WHATCH_MARK, Constant.WHATCH_BUDLING);
            intent.putExtra(Constant.WHATCH_Budling_From_markF, Constant.WHATCH_Budling_From_ManagerFamilyMun);
        } else {
            //绑定手表
            isReplaceWhatch = false;
            intent.putExtra(Constant.WHATCH_MARK, Constant.WHATCH_BUDLING);
            intent.putExtra(Constant.WHATCH_Budling_From_markF, Constant.WHATCH_Budling_From_ManagerFamilyMun);
        }
        startActivityForResult(intent, 16);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void agePicker(int item) {
        LogUtils.d("AgePicker", String.valueOf(item));
        age = item;
        familyMemAge.setText(String.valueOf(item));
        familyMemAge.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void weightPicker(int item) {
        LogUtils.d("weightPicker", String.valueOf(item));
        weight = item + 20;
        familyMemWeight.setText(String.valueOf(item + 20) + " kg");
        familyMemWeight.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void heightPicker(int item) {
        LogUtils.d("heightPicker", String.valueOf(item));
        height = item + 100;
        familyMemHeight.setText(String.valueOf(item + 100) + " cm");
        familyMemHeight.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void sexPicker(int item, String itemName) {
        LogUtils.d("SexPicker", item + itemName);
        sex = item + 1 + "";
        familyMemSex.setText(itemName);
        familyMemSex.setTextColor(getResources().getColor(R.color.black));
    }
}
