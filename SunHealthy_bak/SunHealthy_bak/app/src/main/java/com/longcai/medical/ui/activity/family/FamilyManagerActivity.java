package com.longcai.medical.ui.activity.family;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jauker.widget.BadgeView;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.FamilyManageAdapter;
import com.longcai.medical.bean.FamilyManageBean;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.photo.PictureSelectActivity;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.GlideRoundTransform;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.zxing.activity.CaptureActivity;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/6/26.
 * 家庭管理
 */

public class FamilyManagerActivity extends PictureSelectActivity implements View.OnClickListener {
    private static final String TAG = "FamilyManagerActivity";
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    //    @BindView(R.id.id_recyclerview_horizontal)
//    RecyclerView mRecyclerView;
    @BindView(R.id.tv_family_name)
    EditText tvFamilyName;
    @BindView(R.id.scanner)
    AutoRelativeLayout scanner;
    @BindView(R.id.robot_id)
    TextView robotId;
    @BindView(R.id.scanner_visible)
    AutoRelativeLayout scannerVisible;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.fm_iv3)
    ImageView fmIv3;
    @BindView(R.id.fm_layout_create)
    AutoLinearLayout fmLayoutCreate;
    @BindView(R.id.fm_layout_invite)
    AutoRelativeLayout fmLayoutInvite;
    @BindView(R.id.family_manage_save)
    Button familyManageSave;
    @BindView(R.id.addphoto)
    ImageView addphoto;
    @BindView(R.id.iv_familypage0)
    ImageView ivFamilypage0;
    @BindView(R.id.iv_familypage1)
    ImageView ivFamilypage1;
    @BindView(R.id.iv_familypage2)
    ImageView ivFamilypage2;
    @BindView(R.id.iv_familypage3)
    ImageView ivFamilypage3;

    private String imagePath;
    private ArrayList<FamilyManageBean.MemberListBean> manage_member_list = new ArrayList<>();
    private FamilyManageAdapter adapter;
    private TextView delete_ok, delete_cancel;
    private PopupWindow popupWindow;
    private String mFamily_member_id;
    private String mRobot_imei = "-1";
    private String mFamily_id;
    String mRobotId; //机器人的id
    String mRobotSerial; //机器人序列号
    boolean isAddRobot = false;
    TextView tv_changebind;
    private TextView tv_unbind;
    private LayoutInflater mInflater;
    private PopupWindow popWindow;
    private View view0, view1, view2;
    private HashMap<String, String> map = new HashMap<>();
    private int tag = -1;
//    private int use_big = -1;
    boolean isReplaceRobot = false;
//    private RequestParams params;
//    private boolean isFirst = true;
    private Unbinder unbinder;

    private BadgeView selectedBadge0;
    private BadgeView selectedBadge1;
    private BadgeView selectedBadge2;
    private BadgeView selectedBadge3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_manager);
        unbinder = ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        //获取详情的family-id
        mFamily_id = getIntent().getStringExtra("family_id");
        initView();
        initListener();
        initBadge();
        initEvents();
