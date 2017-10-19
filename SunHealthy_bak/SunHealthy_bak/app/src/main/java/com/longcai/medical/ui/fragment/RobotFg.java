package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetMonitorBean;
import com.longcai.medical.bean.Robot;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.EmptyActivity;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.ui.activity.person.PersonDeviceActivity;
import com.longcai.medical.utils.FileUtils;
import com.longcai.medical.utils.GsonUtils;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.ScalePageTransformer;
import com.longcai.medical.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 机器人界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class RobotFg extends BaseFragment implements OnClickListener {
    String TAG = "RobotFg";
    private View view;
    public Handler mHandler;
    private Intent intent;
    private ViewPager mViewPager;
    //    private ImageView location, history, rightIv;
    private TextView mmgh, time, bpm, low, average, high, hours, grade, num, equipment;
    private RelativeLayout scan;
    private boolean isHaveRobot = true;//是否绑定机器人
    private List<Robot> mTopNews;
    TextView titleTv, title_ok;
    ImageView returIv;
    private TopNewsAdapter topNewsAdapter;
    private HashMap<String, String> map = new HashMap<>();
    private LinearLayout llRobotIndicator;
    private TextView remind;
    private final int ROBOT_BIND = 600;
    private GetMonitorBean.HeartDataBean heartDataBean;
    private boolean refreshRobotList;

    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.robot_fg, null);
        findView();
        return view;
    }

    @Override
    public void initData() {
        // TODO INITdata
        setViewVisiblity();
//        initHandler();
//        mTopNews = new ArrayList<>();
////        robotLogin();
//        getRobotListFromLocalFile();
//        getRobotListFromService();
////        robotLogin();
//        getHethFromService();
//        setHealthData();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (Constant.ifRobotLogin == false) {

//        }
//        if (Constant.isRobotBudling) {
//            Constant.isRobotBudling = false;
//            getRobotListFromService();
//        }
    }

    private void setViewVisiblity() {
        titleTv.setText("智能硬件");
        title_ok.setVisibility(View.GONE);
        loadRl.setVisibility(View.GONE);
        all.setVisibility(View.GONE);
//        scan.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ROBOT_BIND && resultCode == Constant.GF_ROBOT_Fg_REsult_CODE_robotScam_suc) {
            getRobotListFromService();
        }
        switch (resultCode) {
            case Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_suc:
                //更换绑定成功
                setViewVisiblity();
                getHethFromService();
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_unBuild_suc:
                //解绑成功
                setViewVisiblity();
                getHethFromService();
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_fail:
                //更换绑定失败
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_robotScam_unBuild_suc:
                //解除绑定机器人成功
                getRobotListFromService();
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_robotScam_suc:
                //解除绑定机器人成功
                getRobotListFromService();
                break;
        }
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constant.zero:
                        int position = (int) msg.obj;
                        loadRl.setVisibility(View.VISIBLE);
                        all.setVisibility(View.GONE);

                        break;
                    case Constant.one:
                        // rightIv.setVisibility(View.GONE);
                        // mHandler.removeMessages(ConstantY.one);
                        break;
                    case Constant.two:
