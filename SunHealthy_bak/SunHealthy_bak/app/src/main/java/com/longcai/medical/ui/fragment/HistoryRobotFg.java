package com.longcai.medical.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetMonitorBean;
import com.longcai.medical.bean.Robot;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.view.KCalendar;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.TimeUtil;
import com.longcai.medical.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * 历史记录界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class HistoryRobotFg extends BaseFragment implements OnClickListener {

    String TAG = "HistoryRobotFg";
    String mTimeFormat; //选择的日期（判断当前日期所在月的天数，用于上一天与下一天）
    private View view;
    int days; //当前月天数
    int nawDay;//今天
    int Tomorrow;  //下一天
    int lastDay;  //上一天
    String operationData;  //运算后的日期
    int mSelectYear; //选择日期所属当前年
    int firstNawYear; //选择日期所属当前年
    int mSelectMonth;//选择日期所属当前月
    int firstNawMonth;//选择日期所属当前月
    private List<Robot> mTopNews;
    private int onChangePosition = 0;

    private Intent intent;
    private ImageView last;
    private ImageView next;
    private int lastCurrentItem;
    private int nextCurrentItem;
    private TextView mDay;
    String mDate = null;// 设置默认选中的日期 格式为 “2014-04-05” 标准DATE格式
    private ImageView calendarIv;
    private Handler mHandler;
    String mThiDate = null;// 今天的年月日
    private TextView title;
    private boolean isHaveRobot = true;
    private ImageView rightIv;
    private ImageView bindImg;
    private RelativeLayout scan;
    private TextView mmgh;
    private TextView time;
    private TextView bpm;
    private TextView low;
    private TextView average;
    private TextView high;
    private TextView hours;
    private TextView grade;
    private TextView num;
    private GetMonitorBean.BloodDataBean bloodData;
    private GetMonitorBean.HeartDataBean heartData;
    private GetMonitorBean.SleepDataBean sleepData;
    private GetMonitorBean.SportDataBean sportData;
    LinearLayout all;
    private ImageView load;
    private AnimationDrawable aaLoad;
    private RelativeLayout loadRl;
    TextView titleTv, title_ok;
    ImageView returIv;
    private HashMap<String, String> map;
    private String family_member_id;

    public static HistoryRobotFg newInstance(String familyMemberId) {
        HistoryRobotFg fragment = new HistoryRobotFg();
        Bundle args = new Bundle();
        args.putString(Constant.FAMILY_MEMBER_ID,familyMemberId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView() {
        findView();
        if (getArguments() != null){
            family_member_id = getArguments().getString(Constant.FAMILY_MEMBER_ID);
        }
        return view;
    }

    @Override
    public void initData() {
        getNowData();
        initHandler();
        titleTv.setText("历史记录");
        title_ok.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        getNowData();
    }

    private void findView() {

        view = View.inflate(mActivity, R.layout.robot_fg_history, null);
        last = (ImageView) view.findViewById(R.id.history_robot_fg_last);
        next = (ImageView) view.findViewById(R.id.history_robot_fg_next);
        mDay = (TextView) view.findViewById(R.id.history_robot_fg_day);
        calendarIv = (ImageView) view
                .findViewById(R.id.history_robot_fg_calendar_iv);
        title = (TextView) view.findViewById(R.id.history_robot_fg_title);
        loadRl = (RelativeLayout) view.findViewById(R.id.robot_binding_rl_load);
        all = (LinearLayout) view.findViewById(R.id.robot_bottom_all_ll);
        load = (ImageView) view.findViewById(R.id.robot_binding_load);

        scan = (RelativeLayout) view.findViewById(R.id.robot_binding_watch);
        bindImg = (ImageView) view.findViewById(R.id.robot_binding_watch_iv);
//        bindImg.setVisibility(View.GONE);
        returIv = (ImageView) view.findViewById(R.id.title_back);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        title_ok = (TextView) view.findViewById(R.id.title_right_tv);
        returIv.setOnClickListener(this);

        mmgh = (TextView) view.findViewById(R.id.robot_bottom_mmgh);
        time = (TextView) view.findViewById(R.id.robot_bottom_time);
        bpm = (TextView) view.findViewById(R.id.robot_bottom_bpm);
        low = (TextView) view.findViewById(R.id.robot_bottom_low);
        average = (TextView) view.findViewById(R.id.robot_bottom_average);
        high = (TextView) view.findViewById(R.id.robot_bottom_high);
        hours = (TextView) view.findViewById(R.id.robot_bottom_hours);
        grade = (TextView) view.findViewById(R.id.robot_bottom_grade);
        num = (TextView) view.findViewById(R.id.robot_bottom_num);

        scan.setOnClickListener(this);
        low.setOnClickListener(this);
        average.setOnClickListener(this);
        high.setOnClickListener(this);
        last.setOnClickListener(this);
        next.setOnClickListener(this);
        calendarIv.setOnClickListener(this);
        bindImg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        // TODO onClick

        switch (v.getId()) {
            case R.id.history_robot_fg_calendar_iv:
                new PopupWindows(mActivity, calendarIv);

                break;
            case R.id.history_robot_fg_last:
                //ToastUtils.showToast(getActivity(), "上一天");
                setLastDay();//上一天
                break;
            case R.id.history_robot_fg_next:
                //ToastUtils.showToast(getActivity(), "下一天");
                setNextDay();//下一天

                break;
            case R.id.robot_bottom_high:
                bpm.setText(heartData.getMax_heart()); // 心率
                setColor();
                high.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.robot_bottom_low:
                bpm.setText(heartData.getMin_heart()); // 心率
                setColor();
                low.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.robot_bottom_average:
                setColor();
                bpm.setText(heartData.getAvg_heart()); // 心率
                average.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.title_back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.robot_binding_watch_iv:
                Intent intent = new Intent(mActivity, CaptureActivity.class);
                intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_watch);
                intent.putExtra(Constant.WHATCH_MARK, Constant.WHATCH_BUDLING);
                intent.putExtra(Constant.WHATCH_Budling_From_markF, Constant.WHATCH_Budling_From_History);
                startActivityForResult(intent, Constant.whatch_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.WhatchBudlingSuccessToHistory_CODE) {
            // TODO
            getNowData();
        }
    }

    private void setNextDay() {
        Tomorrow = nawDay + 1;  //下一天
        if (mSelectMonth == firstNawMonth) {
            if (!(Tomorrow < firstNawDay)) {
                next.setVisibility(View.GONE);
            }
        }
        if (!(Tomorrow > days)) {
            nawDay = Tomorrow;
            mDay.setText(nawDay + "");
            title.setText(mSelectYear + "年" + mSelectMonth + "月");
            operationData = mSelectYear + "-" + mSelectMonth + "-" + nawDay + "";
            Log.i(TAG, "setNextDay: ");
            Log.i(TAG, "setNextDay: operationData" + operationData);
            String timeStamp = StringUtils.getTime2(operationData);
            Log.i(TAG, "setNextDay: timeStamp  " + timeStamp);
            getHealthyHistoryDataFromService(timeStamp);

        }
    }

    int firstNawDay;

    //上一天
    private void setLastDay() {
        lastDay = nawDay - 1;
        if (lastDay > 0) {
            if (lastDay < firstNawDay) {
                next.setVisibility(View.VISIBLE);
            }
            nawDay = lastDay;
            mDay.setText(nawDay + "");
            title.setText(mSelectYear + "年" + mSelectMonth + "月");
            operationData = mSelectYear + "-" + mSelectMonth + "-" + nawDay + "";
            Log.i(TAG, "setLastDay: operationData" + operationData);
            String timeStamp = StringUtils.getTime2(operationData);
            Log.i(TAG, "setLastDay: timeStamp  " + timeStamp);
            getHealthyHistoryDataFromService(timeStamp);
        }
    }
    /**
     * 从网络获取历史健康数据
     */
    private void getHealthyHistoryDataFromService(String dataTime) {
        map = new HashMap<>();
        // TODO Auto-generated method stub
        load.setBackgroundResource(R.drawable.loading);
        aaLoad = (AnimationDrawable) load.getBackground();
        aaLoad.start();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("data_time", dataTime);
        if (family_member_id != null){
            map.put("family_member_id",family_member_id);
        }
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.HEALTH_MONITOR_HISTORY, map, GetMonitorBean.class, new HttpUtils.OnxHttpCallBack<GetMonitorBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetMonitorBean health) {
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.two;
                msg.obj = health;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFail(int code, String msg) {
                if (aaLoad != null) {
                    aaLoad.stop();
                }
                // 还没有绑定手表
                LogUtils.i(TAG, "processHealthData   code==-3");
                loadRl.setVisibility(View.GONE);
                all.setVisibility(View.GONE);
                scan.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 初始化handler
     */
    private void initHandler() {
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constant.zero:
                        mTimeFormat = (String) msg.obj;
                        mSelectYear = getNowYear(mTimeFormat);
                        mSelectMonth = getNowMonth(mTimeFormat);
                        nawDay = getNowDay(mTimeFormat);//今天
                        //大于当前年份
                        if (mSelectYear > firstNawYear) {
                            next.setVisibility(View.GONE);
                            mSelectYear = firstNawYear;
                            mSelectMonth = firstNawMonth;
                            Toast.makeText(mActivity, "请选择今天之前的日期", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //当年
                        if (mSelectYear == firstNawYear) {
                            if (mSelectMonth > firstNawMonth) {//大于当前月份
                                next.setVisibility(View.GONE);
                                mSelectYear = firstNawYear;
                                mSelectMonth = firstNawMonth;
                                Toast.makeText(mActivity, "请选择今天之前的日期", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (mSelectMonth == firstNawMonth) {//当月
                                next.setVisibility(View.VISIBLE);
                                if (nawDay > firstNawDay) { //大于当天
                                    next.setVisibility(View.GONE);
                                    mSelectYear = firstNawYear;
                                    mSelectMonth = firstNawMonth;
                                    Toast.makeText(mActivity, "请选择今天之前的日期", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (nawDay < firstNawDay) { //小于当天
                                    next.setVisibility(View.VISIBLE);
                                }
                                if (!(Tomorrow < firstNawDay)) { //明天大于今天
                                    next.setVisibility(View.VISIBLE);
                                }
                            } else if (mSelectMonth < firstNawMonth) {//小于当前月份
                                next.setVisibility(View.VISIBLE);
                            }
                        }
                        //小于当前年份
                        if (mSelectYear < firstNawYear) {
                            next.setVisibility(View.VISIBLE);
                        }
                        mDay.setText(nawDay + "");
                        title.setText(mSelectYear + "年" + mSelectMonth + "月");
                        days = StringUtils.getDaysOfMonth(mTimeFormat); //当前月天数
                        setTimeAndGetHealthy(mTimeFormat); //选择了日期后赋值并访问当天的健康数据
                        break;
                    case Constant.two:
                        if (msg.obj != null) {
                            all.setVisibility(View.VISIBLE);
                            scan.setVisibility(View.GONE);
                            GetMonitorBean getMonitor = (GetMonitorBean) msg.obj;
                            setHealthDataToView(getMonitor);//给控件赋值健康数据
                        }
                        break;
                }
            }
        };
    }


    /*得到当前年*/
    private int getNowYear(String time) {
        int nawYear = Integer.parseInt(time.substring(0, time.indexOf("-")));
        LogUtils.i(TAG, "nawYear" + nawYear + "");
        return nawYear;
    }    /*得到当前月*/

    private int getNowMonth(String time) {
        int nawMonth = Integer.parseInt(time.substring(time.indexOf("-") + 1,
                time.lastIndexOf("-")));
        LogUtils.i(TAG, "nawMonth" + nawMonth + "");
        return nawMonth;
    }    /*得到当前年*/

    private int getNowDay(String time) {
        int day = Integer.parseInt(time.substring(time.lastIndexOf("-") + 1,
                time.length()));
        LogUtils.i(TAG, "day" + day + "");
        return day;
    }

    /*选择日期后的赋值与获取数据*/
    private void setTimeAndGetHealthy(String time) {

        String timeFormat = StringUtils.getTime2(time);
        int years = getNowYear(time);
        int month = getNowMonth(time);
        int date = getNowDay(time);
        title.setText(years + "年" + month + "月");
        mDay.setText(date + "");
        Log.i(TAG, "setTimeAndGetHealthy: timeFormat " + timeFormat);
        getHealthyHistoryDataFromService(timeFormat);
    }

    /**
     * 给控件赋值健康数据
     */
    private void setHealthDataToView(GetMonitorBean health) {
        if (aaLoad != null) {
            aaLoad.stop();
        }
        loadRl.setVisibility(View.GONE);
        all.setVisibility(View.VISIBLE);
        bloodData = health.getBlood_data();
        heartData = health.getHeart_data();
        sleepData = health.getSleep_data();
        sportData = health.getSport_data();
        time.setText(bloodData.getCreate_time());
        mmgh.setText(bloodData.getSystolic() + "/" + bloodData.getDiastolic()); // 血压
        bpm.setText(heartData.getAvg_heart()); // 心率
        String sleepTime = TimeUtil.formatDuring(Long
                .parseLong(sleepData.getSleep_time()));
        hours.setText(sleepTime); // 睡眠时间
        num.setText(sportData.getStep_num()); // 步数
        String sleepGrade = "";
        switch (sleepData.getSleep_grade()) {
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
        grade.setText(sleepGrade);// 睡眠等级
    }

    /**
     * 获取当前日期（年月日）
     */
    public void getNowData() {
        Calendar CD = Calendar.getInstance();
        int YY = CD.get(Calendar.YEAR);
        int MM = CD.get(Calendar.MONTH) + 1;
        int DD = CD.get(Calendar.DATE);
        int HH = CD.get(Calendar.HOUR);
        int NN = CD.get(Calendar.MINUTE);
        int SS = CD.get(Calendar.SECOND);
        int MI = CD.get(Calendar.MILLISECOND);

        Calendar cal = Calendar.getInstance();

        // 当前年
        int year = cal.get(Calendar.YEAR);
        // 当前月
        int month = (cal.get(Calendar.MONTH)) + 1;
        // 当前月的第几天：即当前日
        int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
        // 当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        // 当前分
        int minute = cal.get(Calendar.MINUTE);
        // 当前秒
        int second = cal.get(Calendar.SECOND);
        // 0-上午；1-下午
        int ampm = cal.get(Calendar.AM_PM);
        // 当前年的第几周
        int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);
        // 当前月的第几周
        int week_of_month = cal.get(Calendar.WEEK_OF_MONTH);
        // 当前年的第几天
        int day_of_year = cal.get(Calendar.DAY_OF_YEAR);

        mDay.setText(day_of_month + "");
        title.setText(year + "年" + month + "月");
        String data = year + "-" + month + "-" + day_of_month + "";
        mTimeFormat = data;

        days = StringUtils.getDaysOfMonth(mTimeFormat); //当前月天数
        firstNawYear = getNowYear(mTimeFormat);//当前月
        mSelectYear = firstNawYear;
        firstNawMonth = getNowMonth(mTimeFormat);//当前月
        mSelectMonth = firstNawMonth;
        firstNawDay = getNowDay(mTimeFormat);//今天
        nawDay = firstNawDay;

        LogUtils.i(TAG, "getNowData:  " + data);
        String timeStamp = StringUtils.getTime2(data);
        Log.i(TAG, "getNowData: timeStamp  " + timeStamp);
        getHealthyHistoryDataFromService(timeStamp);

    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.popupwindow_calendar,
                    null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_in));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_1));

            setWidth(LayoutParams.MATCH_PARENT);
            setHeight(LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            final TextView popupwindow_calendar_month = (TextView) view
                    .findViewById(R.id.popupwindow_calendar_month);
            final KCalendar calendar = (KCalendar) view
                    .findViewById(R.id.popupwindow_calendar);
            Button popupwindow_calendar_bt_enter = (Button) view
                    .findViewById(R.id.popupwindow_calendar_bt_enter);

            popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
                    + calendar.getCalendarMonth() + "月"
                    + calendar.getCalendarDate() + "日");

            if (null != mDate) {

                int years = Integer.parseInt(mDate.substring(0,
                        mDate.indexOf("-")));
                int month = Integer.parseInt(mDate.substring(
                        mDate.indexOf("-") + 1, mDate.lastIndexOf("-")));
                popupwindow_calendar_month.setText(years + "年" + month + "月");

                calendar.showCalendar(years, month);
                calendar.setCalendarDayBgColor(mDate,
                        R.drawable.calendar_date_focused);
            }

            List<String> list = new ArrayList<String>(); // 设置标记列表
            list.add("2014-04-01");
            list.add("2014-04-02");
            calendar.addMarks(list, 0);

            // 监听所选中的日期
            calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

                public void onCalendarClick(int row, int col, String dateFormat) {
                    int month = Integer.parseInt(dateFormat.substring(
                            dateFormat.indexOf("-") + 1,
                            dateFormat.lastIndexOf("-")));

                    if (calendar.getCalendarMonth() - month == 1// 跨年跳转
                            || calendar.getCalendarMonth() - month == -11) {
                        calendar.lastMonth();

                    } else if (month - calendar.getCalendarMonth() == 1 // 跨年跳转
                            || month - calendar.getCalendarMonth() == -11) {
                        calendar.nextMonth();

                    } else {
                        calendar.removeAllBgColor();
                        calendar.setCalendarDayBgColor(dateFormat,
                                R.drawable.calendar_date_focused);
                        mDate = dateFormat;// 最后返回给全局 date
                        System.out.println(mDate);
                    }
                }
            });

            // 监听当前月份
            calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
                public void onCalendarDateChanged(int year, int month) {
                    popupwindow_calendar_month
                            .setText(year + "年" + month + "月");
                }
            });

            // 上月监听按钮
            RelativeLayout popupwindow_calendar_last_month = (RelativeLayout) view
                    .findViewById(R.id.popupwindow_calendar_last_month);
            popupwindow_calendar_last_month
                    .setOnClickListener(new OnClickListener() {

                        public void onClick(View v) {
                            calendar.lastMonth();
                        }

                    });

            // 下月监听按钮
            RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) view
                    .findViewById(R.id.popupwindow_calendar_next_month);
            popupwindow_calendar_next_month
                    .setOnClickListener(new OnClickListener() {

                        public void onClick(View v) {
                            calendar.nextMonth();
                        }
                    });

            // 关闭窗口
            popupwindow_calendar_bt_enter
                    .setOnClickListener(new OnClickListener() {

                        public void onClick(View v) {
                            dismiss();
                            if (mDate != null) {
                                Message msg = mHandler.obtainMessage();
                                msg.what = Constant.zero;
                                msg.obj = mDate;
                                mHandler.sendMessage(msg);
                            }

                        }
                    });
        }
    }



    /**
     * 给实时。平均、最高、统一设置为黑色
     */
    private void setColor() {
        // TODO Auto-generated method stub
        high.setTextColor(getResources().getColor(R.color.black));
        low.setTextColor(getResources().getColor(R.color.black));
        average.setTextColor(getResources().getColor(R.color.black));

    }
}
