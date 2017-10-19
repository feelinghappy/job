package com.longcai.medical.ui.activity.family;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.TubatuAdapter;
import com.longcai.medical.bean.GetFamilyInfoBean;
import com.longcai.medical.bean.GetLocationBean;
import com.longcai.medical.bean.GetMonitorBean;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.AMapActivity;
import com.longcai.medical.ui.activity.GloabeActivityFirst;
import com.longcai.medical.ui.activity.GloabeActivitySecond;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.ui.activity.person.PersonDeviceActivity;
import com.longcai.medical.ui.view.ClipViewPager;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.FileUtils;
import com.longcai.medical.utils.GsonUtils;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.ScalePageTransformer;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.TimeUtil;
import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.zxing.activity.CaptureActivity;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/18.
 * 家庭详情
 */

public class FamilyDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "FamilyDetailActivity";
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.title_right_iv)
    ImageView titleRightIv;
    @BindView(R.id.fd_familyPage)
    ImageView fdFamilyPage;
    @BindView(R.id.fd_tv_xfyj)
    TextView fdTvXfyj;
    @BindView(R.id.fd_tv_jtID)
    TextView fdTvJtID;
    @BindView(R.id.fd_tv_jtrs)
    TextView fdTvJtrs;
    @BindView(R.id.fd_rll_layout_vp)
    AutoRelativeLayout fdRllLayoutVp;
    @BindView(R.id.iv_location)
    TextView ivLocation;
    @BindView(R.id.fd_sex)
    TextView fdSex;
    @BindView(R.id.fd_height)
    TextView fdHeight;
    @BindView(R.id.fd_age)
    TextView fdAge;
    @BindView(R.id.fd_weight)
    TextView fdWeight;
    @BindView(R.id.family_detail_history)
    TextView familyDetailHistory;
    @BindView(R.id.sport_num)
    TextView sportNum;
    @BindView(R.id.heart_fast_num)
    TextView heartFastNum;
    @BindView(R.id.heart_slow_num)
    TextView heartSlowNum;
    @BindView(R.id.heart_num)
    TextView heartNum;
    @BindView(R.id.blood_time_num)
    TextView bloodTimeNum;
    @BindView(R.id.blood_num)
    TextView bloodNum;
    @BindView(R.id.sleep_grade)
    TextView sleepGrade;
    @BindView(R.id.sleep_num)
    TextView sleepNum;
    @BindView(R.id.heart_pilao_num)
    TextView heartPilaoNum;
    @BindView(R.id.heart_pic_time_num)
    TextView heartPicTimeNum;
    @BindView(R.id.heart_pic_num)
    TextView heartPicNum;
    @BindView(R.id.fd_monitor)
    AutoLinearLayout fdMonitor;
    @BindView(R.id.fd_clickBind)
    ImageView fdClickBind;
    @BindView(R.id.fd_unBindButton)
    AutoRelativeLayout fdUnBindButton;
    @BindView(R.id.fd_unBindload)
    AutoLinearLayout fdUnBindload;
    @BindView(R.id.viewpager)
    ClipViewPager viewpager;
    @BindView(R.id.page_container)
    AutoRelativeLayout pageContainer;
    private LayoutInflater mInflater;
    //    private ImageView menu_invite_member, menu_open_video;
    private View popopMenu, popop_quit_family;
    private HashMap<String, String> map = new HashMap<>();
    private List<GetFamilyInfoBean.MemberListBean> member_list;
    private ClipViewPager mViewPager;
    private TubatuAdapter mPagerAdapter;
    private String is_mange;
    private String family_member_id;
    private String my_family_member_id;
    //    private QuitFamilyBean quitFamilyBean;
    private String family_id, family_name;
    private String mRobot_imei; //机器人编码
    private boolean isHaveWatch = false;
    private PopupWindow popWindow_quit;
    private TextView pop_familyname;
    private TextView quit_ok;
    private TextView quit_cancel;
    private PopupWindow popWindow_menu;
    //    private GetLocationBean getLocationBean;
    private String lat, lng;
    //    private AnimationDrawable aaLoad;
    private String family_number;
    private Unbinder unbinder;
    private boolean newFamily;
    private boolean havaScanner;
    private boolean fromDevices;
    private boolean editFamily = false;
    private int familyPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_detailinfo);
        unbinder = ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        /*获取family_id*/
        family_id = getIntent().getStringExtra("family_id");
        my_family_member_id = getIntent().getStringExtra("family_member_id");
        newFamily = getIntent().getBooleanExtra("new_family", false);
        havaScanner = getIntent().getBooleanExtra("haveScanner", false);
        fromDevices = getIntent().getBooleanExtra("from_devices", false);
        familyPosition = getIntent().getIntExtra("family_position", -1);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initHeadPager();
    }

    /*切换用户*/
    private void initHeadPager() {
        mViewPager = (ClipViewPager) findViewById(R.id.viewpager);
        mViewPager.setPageTransformer(true, new ScalePageTransformer());

        findViewById(R.id.page_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mPagerAdapter = new TubatuAdapter(this);
        initData();
    }

    private void initData() {
        /*获取家庭详细MemberList*/
        getFamilyDetailData();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getInfoHealth(position);//健康信息
                initGetMonitor();//监测信息
            }

            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
                /*getInfoHealth(position);//健康信息
                initGetMonitor();//监测信息*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        if (newFamily) {
            if (havaScanner) {
                if (fromDevices) {
                    Intent intent = new Intent(this, PersonDeviceActivity.class);
                    startActivity(intent.putExtra("refresh_devices", true));
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                } else {
                    Intent intent = new Intent(this, GloabeActivityFirst.class);
                    startActivity(intent.putExtra("refreshRobotList", true));
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                }
            } else {
                MyApplication.source = 2;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent.putExtra("source", MyApplication.source).putExtra("refresh_family", true));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        } else {
            if (editFamily) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("family_position", familyPosition);
                intent.putExtra("source", 2);
                intent.putExtra("refresh_family", true);
                startActivity(intent);
                finish();
            } else {
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        }
    }

    @OnClick({R.id.title_back, R.id.title_right_iv, R.id.iv_location, R.id.family_detail_history, R.id.fd_clickBind})
    public void onViewClicked(View view) {
        initTextColor();
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
            case R.id.title_right_iv://菜单
                popWindow_menu = new ShowPopupWindow(FamilyDetailActivity.this).showPopup(popopMenu);
                popWindow_menu.setAnimationStyle(0);
                popWindow_menu.showAsDropDown(titleRightIv);
                break;
            case R.id.iv_location:
                if (isHaveWatch) {
                    getWatchLocation(); //获取手表位置
                } else {
                    ToastUtils.showToast(this, "您还没有绑定手表设备");
                }
                break;
            case R.id.family_detail_history://历史记录
                Intent intentY = new Intent(this, GloabeActivitySecond.class);
                intentY.putExtra(Constant.FRAGMENT_MARK, Constant.HISTORY_FG);
//                intentY.putExtra(Constant.FAMILY_MEMBER_ID, family_member_id);
                startActivity(intentY);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.fd_clickBind://点击绑定智能穿戴设备
                addWhatch();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_family_manage://家庭管理
                popWindow_menu.dismiss();
                if (is_mange != null) {
                    if (is_mange.equals("1")) {
                        LogUtils.e("family_id = " + family_id);
                        startActivityForResult(new Intent(FamilyDetailActivity.this, FamilyManagerActivity.class)
                                .putExtra("family_id", family_id), 200);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else if (is_mange.equals("0")) {
                        ToastUtils.showToast(this, "非家庭管理者无法操作");
                        return;
                    }
                }
                break;

            case R.id.menu_quit_family://退出家庭
                if (popWindow_menu != null) {
                    popWindow_menu.dismiss();
                }
                pop_familyname.setText(family_name);
                popWindow_quit = new ShowPopupWindow(FamilyDetailActivity.this).showPopup(popop_quit_family);
                popWindow_quit.showAtLocation(popop_quit_family, Gravity.CENTER, 0, 0);
                initPop();
                break;

        }
    }

    /*
    * 添加手表设备*/
    private void addWhatch() {
        Intent intent = new Intent(FamilyDetailActivity.this, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_watch);
        intent.putExtra(Constant.WHATCH_MARK, Constant.WHATCH_BUDLING);
        intent.putExtra(Constant.WHATCH_Budling_From_markF, Constant.WHATCH_Budling_From_FamilyDetailMun);
        startActivityForResult(intent, 12);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    /*获取家庭详细*/
    private void getFamilyDetailData() {
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", family_id);
        HttpUtils.xOverallHttpPost(this, MyUrl.FAMILY_INFO, map, GetFamilyInfoBean.class, new HttpUtils.OnxHttpCallBack<GetFamilyInfoBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetFamilyInfoBean getFamilyInfoBean) {
                processFamilyInfoData(getFamilyInfoBean);
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    //获取家庭详细返回结果
    private void processFamilyInfoData(GetFamilyInfoBean familyInfo) {
        //设置图片变暗
        int brightness = -30; //RGB偏移量，变暗为负数
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(matrix);
        fdFamilyPage.setColorFilter(cmcf);
        //是否管理者 1.是 0.否
        is_mange = familyInfo.getFamily_info().getIs_mange();
        family_name = familyInfo.getFamily_info().getFamily_name();
        family_number = familyInfo.getFamily_info().getFamily_number();
        String member_count = familyInfo.getFamily_info().getMember_count();
        mRobot_imei = familyInfo.getFamily_info().getRobot_imei();
        member_list = familyInfo.getMember_list();
        String file_path = familyInfo.getFamily_info().getAffix().getFile_path();
        String is_image = familyInfo.getFamily_info().getAffix().getIs_image();
        //0 是默认三张图 1 是上传的图片
        if (is_image.equals("0")) {
            if (file_path.equals("1")) {
                fdFamilyPage.setImageResource(R.mipmap.family_list1);
            } else if (file_path.equals("2")) {
                fdFamilyPage.setImageResource(R.mipmap.family_list2);
            } else if (file_path.equals("3")) {
                fdFamilyPage.setImageResource(R.mipmap.family_list3);
            }
        } else if (is_image.equals("1")) {
            Glide.with(getApplicationContext()).load(file_path).placeholder(R.mipmap.test_zhanweitu).into(fdFamilyPage);
        }
        fdTvJtrs.setText(member_count);
        fdTvJtID.setText("家庭ID:" + family_number);
        //默认显示第一个成员的数据
        getInfoHealth(0);
        /*获取健康监测数据 */
        initGetMonitor();
        //设置OffscreenPageLimit
        mViewPager.setOffscreenPageLimit(Math.min(member_list.size(), 3));
        mPagerAdapter.addAll(member_list);

        if (member_list.size() > 0) {
            mViewPager.setCurrentItem(0);
        }
        mViewPager.setAdapter(mPagerAdapter);
    }

    /*从获取家庭详细接口获取个人信息*/
    private void getInfoHealth(int position) {
        family_member_id = member_list.get(position).getMember_info().getFamily_member_id();
        //user_health_info
        String member_age = member_list.get(position).getHealth_info().getMember_age();
        String member_height = member_list.get(position).getHealth_info().getMember_height();
        String member_sex = member_list.get(position).getHealth_info().getMember_sex();
        String member_weight = member_list.get(position).getHealth_info().getMember_weight();
        Object physique_type = member_list.get(position).getHealth_info().getPhysique_type();
        //设置家庭详情个人数据
        fdTvXfyj.setText(family_name);
        fdTvJtID.setText(family_number);
        if (member_sex.equals("1")) {//性别
            fdSex.setText("男");
        } else if (member_sex.equals("2")) {
            fdSex.setText("女");
        } else {
            fdSex.setText("未知");
        }
        if (member_height == null || member_height.equals("0")) {//身高
            fdHeight.setText("未知");
        } else {
            fdHeight.setText(member_height + "cm");
        }
        if (member_weight == null || member_weight.equals("0")) {//体重
            fdWeight.setText("未知");
        } else {
            fdWeight.setText(member_weight + "kg");
        }
        if (member_age != null) {//年龄
            fdAge.setText(member_age + "岁");
        } else {
            fdAge.setText("未知");
        }

    }

    /*获取手表健康监测数据 12*/
    private void initGetMonitor() {
        if (!TextUtils.isEmpty(my_family_member_id) && my_family_member_id.equals(family_member_id)) {
            String healthDataStr = FileUtils.readFromFile(this, "watch_data");
            if (!TextUtils.isEmpty(healthDataStr)) {
                LogUtils.d("HealthDataFromLocal", healthDataStr);
                GetMonitorBean getMonitor = GsonUtils.GsonToBean(healthDataStr, GetMonitorBean.class);
                processGetMonitorData(getMonitor);
            }
        } else {
            fdUnBindButton.setVisibility(View.VISIBLE);
            fdMonitor.setVisibility(View.GONE);
        }
        /*fdUnBindload.setBackgroundResource(R.drawable.loading);
        aaLoad = (AnimationDrawable) fdUnBindload.getBackground();
        aaLoad.start();*/
        map.clear();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_member_id", family_member_id);

        HttpUtils.xOverallHttpPost(this, MyUrl.GET_MONITOR, map, GetMonitorBean.class, new HttpUtils.OnxHttpCallBack<GetMonitorBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetMonitorBean getMonitorBean) {
               /* if (aaLoad != null) {
                    aaLoad.stop();
                }*/
                processGetMonitorData(getMonitorBean);
            }

            @Override
            public void onFail(int code, String msg) {
               /* if (aaLoad != null) {
                    aaLoad.stop();
                }
                fdUnBindload.setVisibility(View.GONE);*/
                try {
                    fdUnBindButton.setVisibility(View.VISIBLE);
                    fdMonitor.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //获取健康数据返回结果
    private void processGetMonitorData(GetMonitorBean getMonitorBean) {
        isHaveWatch = true;
//        fdUnBindload.setVisibility(View.GONE);
        fdUnBindButton.setVisibility(View.GONE);
        fdMonitor.setVisibility(View.VISIBLE);
        if (null != getMonitorBean) {
            /*血压*/
            GetMonitorBean.BloodDataBean blood_data = getMonitorBean.getBlood_data();
//        bloodMax.setText(blood_data.getSystolic());
//        bloodMin.setText(blood_data.getDiastolic());
            bloodTimeNum.setText(blood_data.getCreate_time() + " 更新");
        /*心率*/
            GetMonitorBean.HeartDataBean heart_data = getMonitorBean.getHeart_data();
            heartNum.setText(heart_data.getAvg_heart());
        /*睡眠*/
            GetMonitorBean.SleepDataBean sleep_data = getMonitorBean.getSleep_data();
            String sleep_time = TimeUtil.formatDuring(Long.parseLong(sleep_data.getSleep_time()));
            sleepNum.setText(sleep_time);
            String sleep_grade = sleep_data.getSleep_grade();
            if (sleep_grade.equals("1")) {
                sleepGrade.setText("优秀");
            } else if (sleep_grade.equals("2")) {
                sleepGrade.setText("良好");
            } else if (sleep_grade.equals("3")) {
                sleepGrade.setText("一般");
            } else if (sleep_grade.equals("4")) {
                sleepGrade.setText("不好");
            } else if (sleep_grade.equals("5")) {
                sleepGrade.setText("极不好");
            }
        /*步数*/
            GetMonitorBean.SportDataBean sport_data = getMonitorBean.getSport_data();
            sportNum.setText(sport_data.getStep_num());
        }
    }

    /*退出家庭请求*/
    private void quitFamilyData() {
        map.clear();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", family_id);
        HttpUtils.xOverallHttpPost(this, MyUrl.QUIT_FAMILY, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String objects) {
                if (TextUtils.isEmpty(mRobot_imei)) {
                    MyApplication.source = 2;
                    Intent intent = new Intent(FamilyDetailActivity.this, MainActivity.class);
                    startActivity(intent.putExtra("source", MyApplication.source).putExtra("refresh_family", true));
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                } else {
                    //从机器人服务器上解绑
                    setRobotRelieveToRobotService();
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (msg.equals("family owner can not quit")) {
                    ToastUtils.showToast(FamilyDetailActivity.this, " 有家庭成员无法退出");
                }
               /* if (code == -5) {
                    ToastUtils.showToast(FamilyDetailActivity.this, " 有家庭成员无法退出");
                } else {
                    ToastUtils.showToast(FamilyDetailActivity.this, msg);
                }*/
            }
        });
    }

    /*获取手表位置*/
    private void getWatchLocation() {
        map.clear();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_member_id", family_member_id);
        HttpUtils.xOverallHttpPost(this, MyUrl.WHATCH_LOCATION, map, GetLocationBean.class, new HttpUtils.OnxHttpCallBack<GetLocationBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetLocationBean getLocationBean) {
                isHaveWatch = true;
                /*得到了经纬度*/
                lat = getLocationBean.getLat();
                lng = getLocationBean.getLng();
                if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)){
                    openMap(lng, lat, Constant.Watch_Location);
                }else {
                    ToastUtils.showToast(FamilyDetailActivity.this,"未绑定手表");
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 11243){
                    ToastUtils.showToast(FamilyDetailActivity.this,"未绑定手表");
                }
            }
        });
    }

    /**
     * 打开地图
     */
    private void openMap(String longitude, String latitude, String name) {
        // mLongitude = "37.583092";
        // mLatitude = "112.685562";
        Intent intent = new Intent(FamilyDetailActivity.this, AMapActivity.class);
        intent.putExtra(Constant.LONGITUDE, longitude);
        intent.putExtra(Constant.LATITUDE, latitude);
        intent.putExtra(Constant.NAME, name);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //打电话给机器人
    private void callToRobat() {
        if (Constant.ifRobotLogin) {
            //获取机器人信息
            // getRobotsInfo result:[id:ZK20C1494818532823,addr:112.74.132.204,
            // rid:121467,rname:小宝,online:true,controller:0,serial:YF86,battery:87,
            // id:ZK20C1494818532830,addr:112.74.132.204,rid:121474,
            // rname:东方红,online:false,controller:0,serial:E2GA,battery:0]

        } else {
            ToastUtils.showToast(FamilyDetailActivity.this, "机器人还没有登录");
        }
    }

    /**
     * 机器人绑定到机器人厂家服务器
     */
    private void buildRobotService(String robotId, String robotSerial) {
        Constant.RobotId = robotId;
        Constant.RobotSerial = robotSerial;
        Log.e(TAG, "setBudlingRobotService: phone  " + Constant.PHONE_NUMBER + "  robotId  " +
                Constant.RobotId + "  robotSerial  " + Constant.RobotSerial);
        //(robotId - 机器人的id    robotSerial - 机器人序列号)

    }

    /* 控制机器人*/
    private void controlRobot(final String rid) {
        //控制机器人(机器人id,)

    }

    /**
     * 机器人服务器，机器人解绑
     */
    private void setRobotRelieveToRobotService() {
        //(robotId - 机器人的id    robotSerial - 机器人序列号)
        String robotId = StringUtils.getSubString(0, mRobot_imei.indexOf("-"), mRobot_imei);
        String robotSerial = StringUtils.getSubString(mRobot_imei.indexOf("-") + 1, mRobot_imei.length(), mRobot_imei);
        LogUtils.d("截取字符串 result:" + robotId + "...." + robotSerial);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "resultCode  " + resultCode + "");
        if (requestCode == 200 && resultCode == RESULT_OK) {
            editFamily = true;
        }
        switch (resultCode) {
            case Constant.WhatchBudlingSuccessToManaDet_CODE:
                initGetMonitor();
                break;
        }
    }

    private void initView() {
        titleTv.setText("家庭详情");
        titleRightTv.setVisibility(View.GONE);
        titleRightIv.setVisibility(View.VISIBLE);
        titleRightIv.setImageResource(R.mipmap.icon_caidan);

//        fdClickBind.setVisibility(View.INVISIBLE);
        popopMenu = mInflater.inflate(R.layout.popup_menu, null);
        AutoLinearLayout menu_quit_family = (AutoLinearLayout) popopMenu.findViewById(R.id.menu_quit_family);
        AutoLinearLayout menu_family_manage = (AutoLinearLayout) popopMenu.findViewById(R.id.menu_family_manage);

        popop_quit_family = mInflater.inflate(R.layout.popop_quit_family, null);
        pop_familyname = (TextView) popop_quit_family.findViewById(R.id.fd_familyName);
        quit_ok = (TextView) popop_quit_family.findViewById(R.id.quit_ok);
        quit_cancel = (TextView) popop_quit_family.findViewById(R.id.quit_cancel);
//        menu_invite_member = (ImageView) popopMenu.findViewById(R.id.menu_member_invite);
//        menu_open_video = (ImageView) popopMenu.findViewById(R.id.menu_open_video);
        menu_family_manage.setOnClickListener(this);
        menu_quit_family.setOnClickListener(this);
//        menu_invite_member.setOnClickListener(this);
//        menu_open_video.setOnClickListener(this);

    }

    //退出家庭pop
    private void initPop() {
        quit_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow_quit != null) {
                    popWindow_quit.dismiss();
                }
                quitFamilyData();
            }
        });
        quit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow_quit != null) {
                    popWindow_quit.dismiss();
                }
            }
        });
    }

    private void initTextColor() {
//        heartAva.setTextColor(getResources().getColor(R.color.lan));
        heartFastNum.setTextColor(getResources().getColor(R.color.qianhui));
        heartSlowNum.setTextColor(getResources().getColor(R.color.qianhui));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