//        initRecyclerData();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //popupwindow点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_cancel:
                popupWindow.dismiss();
                break;
            case R.id.tv_changebind://换绑机器人
                popWindow.dismiss();
                budlingOrReplaceRobotToScanner(true);
                break;
            case R.id.tv_unbind://解绑机器人
                popWindow.dismiss();
                robotUnBindToOurService();
                break;
        }
    }

    @OnClick({R.id.title_back, R.id.family_manage_save, R.id.title_right_tv, R.id.scanner,
            R.id.fm_layout_create, R.id.fm_layout_invite, R.id.iv_familypage0, R.id.iv_familypage1,
            R.id.iv_familypage2, R.id.iv_familypage3, R.id.addphoto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.family_manage_save://确认
                // TODO
                if (isAddRobot) {
                    //绑定、换绑机器人 到自己服务器上
                    setBudlingOurService();
                } else {
                    initEditInfo();
                }
                break;
            case R.id.fm_layout_create://创建新成员
                startActivityForResult(new Intent(this, FamilyMemberActivity.class)
                        .putExtra("family_id", mFamily_id).putExtra("isManagerActivityIntent", 1), 10);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                Constant.isCreateMem = true;
                break;
            case R.id.fm_layout_invite://邀请新成员
                startActivity(new Intent(this, SearchActivity.class)
                        .putExtra("FamilyManagerActivity", 2).putExtra("family_id", mFamily_id));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            /*case R.id.change_robot:
                //设置popwindow显示位置
                if (!mRobot_imei.equals("-1")) {
                    popWindow = new ShowPopupWindow(this).showPopup(view2);
                    popWindow.showAtLocation(changeRobot, Gravity.CENTER, 0, 0);
                }
                break;*/
            case R.id.scanner://绑定机器人
                budlingOrReplaceRobotToScanner(false);
                break;
            case R.id.iv_familypage0://点击上传图
                tag = 0;
                setSelectedPhoto();
                map.put("check_type", "0");
                break;
            case R.id.iv_familypage1://点击第一张默认图
                tag = 1;
                setSelectedPhoto();
//                map.put("check_type", "1");
                String path = StringUtils.getResPath(this, R.mipmap.family_list1, "family_list1.png");
                if (!TextUtils.isEmpty(path)) {
                    map.put("check_type", "0");
                    imagePath = path;
                }
                break;
            case R.id.iv_familypage2://点击第二张默认图
                tag = 2;
                setSelectedPhoto();
//                map.put("check_type", "2");
                String path2 = StringUtils.getResPath(this, R.mipmap.family_list2, "family_list2.png");
                if (!TextUtils.isEmpty(path2)) {
                    map.put("check_type", "0");
                    imagePath = path2;
                }
                break;
            case R.id.iv_familypage3://点击第三张默认图
                tag = 3;
                setSelectedPhoto();
//                map.put("check_type", "3");
                String path3 = StringUtils.getResPath(this, R.mipmap.family_list3, "family_list3.png");
                if (!TextUtils.isEmpty(path3)) {
                    map.put("check_type", "0");
                    imagePath = path3;
                }
                break;
            case R.id.addphoto:
                selectPicture();
                break;
        }
    }

    //初始化listview的头布局
  /*  private void initHeadView() {
        View headView = View.inflate(this, R.layout.family_manage_listmember_head_view, null);
        lv.addHeaderView(headView);
    }*/
    public void initEvents() {
        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(FamilyManagerActivity.this).load(stream.toByteArray()).placeholder(R.mipmap.test_zhanweitu)
                        .transform(new GlideRoundTransform(FamilyManagerActivity.this))
                        .into(ivFamilypage0);
                String filePath = fileUri.getEncodedPath();
                imagePath = Uri.decode(filePath);
                ivFamilypage0.setVisibility(View.VISIBLE);
                selectedBadge0.setTargetView(ivFamilypage0);
                LogUtils.d("图片已经保存到:" + imagePath);
            }
        });
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

    //获取家庭管理数据
    private void initData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", mFamily_id);
        HttpUtils.xOverallHttpPost(this, MyUrl.FAMILY_MANAGE, map, FamilyManageBean.class, new HttpUtils.OnxHttpCallBack<FamilyManageBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(FamilyManageBean familyManageBean) {
                processFamilyManageData(familyManageBean);
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    //获取家庭管理数据返回数据
    private void processFamilyManageData(FamilyManageBean familyManageBean) {
        mRobot_imei = familyManageBean.getRobot_imei();
        tvFamilyName.setText(familyManageBean.getFamily_name());
        tvFamilyName.setSelection(familyManageBean.getFamily_name().length());
        List<FamilyManageBean.AffixListBean> affix_list = familyManageBean.getAffix_list();
        if (manage_member_list != null && manage_member_list.size() > 0) {
            manage_member_list.clear();
        }
        manage_member_list.addAll(familyManageBean.getMember_list());
        //附件ID
        for (int i = 0; i < affix_list.size(); i++) {
//            String is_use = affix_list.get(i).getIs_use();
            String file_path = affix_list.get(i).getFile_path();
            //是否图片： 1：是 0：否
            String is_image = affix_list.get(i).getIs_image();
            String affix_id = affix_list.get(i).getAffix_id();

            if (file_path != null) {
                tag = 5;
                if (is_image.equals("1")) {
                    ivFamilypage0.setVisibility(View.VISIBLE);
                    isUse(selectedBadge0, ivFamilypage0);
                    Glide.with(this).load(file_path).placeholder(R.mipmap.test_zhanweitu).transform(new GlideRoundTransform(this)).into(ivFamilypage0);
                } else if (is_image.equals("0")) {
                    //本地默认图片
                    if (file_path.equals("1")) {
                        isUse(selectedBadge1, ivFamilypage1);
                    } else if (file_path.equals("2")) {
                        isUse(selectedBadge2, ivFamilypage2);
                    } else if (file_path.equals("3")) {
                        isUse(selectedBadge3, ivFamilypage3);
                    }
                }
            }
            Log.e(TAG, "run: " + file_path);
        }
        //机器人编码
        if (!mRobot_imei.equals("")) {
            scanner.setVisibility(View.GONE);
            scannerVisible.setVisibility(View.VISIBLE);
            robotId.setText(mRobot_imei);
            mRobotId = StringUtils.getSubString(
                    0, mRobot_imei.indexOf("-"), mRobot_imei);
            mRobotSerial = StringUtils.getSubString(
                    mRobot_imei.indexOf("-") + 1, mRobot_imei.length(), mRobot_imei);
        }

        LogUtils.i(TAG, "家庭管理成员列表集合" + manage_member_list.size() + "");
        adapter = new FamilyManageAdapter(FamilyManagerActivity.this, manage_member_list);
        //初始化listview的头布局 邀请按钮
        /*if (isFirst) {
            initHeadView();
            isFirst = false;
        }*/
        lv.setAdapter(adapter);
        //listview监听事件
        initListItemListener();
    }

    private void isUse(BadgeView selectedBadge, ImageView ivFamilypage) {
        selectedBadge.setVisibility(View.VISIBLE);
        selectedBadge.setTargetView(ivFamilypage);
    }

    //listview点击事件 长按删除，点击跳转进入成员详情
    private void initListItemListener() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//长按
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                LogUtils.i(TAG, "onItemLongClick  " + position + "");
                if (position > 0) {
                    popupWindow = new ShowPopupWindow(FamilyManagerActivity.this).showPopup(view1);
                    popupWindow.showAtLocation(lv, Gravity.CENTER, 0, 0);
                    delete_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            longDeleteMemberData(position);
                        }
                    });
                }
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFamily_member_id = manage_member_list.get(position).getFamily_member_id();
                Log.e(TAG, "onItemClick:mFamily_member_id: " + mFamily_member_id);
                String is_member = manage_member_list.get(position).getIs_member();
                //是否是邀请会员 1：是 0：否
                if (is_member.equals("1")) {
                    ToastUtils.showToast(FamilyManagerActivity.this, "邀请会员无法编辑");
                    return;
                } else {
                    startActivityForResult(new Intent(FamilyManagerActivity.this, FamilyMemberActivity.class)
                            .putExtra("family_member_id", mFamily_member_id), 10);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    Constant.isCreateMem = false;
                }
            }
        });
    }

    //修改家庭详细(图片)
    private void initEditInfo() {
        if (tag == -1) {
            ToastUtils.showToast(this, "请选择一张图片");
            return;
        }
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", mFamily_id);
        map.put("family_name", tvFamilyName.getText().toString());
        //判断imglist是否添加了图片
//        if (tag == 0) {
            PicData();
//        } else {
//            noPicData();
////            PicData();
//        }
    }

    //（修改家庭详细）没有图片的网络请求
    private void noPicData() {
        HttpUtils.xOverallHttpPost(this, MyUrl.EDIT_FAMILY_INFO, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String objects) {
                finish();
            }

            @Override
            public void onFail(int code, String msg) {
                ToastUtils.showToast(FamilyManagerActivity.this, "编辑失败");
            }
        });
    }

    private void PicData() {
        HashMap<String, String> filemap = new HashMap<>();
        if (!TextUtils.isEmpty(imagePath)) {
            filemap.put("photo", imagePath);
        }
        HttpUtils.xOverallHttpPost(this, MyUrl.EDIT_FAMILY_INFO, filemap, map, String.class, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String addFamilyBean) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(int code, String msg) {
                ToastUtils.showToast(FamilyManagerActivity.this, "编辑失败");
            }
        });
    }

    //移除家庭成员
    private void longDeleteMemberData(final int position) {
        HashMap<String, String> map = new HashMap<>();
        mFamily_member_id = manage_member_list.get(position).getFamily_member_id();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_member_id", FamilyManagerActivity.this.mFamily_member_id);
        HttpUtils.xOverallHttpPost(this, MyUrl.DELETE_FAMILY_MEMBER, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String objects) {
                ///长按删除
                manage_member_list.remove(position);
                adapter.notifyDataSetChanged();
                //从机器人服务器上解绑
                unBudlingRobot(false);
            }

            @Override
            public void onFail(int code, String msg) {
                ToastUtils.showToast(FamilyManagerActivity.this, code + msg);
            }
        });
    }


    /* 机器人模块
    */
    //机器人绑定(更换)到自己服务器
    private void setBudlingOurService() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", mFamily_id);
        map.put("robot_imei", Constant.Robot_imeiResult);
        if (isReplaceRobot) {
            isReplaceRobot = false;
            map.put("old_robot_imei", mRobot_imei);
        }
        HttpUtils.xOverallHttpPost(this, MyUrl.ROBOT_BUNDLING, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String objects) {
                Constant.Robot_imeiResult = "0";
                if (isReplaceRobot) {
                    unBudlingRobot(true);//解绑机器人
                } else {
                    setBudlingToRobotService();//绑定机器人
                }
                Constant.isRobotBudling = true;
            }

            @Override
            public void onFail(int code, String msg) {
                ToastUtils.showToast(FamilyManagerActivity.this, "绑定失败");
                //创建家庭失败（绑定机器人失败）
                Constant.isRobotBudling = false;
                initEditInfo();
            }
        });
    }

    //添加绑定机器人  ifRelace 判断是更换还是绑定
    private void budlingOrReplaceRobotToScanner(boolean ifRelace) {
        //直接打开扫码界面，传一些数据标示（参考RobotFg）
        Intent intent = new Intent(this, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_robot);
//        if (ifRelace) {
//            //更换
//            intent.putExtra(Constant.Robot_MARK, Constant.ROBOT_REPLACE);
//            intent.putExtra(Constant.ROBOT_imei, mRobot_imei);
//            intent.putExtra(Constant.ROBOT_Replace_familyId, mFamily_id);
//        } else {
        //绑定
        intent.putExtra(Constant.Robot_MARK, Constant.ROBOT_BUDLING);
        intent.putExtra(Constant.Robot_create_MARK, Constant.ROBOT_manager);
//        }
        startActivityForResult(intent, 28);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //解绑机器人 (自己服务器)
    private void robotUnBindToOurService() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", Constant.UID);
        map.put("family_id", mFamily_id);
        //这个编号 他是不是 换的 -----------------
        map.put("robot_imei", mRobot_imei);
        HttpUtils.xOverallHttpPost(this, MyUrl.ROBOT_UNBUNDLING, map, new HttpUtils.OnxHttpCallBack<List<?>>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(List<?> objects) {
                unBudlingRobot(false); //解绑机器人
                scanner.setVisibility(View.VISIBLE);
                scannerVisible.setVisibility(View.GONE);
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    // 机器人  解绑 (机器人服务器)
    private void unBudlingRobot(final boolean isReplace) {
        //(robotId - 机器人的id    robotSerial - 机器人序列号)

    }

    //机器人  绑定（机器人服务器）
    private void setBudlingToRobotService() {
        Log.e(TAG, "setBudlingToRobotService: phone  " + Constant.PHONE_NUMBER + "  robotId  " +
                Constant.RobotId + "  robotSerial  " + Constant.RobotSerial);
        //(robotId - 机器人的id    robotSerial - 机器人序列号)
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 结果码不等于取消时候
        switch (resultCode) {
            case Constant.Manager_family_CODE:
                // 家庭管理界面绑定机器人返回结果
                // TODO 添加标示是否扫到机器人
                budlingAndReplaceGloable();
                break;
            case Constant.Manager_family_Replace_CODE:
                isReplaceRobot = true;
                budlingAndReplaceGloable();
                break;
            case Constant.WhatchBudlingSuccessToFamDet_CODE:
                //TODO 重新访问家庭管理接口
                initData();
                break;
            case Constant.Manager_family_Intent_CODE:
                initData();
                break;
            case 10:
                initData();
                break;
        }
    }

    private void budlingAndReplaceGloable() {
        isAddRobot = true;
        scanner.setVisibility(View.GONE);
        scannerVisible.setVisibility(View.VISIBLE);
        robotId.setText(Constant.Robot_imeiResult);
    }

    /*
        @Override
        protected Crop getCrop() {
            return new Crop().setCrop(true);
        }

        @Override
        protected String getCameraAbsolutePath() {
            if (UtilSDCard.isSDCardEnable()) {
                File file = new File(Environment.getExternalStorageDirectory() + "/medical/head/");
                if (!file.exists()) {
                    file.mkdir();
                }
            } else {
                File dir = new File(getApplicationInfo().dataDir + "/medical/head/");
                if (!dir.exists())
                    dir.mkdir();
                return dir.getAbsolutePath();
            }
            return Environment.getExternalStorageDirectory() + "/medical/head/";
        }

        @Override
        protected void resultPhotoPath(ImageView imageView, String s) {
            Log.e(TAG, "resultPhotoPath: 图片路径：" + s);
            if (s != null) {
                imagePath = s;
                if (ivFamilypage0.getVisibility() == View.GONE) {
                    ivFamilypage0.setVisibility(View.VISIBLE);
                    selectedBadge0.setTargetView(ivFamilypage0);
    //                ivFamilypage0.setOnClickListener(new View.OnClickListener() {
    //                    @Override
    //                    public void onClick(View view) {
    //                        tag = 0;
    //                        setSelectedPhoto();
    //                        map.put("check_type", "0");
    //                    }
    //                });
                    Glide.with(this).load(s).placeholder(R.mipmap.zw01).transform(new GlideRoundTransform(this)).into(ivFamilypage0);
                } else {
                    Glide.with(this).load(s).placeholder(R.mipmap.zw01).transform(new GlideRoundTransform(this)).into(ivFamilypage0);
                }
            }
        }*/
 /*private void deleteDir() {
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
    private void initListener() {
        tv_unbind.setOnClickListener(this);
        tv_changebind.setOnClickListener(this);
        delete_cancel.setOnClickListener(this);
    }

    private void initView() {
        titleTv.setText("编辑家庭");
        titleRightTv.setVisibility(View.GONE);
        view0 = mInflater.inflate(R.layout.addphoto_popupwindow, null);
        view1 = mInflater.inflate(R.layout.popop_delete_member, null);
        view2 = mInflater.inflate(R.layout.popup_control_robot, null);

        tv_unbind = (TextView) view2.findViewById(R.id.tv_unbind);
        tv_changebind = (TextView) view2.findViewById(R.id.tv_changebind);
        delete_ok = (TextView) view1.findViewById(R.id.delete_ok);
        delete_cancel = (TextView) view1.findViewById(R.id.delete_cancel);
        TextView pop1 = (TextView) view0.findViewById(R.id.tv_takephoto);
        TextView pop2 = (TextView) view0.findViewById(R.id.tv_choosephoto);

        Glide.with(this).load(R.mipmap.family_list1).transform(new GlideRoundTransform(this)).into(ivFamilypage1);
        Glide.with(this).load(R.mipmap.family_list2).transform(new GlideRoundTransform(this)).into(ivFamilypage2);
        Glide.with(this).load(R.mipmap.family_list3).transform(new GlideRoundTransform(this)).into(ivFamilypage3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