//                        if (health != null) {
//                            setHealthDataToView();
//                        }
                        break;
                    case Constant.three:
                        if (!isControlRobot) {
                            mViewPager.setOffscreenPageLimit(3);
                            mViewPager.setPageMargin(5);
                            topNewsAdapter = new TopNewsAdapter();
                            mViewPager.setAdapter(topNewsAdapter);
                            mViewPager.setCurrentItem(0);
                        }
                        break;
                }
            }
        };
    }

    private void getRobotListFromLocalFile() {
        String robotListStr = FileUtils.readFromFile(getActivity(), "robot_list");
        if (!TextUtils.isEmpty(robotListStr)) {
            List<Robot> robotList = GsonUtils.jsonToList(robotListStr, Robot.class);
            if (null != robotList) {
                LogUtils.d("RobotListFromLocal", robotListStr);
                if (null != mTopNews && mTopNews.size() > 0) {
                    mTopNews.clear();
                }
                if (null != llRobotIndicator && llRobotIndicator.getChildCount() > 0) {
                    llRobotIndicator.removeAllViews();
                }
                mTopNews.addAll(robotList);
            }
        }
        setVp();
    }

    /**
     * 获取可操作机器人列表
     */
    private void getRobotListFromService() {
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.ROBOT_OPERATION, map, Robot.class, new
                HttpUtils.OnxHttpWithListResultCallBack<Robot>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {
                    }

                    @Override
                    public void onSuccess(final List<Robot> robotList) {
                        FileUtils.writeToFile(getActivity(), "robot_list", GsonUtils.GsonString(robotList));
                        if (null != mTopNews && mTopNews.size() > 0) {
                            mTopNews.clear();
                        }
                        mTopNews.addAll(robotList);
                        if (null != llRobotIndicator && llRobotIndicator.getChildCount() > 0) {
                            llRobotIndicator.removeAllViews();
                        }
                        setVp();
                        controlType = 3;
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        List<Robot> robotList = new ArrayList<>();
                        FileUtils.writeToFile(getActivity(), "robot_list", GsonUtils.GsonString(robotList));
                        if (code == 11180) {
                            if (null != mTopNews && mTopNews.size() > 0) {
                                mTopNews.clear();
                            }
                            if (null != llRobotIndicator && llRobotIndicator.getChildCount() > 0) {
                                llRobotIndicator.removeAllViews();
                            }
                            setVp();
                        }
                    }
                });
    }

    LinearLayout all;
    //    private ImageView load;
//    private AnimationDrawable aaLoad;
    private RelativeLayout loadRl;

    private void getRobotsInfo() {
        controlType = 3;
    }
    /**
     * 获取健康数据
     */
    public void getHethFromService() {
        refreshRobotList = true;
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_member_id", "");
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.HEALTH_MONITOR, map, GetMonitorBean.class, new HttpUtils.OnxHttpCallBack<GetMonitorBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(GetMonitorBean getMonitor) {
                if (getMonitor != null) {
                    FileUtils.writeToFile(getActivity(), "watch_data", GsonUtils.GsonString(getMonitor));
                    setHealthData(getMonitor);
                }
            }

            @Override
            public void onFail(int code, String msg) {
//                if (code == 11252) {
//
//                }
//                all.setVisibility(View.GONE);
//                scan.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setHealthData() {
        try {
            String healthDataStr = FileUtils.readFromFile(getActivity(), "watch_data");
            GetMonitorBean getMonitor = null;
            if (!TextUtils.isEmpty(healthDataStr)) {
                LogUtils.d("HealthDataFromLocal", healthDataStr);
                getMonitor = GsonUtils.GsonToBean(healthDataStr, GetMonitorBean.class);
            }
            String systolic = "0";
            String diastolic = "0";
            String min_heart = "0";
            String avg_heart = "0";
            String max_heart = "0";
            String sleep_time = "0";
            String step_num = "0";
            String sleep_grade = "0";
            if (null != getMonitor) {
                heartDataBean = getMonitor.getHeart_data();
                systolic = getMonitor.getBlood_data().getSystolic();
                diastolic = getMonitor.getBlood_data().getDiastolic();
                //            min_heart = getMonitor.getHeart_data().getMin_heart();
                avg_heart = getMonitor.getHeart_data().getAvg_heart();
                //            max_heart = getMonitor.getHeart_data().getMax_heart();
                sleep_time = getMonitor.getSleep_data().getSleep_time();
                step_num = getMonitor.getSport_data().getStep_num();
                //            sleep_grade = getMonitor.getSleep_data().getSleep_grade();
                //            MyApplication.myPreferences.saveSystolic(systolic);
                //            MyApplication.myPreferences.saveDiastolic(diastolic);
                //            MyApplication.myPreferences.saveMin_heart(min_heart);
                //            MyApplication.myPreferences.saveMax_heart(max_heart);
                //            MyApplication.myPreferences.saveAvg_heart(avg_heart);
                //            MyApplication.myPreferences.saveSleep_time(sleep_time);
                //            MyApplication.myPreferences.saveSleep_grade(sleep_grade);
                //            MyApplication.myPreferences.saveStep_num(step_num);
            }
            loadRl.setVisibility(View.GONE);
            all.setVisibility(View.VISIBLE);
            scan.setVisibility(View.GONE);
//        time.setText(getMonitor.getBlood_data().getCreate_time());
            mmgh.setText(systolic + "/" + diastolic); // 血压
            bpm.setText(min_heart); // 心率
            hours.setText(sleep_time); // 睡眠时间
            num.setText(step_num); // 步数
            String sleepGrade = "";
            switch (sleep_grade) {
                case "1":
                    sleepGrade = "优秀";
                    break;
                case "2":
                    sleepGrade = "良好";
                    break;
                case "3":
                    sleepGrade = "一般";
                    break;
                case "4":
                    sleepGrade = "不好";
                    break;
                case "5":
                    sleepGrade = "极不好";
                    break;

            }
            grade.setText(sleepGrade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHealthData(GetMonitorBean getMonitor) {
        String systolic = "120";
        String diastolic = "77";
        String min_heart = "70";
        String avg_heart = "70";
        String max_heart = "70";
        String sleep_time = "8";
        String step_num = "3000";
        String sleep_grade = "5";
        if (null != getMonitor) {
            systolic = getMonitor.getBlood_data().getSystolic();
            diastolic = getMonitor.getBlood_data().getDiastolic();
            heartDataBean = getMonitor.getHeart_data();
            min_heart = heartDataBean.getMin_heart();
            avg_heart = heartDataBean.getAvg_heart();
            max_heart = heartDataBean.getMax_heart();
            sleep_time = getMonitor.getSleep_data().getSleep_time();
            step_num = getMonitor.getSport_data().getStep_num();
            sleep_grade = getMonitor.getSleep_data().getSleep_grade();
//            MyApplication.myPreferences.saveSystolic(systolic);
//            MyApplication.myPreferences.saveDiastolic(diastolic);
//            MyApplication.myPreferences.saveMin_heart(min_heart);
//            MyApplication.myPreferences.saveMax_heart(max_heart);
//            MyApplication.myPreferences.saveAvg_heart(avg_heart);
//            MyApplication.myPreferences.saveSleep_time(sleep_time);
//            MyApplication.myPreferences.saveSleep_grade(sleep_grade);
//            MyApplication.myPreferences.saveStep_num(step_num);
        }
//        else {
//            systolic = MyApplication.myPreferences.readSystolic();
//            diastolic = MyApplication.myPreferences.readDiastolic();
//            min_heart = MyApplication.myPreferences.readMin_heart();
//            avg_heart = MyApplication.myPreferences.readAvg_heart();
//            max_heart = MyApplication.myPreferences.readMax_heart();
//            sleep_time = MyApplication.myPreferences.readSleep_time();
//            step_num = MyApplication.myPreferences.readStep_num();
//            sleep_grade = MyApplication.myPreferences.readSleep_grade();
//        }
        loadRl.setVisibility(View.GONE);
        all.setVisibility(View.VISIBLE);
        scan.setVisibility(View.GONE);
        time.setText(getMonitor.getBlood_data().getCreate_time());
        mmgh.setText(systolic + "/" + diastolic); // 血压
        bpm.setText(min_heart); // 心率
        hours.setText(sleep_time); // 睡眠时间
        num.setText(step_num); // 步数
        String sleepGrade = "";
        switch (sleep_grade) {
            case "1":
                sleepGrade = "优秀";
                break;
            case "2":
                sleepGrade = "良好";
                break;
            case "3":
                sleepGrade = "一般";
                break;
            case "4":
                sleepGrade = "不好";
                break;
            case "5":
                sleepGrade = "极不好";
                break;
        }
        grade.setText(sleepGrade);
    }

    boolean isHaveWhatch = false;

    /**
     * 设置顶部滑动
     */
    private void setVp() {
        // TODO SetVp
        mViewPager.setPageTransformer(true, new ScalePageTransformer()); //实现缩放动画
        Robot robotResult;
        if (mTopNews.size() < 1) {
            isHaveRobot = false;
            robotResult = new Robot();
            mTopNews.add(robotResult);
        } else if (mTopNews.size() > 0) {
            isHaveRobot = true;
//            if (isHaveRobot) {
            robotResult = new Robot();
            mTopNews.add(robotResult);
//                if (Constant.ifRobotLogin) {
////                    getRobotInfo();
//                }
//            } else {
            //         mViewPager.setAdapter(new mTopNewsAdapter());
//            }
        }
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(5);

        topNewsAdapter = new TopNewsAdapter();
        mViewPager.setAdapter(topNewsAdapter);
        mViewPager.setCurrentItem(0);
        int size = mTopNews.size();
        if (size > 1) {
            for (int i = 0; i < size; i++) {
                ImageView indicator = new ImageView(getActivity());
                if (i == 0) {
                    indicator.setImageResource(R.mipmap.dot_selected);
                } else {
                    indicator.setImageResource(R.mipmap.dot_default);
                }
                LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i > 0) {
                    dotParams.leftMargin = 5;
                }
                indicator.setLayoutParams(dotParams);
                llRobotIndicator.addView(indicator);
            }
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count = llRobotIndicator.getChildCount();
                for (int i = 0; i < count; i++) {
                    ImageView dotView = (ImageView) llRobotIndicator.getChildAt(i);
                    if (i == position) {
                        dotView.setImageResource(R.mipmap.dot_selected);
                    } else {
                        dotView.setImageResource(R.mipmap.dot_default);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //TODO onClick(View v)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.robot_bottom_high:
                if (null != heartDataBean) {
                    bpm.setText(heartDataBean.getMax_heart()); // 心率
                    setColor();
                    high.setTextColor(getResources().getColor(R.color.blue));
                }
                break;
            case R.id.robot_bottom_low:
                if (null != heartDataBean) {
                    bpm.setText(heartDataBean.getMin_heart()); // 心率
                    setColor();
                    low.setTextColor(getResources().getColor(R.color.blue));
                }
                break;
            case R.id.robot_bottom_average:
                if (null != heartDataBean) {
                    bpm.setText(heartDataBean.getAvg_heart()); // 心率
                    setColor();
                    average.setTextColor(getResources().getColor(R.color.blue));
                }
                break;
            case R.id.robot_fg_equipment:
                //设备管理
                intent = new Intent(mActivity, PersonDeviceActivity.class);
                getActivity().startActivityForResult(intent.putExtra("fromRobot", true), Constant.device_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.robot_fg_remind://设置提醒
                startActivity(new Intent(getActivity(), EmptyActivity.class).putExtra("title", "设置提醒"));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.robot_binding_watch:
//                Toast.makeText(mActivity, "添加手表设备", Toast.LENGTH_SHORT).show();
                addWhatch();//添加手表设备
                break;
            case R.id.robot_top_conversation:

                break;
            case R.id.robot_top_monitor:

                break;
            case R.id.robot_top_add_robot:
//                Toast.makeText(mActivity, "添加机器人", Toast.LENGTH_SHORT).show();
                try {
                    addRobot(); //添加机器人
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.title_back:
                if (refreshRobotList) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("source", 1);
                    intent.putExtra("refresh_family", true);
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                } else {
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }
                break;
        }
    }

    public void refreshRobotList() {
        refreshRobotList = true;
        getRobotListFromService();
    }

    /*
    * 添加手表设备*/
    private void addWhatch() {
        intent = new Intent(mActivity, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_watch);
        intent.putExtra(Constant.WHATCH_MARK, Constant.WHATCH_BUDLING);
        intent.putExtra(Constant.WHATCH_Budling_From_markF, Constant.WHATCH_Budling_From_Robot);
        getActivity().startActivityForResult(intent, Constant.whatch_REQUEST_CODE);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /*
    * 添加绑定+机器人*/
    private void addRobot() {
        intent = new Intent(mActivity, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_robot);
        intent.putExtra(Constant.Robot_MARK, Constant.ROBOT_BUDLING);
        intent.putExtra(Constant.Robot_create_MARK, Constant.ROBOT_Add);
        startActivityForResult(intent, ROBOT_BIND);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    //String mRobotId = "ZK20C1494818532823";//机器人id
    // String robotSerial = "E2GA";// - 机器人序列号
    // String robotSerial = "YF86";// - 机器人序列号
    //String mUser = "13311023455";// - 用户手机号
    // String mRid = "121467";
    boolean isControlRobot = false; //是否要控制机器人
    int controlType = -1;// 1-video 2-monitor 3-robotsInfo

    /**
     * 给实时。平均、最高、统一设置为黑色
     */
    private void setColor() {
        high.setTextColor(getResources().getColor(R.color.black));
        low.setTextColor(getResources().getColor(R.color.black));
        average.setTextColor(getResources().getColor(R.color.black));
    }


//    RelativeLayout allBackg;

    // 头条新闻数据适配器
    class TopNewsAdapter extends PagerAdapter {

        // private BitmapUtils mBitmapUtils;

        public TopNewsAdapter() {
            // mBitmapUtils = new BitmapUtils(mActivity);
            // mBitmapUtils
            // .configDefaultLoadingImage(R.drawable.topnews_item_default);//
            // 设置加载中的默认图片
        }

        @Override
        public int getCount() {
            return mTopNews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mActivity, R.layout.robot_fg_top_vp, null);
            TextView name = (TextView) view.findViewById(R.id.robot_top_name);
            TextView robot_imei = (TextView) view.findViewById(R.id.robot_top_robot_imei);
            TextView robotState = (TextView) view.findViewById(R.id.robot_state);
            ImageView conversation = (ImageView) view.findViewById(R.id.robot_top_conversation);
            ImageView monitor = (ImageView) view.findViewById(R.id.robot_top_monitor);
            ImageView addImg = (ImageView) view.findViewById(R.id.robot_top_add_robot);
            addImg.setOnClickListener(RobotFg.this);
            conversation.setOnClickListener(RobotFg.this);
            monitor.setOnClickListener(RobotFg.this);
            RelativeLayout all = (RelativeLayout) view.findViewById(R.id.robot_top_all);
            addImg.setVisibility(View.GONE);
            all.setVisibility(View.GONE);
            if (!isHaveRobot) {
                addImg.setVisibility(View.VISIBLE);
            } else {
                all.setVisibility(View.VISIBLE);
                Robot result = mTopNews.get(position);
                if (result.getRobotState().equals("1")) {
                    robotState.setText("在线");
                } else {
                    robotState.setText("离线");
                }
                name.setText(result.getRname());
                robot_imei.setText(result.robot_imei);
                if (position == mTopNews.size() - 1) {
                    addImg.setVisibility(View.VISIBLE);
                    all.setVisibility(View.GONE);
                }
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void findView() {
        loadRl = (RelativeLayout) view.findViewById(R.id.robot_binding_rl_load);
        all = (LinearLayout) view.findViewById(R.id.robot_bottom_all_ll);
        equipment = (TextView) view.findViewById(R.id.robot_fg_equipment);
        remind = (TextView) view.findViewById(R.id.robot_fg_remind);

        mViewPager = (ViewPager) view.findViewById(R.id.robot_fg_vp);
        llRobotIndicator = (LinearLayout) view.findViewById(R.id.ll_robot_indicator);
        scan = (RelativeLayout) view.findViewById(R.id.robot_binding_watch);
        mmgh = (TextView) view.findViewById(R.id.robot_bottom_mmgh);
        time = (TextView) view.findViewById(R.id.robot_bottom_time);
        bpm = (TextView) view.findViewById(R.id.robot_bottom_bpm);
        low = (TextView) view.findViewById(R.id.robot_bottom_low);
        average = (TextView) view.findViewById(R.id.robot_bottom_average);
        high = (TextView) view.findViewById(R.id.robot_bottom_high);
        hours = (TextView) view.findViewById(R.id.robot_bottom_hours);
        grade = (TextView) view.findViewById(R.id.robot_bottom_grade);
        num = (TextView) view.findViewById(R.id.robot_bottom_num);
        returIv = (ImageView) view.findViewById(R.id.title_back);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        title_ok = (TextView) view.findViewById(R.id.title_right_tv);

        returIv.setOnClickListener(this);
        equipment.setOnClickListener(this);
        remind.setOnClickListener(this);
        scan.setOnClickListener(this);
        low.setOnClickListener(this);
        average.setOnClickListener(this);
        high.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //RobotManager.loginRobotOut();
    }
}
