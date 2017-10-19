package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.HealthDataPagerAdapter;
import com.longcai.medical.adapter.IndexAdapter;
import com.longcai.medical.adapter.IndexAdvertPagerAdapter;
import com.longcai.medical.bean.AdvertListResult;
import com.longcai.medical.bean.AdvertResult;
import com.longcai.medical.bean.ArticleListResult;
import com.longcai.medical.bean.GetLocationBean;
import com.longcai.medical.bean.GetMonitorBean;
import com.longcai.medical.bean.IndexResult;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.rob.ui.RobotHomeActivity;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.AMapActivity;
import com.longcai.medical.ui.activity.EmptyActivity;
import com.longcai.medical.ui.activity.GloabeActivityFirst;
import com.longcai.medical.ui.activity.GloabeActivitySecond;
import com.longcai.medical.ui.activity.LoginActivity;
import com.longcai.medical.ui.activity.index.ZiXunActivity;
import com.longcai.medical.ui.activity.index.ZiXunInfoActivity;
import com.longcai.medical.ui.activity.person.PersonDeviceActivity;
import com.longcai.medical.ui.view.HealthDataView;
import com.longcai.medical.utils.FileUtils;
import com.longcai.medical.utils.GsonUtils;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.ToastUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/3.
 * 首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.refresh_view)
    PullToRefreshListView refresh_view;
    @BindView(R.id.title_tv)
    TextView txtTitle;
    @BindView(R.id.relative_title)
    AutoRelativeLayout txtTitleBg;

    // top advert
    RelativeLayout rlHealthAdvert;
    ViewPager vpHealthAdvert;
    LinearLayout llAdvertIndicator;
    ImageView ivAdvertPlaceholder;
    // button
    RelativeLayout rlRobot;
    RelativeLayout rlReport;
    // health data banner
    RelativeLayout rlHealthBanner;
    LinearLayout llHealthIndicator;
    ViewPager vpHealthData;
    ImageView ivDot1;
    ImageView ivDot2;
    ImageView ivDot3;
    ImageView ivDot4;
    // watch data
    LinearLayout llHealthWatchData;
    RelativeLayout rlMap;
    RelativeLayout rlHistory;
    // data placeholder
    TextView tvDataPlaceholder;
    // infomation
    RelativeLayout rlInfoRead;

    @BindView(R.id.img_shader)
    ImageView imgShader;

    private Unbinder unbinder;
    private View headerView;
    private int currentHealthDataItem;
    private List<ArticleListResult> articleList = new ArrayList<>();
    private int currentPage = -1;
    private IndexAdapter indexAdapter;
    private boolean isHaveWhatch = false;
//    private String goods_commonid;
//    private String goods_commonid2;
    private boolean isVisibleToUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        ToastUtils.showToast(getActivity(), "isVisibleToUser" + isVisibleToUser);
        LogUtils.d("setUserVisibleHint-Home", String.valueOf(isVisibleToUser));
        try {
            if (isVisibleToUser) {
                mH.postDelayed(healthDataRunnable, 2500);
//                refreshMessage();
            } else {
                mH.removeCallbacks(healthDataRunnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        txtTitle.setAlpha(0);
        txtTitleBg.setAlpha(0);
        initHeader();
        initRefreshView();
        getHeadlthDataFromLocal();
        getIndex();
    }

    private void initRefreshView() {
        final ListView listView = refresh_view.getRefreshableView();
        refresh_view.setMode(PullToRefreshBase.Mode.BOTH);
        refresh_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                txtTitle.setAlpha(0);
                txtTitleBg.setAlpha(0);
                imgShader.setVisibility(View.GONE);
                getIndex();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getArticleList();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int location[] = new int[2];
                headerView.getLocationInWindow(location);
                int headerViewToTop = location[1];
                int bannerHeight = rlHealthAdvert.getHeight();
                float fraction = Math.abs((float) headerViewToTop / bannerHeight);
                LogUtils.d("headerViewToTop-" + headerViewToTop);
                LogUtils.d("bannerHeight-" + bannerHeight);
                LogUtils.d("fraction-" + fraction);
                if (fraction >= 0 && fraction < 1) {
                    txtTitle.setAlpha(fraction);
                    txtTitleBg.setAlpha(fraction);
                    imgShader.setVisibility(View.GONE);
                }
                if (fraction < 0) {
                    txtTitle.setAlpha(0);
                    txtTitleBg.setAlpha(0);
                    imgShader.setVisibility(View.GONE);
                }
                if (fraction >= 1) {
                    txtTitle.setAlpha(1);
                    txtTitleBg.setAlpha(1);
                    imgShader.setVisibility(View.VISIBLE);
                }
            }
        });
        listView.addHeaderView(headerView);
        indexAdapter = new IndexAdapter(articleList, getActivity());
        refresh_view.setAdapter(indexAdapter);
    }

    private void initHeader() {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        headerView = View.inflate(getActivity(), R.layout.fragment_home_top, null);
        rlHealthAdvert = (RelativeLayout) headerView.findViewById((R.id.rl_health_advert));
        vpHealthAdvert = (ViewPager) headerView.findViewById(R.id.vp_health_advert);
        llAdvertIndicator = (LinearLayout) headerView.findViewById(R.id.ll_advert_indicator);
        ivAdvertPlaceholder = (ImageView) headerView.findViewById(R.id.iv_advert_placeholder);
        // button
        rlRobot = (RelativeLayout) headerView.findViewById(R.id.rl_robot);
        rlRobot.setOnClickListener(this);
        rlReport = (RelativeLayout) headerView.findViewById(R.id.rl_report);
        rlReport.setOnClickListener(this);
        // health data banner
        rlHealthBanner = (RelativeLayout) headerView.findViewById(R.id.rl_health_banner);
        llHealthIndicator = (LinearLayout) headerView.findViewById(R.id.ll_health_indicator);
        vpHealthData = (ViewPager) headerView.findViewById(R.id.vp_health_data);
        ivDot1 = (ImageView) headerView.findViewById(R.id.iv_dot1);
        ivDot2 = (ImageView) headerView.findViewById(R.id.iv_dot2);
        ivDot3 = (ImageView) headerView.findViewById(R.id.iv_dot3);
        ivDot4 = (ImageView) headerView.findViewById(R.id.iv_dot4);
        // watch data
        llHealthWatchData = (LinearLayout) headerView.findViewById(R.id.ll_health_watch_data);
        rlMap = (RelativeLayout) headerView.findViewById(R.id.rl_map);
        rlMap.setOnClickListener(this);
        rlHistory = (RelativeLayout) headerView.findViewById(R.id.rl_history);
        rlHistory.setOnClickListener(this);
        // data placeholder\
        tvDataPlaceholder = (TextView) headerView.findViewById(R.id.tv_data_placeholder);
        tvDataPlaceholder.setOnClickListener(this);
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            tvDataPlaceholder.setVisibility(View.VISIBLE);
        } else {
            tvDataPlaceholder.setVisibility(View.GONE);
        }
        // infomation
        rlInfoRead = (RelativeLayout) headerView.findViewById(R.id.rl_info_read);
        rlInfoRead.setOnClickListener(this);
        headerView.setLayoutParams(layoutParams);
    }

//    private void setProductData(List<GetInfoGoodsResult> results) {
//        try {
//            GetInfoGoodsResult result1 = results.get(1);
//            goods_commonid = result1.getGoods_commonid();
//            Glide.with(getActivity()).load(result1.getGoods_image()).fitCenter().placeholder(R.mipmap.chanpinxiangqing_zhanweitu).into(imgRobot);
//            txtProductName.setText(result1.getGoods_name());
//            txtProductMoney.setText("￥" + result1.getGoods_price());
//
//            GetInfoGoodsResult result2 = results.get(0);
//            goods_commonid2 = result2.getGoods_commonid();
//            Glide.with(getActivity()).load(result2.getGoods_image()).fitCenter().placeholder(R.mipmap.chanpinxiangqing_zhanweitu).into(imgRobot2);
//            txtProductName2.setText(result2.getGoods_name());
//            txtProductMoney2.setText("￥" + result2.getGoods_price());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // TODO refresh health data
    public void refreshHealthData() {
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            tvDataPlaceholder.setVisibility(View.VISIBLE);
            tvDataPlaceholder.setOnClickListener(this);
        } else {
            initHealthData();
        }
    }

//    public void refreshMessage() {
//        if (null != getActivity()) {
//            ((MainActivity) getActivity()).refreshMsg();
//        }
//    }

    //获取健康信息
    private void initHealthData() {
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_member_id", "");
        HttpUtils.xOverallHttpPostWithoutDialog(getActivity(), MyUrl.HEALTH_MONITOR, map, GetMonitorBean.class, new
                HttpUtils.OnxHttpCallBack<GetMonitorBean>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {
                    }

                    @Override
                    public void onSuccess(GetMonitorBean getMonitor) {
                        if (getMonitor != null) {
                            FileUtils.writeToFile(getActivity(), "watch_data", GsonUtils.GsonString(getMonitor));
                            setHealthData(getMonitor);
                            tvDataPlaceholder.setVisibility(View.GONE);
                            tvDataPlaceholder.setOnClickListener(HomeFragment.this);
                            isHaveWhatch = true;
                            MyApplication.myPreferences.saveHavaWatch(isHaveWhatch);
                        }
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        if (code == 11252) {
                            try {
                                setHealthData(null);
                                tvDataPlaceholder.setVisibility(View.VISIBLE);
                                tvDataPlaceholder.setOnClickListener(HomeFragment.this);
                                isHaveWhatch = false;
                                MyApplication.myPreferences.saveHavaWatch(isHaveWhatch);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void getHeadlthDataFromLocal() {
        String healthDataStr = FileUtils.readFromFile(getActivity(), "watch_data");
        if (!TextUtils.isEmpty(healthDataStr)) {
            LogUtils.d("HealthDataFromLocal", healthDataStr);
            GetMonitorBean getMonitor = GsonUtils.GsonToBean(healthDataStr, GetMonitorBean.class);
            setHealthData(getMonitor);
        }
    }

    private void setHealthData(GetMonitorBean getMonitor) {
        String systolic = "0";
        String diastolic = "0";
        String avg_heart = "0";
        String sleep_time = "0";
        String step_num = "0";
        if (null != getMonitor) {
            systolic = getMonitor.getBlood_data().getSystolic();
            diastolic = getMonitor.getBlood_data().getDiastolic();
            avg_heart = getMonitor.getHeart_data().getAvg_heart();
            sleep_time = getMonitor.getSleep_data().getSleep_time();
            step_num = getMonitor.getSport_data().getStep_num();
        }
        FileUtils.writeToFile(getActivity(), "watch_data", GsonUtils.GsonString(getMonitor));
        final List<HealthDataView> views = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HealthDataView view = new HealthDataView(getActivity());
            //步数 睡眠 心率 血压
            if (i == 0) {
                view.setIcon(R.mipmap.icon_step);
                view.setName("步数");
                view.setValue(step_num);
                view.setUnit("步");
            } else if (i == 1) {
                view.setIcon(R.mipmap.icon_sleep);
                view.setName("睡眠");
                view.setValue(sleep_time);
                view.setUnit("h");
            } else if (i == 2) {
                view.setIcon(R.mipmap.icon_heart_rate);
                view.setName("心率");
                view.setValue(avg_heart);
                view.setUnit("bmp");
            } else if (i == 3) {
                view.setIcon(R.mipmap.icon_blood_pressure);
                view.setName("血压");
                view.setValue(systolic + "/" + diastolic);
                view.setUnit("mmgh");
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            views.add(view);
        }
        HealthDataPagerAdapter adapter = new HealthDataPagerAdapter(views);
        vpHealthData.setAdapter(adapter);
        vpHealthData.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        currentHealthDataItem = 0;
                        initDots();
                        ivDot1.setImageResource(R.mipmap.dot_selected);
                        break;
                    case 1:
                        currentHealthDataItem = 1;
                        initDots();
                        ivDot2.setImageResource(R.mipmap.dot_selected);
                        break;
                    case 2:
                        currentHealthDataItem = 2;
                        initDots();
                        ivDot3.setImageResource(R.mipmap.dot_selected);
                        break;
                    case 3:
                        currentHealthDataItem = 3;
                        initDots();
                        ivDot4.setImageResource(R.mipmap.dot_selected);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        currentHealthDataItem = 0;
        vpHealthData.setCurrentItem(0, false);
    }

    private void getIndex() {
        HttpUtils.xOverallHttpPostWithoutDialog(getActivity(), MyUrl.INDEX, new HashMap<String, String>(), IndexResult.class, new HttpUtils.OnxHttpCallBack<IndexResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(IndexResult result) {
                mH.sendEmptyMessage(100);
                LogUtils.d("Index", result.toString());
                setIndex(result);
            }

            @Override
            public void onFail(int code, String msg) {
                try {
                    if (refresh_view.isRefreshing()) {
                        refresh_view.onRefreshComplete();
                    }
                    indexAdapter = new IndexAdapter(articleList, getActivity());
                    refresh_view.setAdapter(indexAdapter);
//                    refresh_view.setOnItemClickListener(HomeFragment.this);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    getIndex();
                }
            }
        });
    }

    public void setIndex(IndexResult result) {
        try {
            if (null != result) {
//                setTopAdvert(result);
                if (null != articleList && articleList.size() > 0) {
                    articleList.clear();
                }
                if (null != result.getArticle_list()) {
                    articleList.addAll(result.getArticle_list());
                }
                indexAdapter = new IndexAdapter(articleList, getActivity());
                refresh_view.setAdapter(indexAdapter);
                refresh_view.setOnItemClickListener(HomeFragment.this);
                currentPage = 1;
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    setHealthData(null);
                } else {
                    initHealthData();
                }
            } else {
                indexAdapter = new IndexAdapter(articleList, getActivity());
                refresh_view.setAdapter(indexAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getArticleList() {
        currentPage++;
        HashMap<String, String> map = new HashMap<>();
        map.put("page", Integer.toString(currentPage));
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.GET_ARTICLE_LIST, map, ArticleListResult.class, new HttpUtils.OnxHttpWithListResultCallBack<ArticleListResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(final List<ArticleListResult> resultList) {
                // TODO
                if (null != articleList) {
                    articleList.addAll(resultList);
                    if (null != indexAdapter) {
                        indexAdapter.notifyDataSetChanged();
                    }
                    mH.sendEmptyMessage(100);
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (null != refresh_view && refresh_view.isRefreshing()) {
                    refresh_view.onRefreshComplete();
                }
                if (code == 10150) {
                    ToastUtils.showToast(getContext(), "没有更多了~");
                }
            }
        });
    }

    private void setTopAdvert(IndexResult result) {
        final AdvertListResult startAdvert = result.getBanner_advert();
        List<AdvertResult> adverts = null;
        if (null != startAdvert) {
            adverts = startAdvert.getAdvert();
        }
        if (null != adverts && adverts.size() > 0) {
            rlHealthAdvert.setVisibility(View.VISIBLE);
            ivAdvertPlaceholder.setVisibility(View.GONE);
            if (null != llAdvertIndicator && llAdvertIndicator.getChildCount() > 0) {
                llAdvertIndicator.removeAllViews();
            }
            List<ImageView> advertList = new ArrayList<ImageView>();
            for (int i = 0, size = adverts.size(); i < size; i++) {
                AdvertResult advertResult = adverts.get(i);
                ImageView advertImg = new ImageView(getActivity());
                advertImg.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).load(advertResult.getAdv_pic()).fitCenter().placeholder(R.mipmap.home_top).into(advertImg);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                advertImg.setLayoutParams(params);
                advertList.add(advertImg);
                if (size > 1) {
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
                    llAdvertIndicator.addView(indicator);
                }
            }
            if (advertList.size() > 0) {
                IndexAdvertPagerAdapter adapter = new IndexAdvertPagerAdapter(advertList);
                vpHealthAdvert.setAdapter(adapter);
                vpHealthAdvert.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        int count = llAdvertIndicator.getChildCount();
                        for (int i = 0; i < count; i++) {
                            ImageView dotView = (ImageView) llAdvertIndicator.getChildAt(i);
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
        } else {
            rlHealthAdvert.setVisibility(View.GONE);
            ivAdvertPlaceholder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("onResume-Home", "onResume");
        mH.postDelayed(healthDataRunnable, 2500);
        if (isVisibleToUser) {
            refreshHealthData();
        }
    }

    private void initDots() {
        ivDot1.setImageResource(R.mipmap.dot_default);
        ivDot2.setImageResource(R.mipmap.dot_default);
        ivDot3.setImageResource(R.mipmap.dot_default);
        ivDot4.setImageResource(R.mipmap.dot_default);
    }

    private Runnable healthDataRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentHealthDataItem == 3) {
                currentHealthDataItem = 0;
                if (null != vpHealthData) {
                    vpHealthData.setCurrentItem(currentHealthDataItem, true);
                }
            } else {
                currentHealthDataItem++;
                if (null != vpHealthData) {
                    vpHealthData.setCurrentItem(currentHealthDataItem, true);
                }
            }
            mH.sendEmptyMessage(110);
        }
    };

    private Handler mH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (refresh_view != null) {
                        if (refresh_view.isRefreshing()) {
                            refresh_view.onRefreshComplete();
                        }
                    }
                    break;
                case 110:
                    mH.postDelayed(healthDataRunnable, 2500);
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d("onPause-Home", "onPause");
        mH.removeCallbacks(healthDataRunnable);
    }

    @Override
    public void LoadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == rlRobot) {
            if (MyApplication.myPreferences.readToken().equals("-1")) {
                login();
            } else {
                // 机器人
//                Intent robotIntent = new Intent(getActivity(), GloabeActivityFirst.class);
//                robotIntent.putExtra(Constant.FRAGMENT_MARK, Constant.ROBOT_FG);
//                startActivity(robotIntent);

                Intent robotIntent = new Intent(getActivity(), RobotHomeActivity.class);
                startActivity(robotIntent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        } else if (view == rlReport) {
            if (MyApplication.myPreferences.readToken().equals("-1")) {
                login();
            } else {
                // 健康报告
                startActivity(new Intent(getActivity(), EmptyActivity.class).putExtra("title", "健康报告"));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        } else if (view == rlMap) {
            if (MyApplication.myPreferences.readToken().equals("-1")) {
                login();
            } else {
                // 我的位置(先获取手表数据)
                if (isHaveWhatch) {
                    getLocation();
                    // openMap("37.583092", "112.685562", "我的位置");
                } else {
                    Toast.makeText(mActivity, "您还没有绑定手表设备", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (view == rlHistory) {
            if (MyApplication.myPreferences.readToken().equals("-1")) {
                login();
            } else {
                // 历史记录
                startActivity(new Intent(getActivity(), GloabeActivitySecond.class)
                        .putExtra(Constant.FRAGMENT_MARK, Constant.HISTORY_FG)
                        .putExtra(Constant.FAMILY_MEMBER_ID, "-1"));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        } else if (view == rlInfoRead) {
            // 资讯
            startActivity(new Intent(getActivity(), ZiXunActivity.class));
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        else if (view == tvDataPlaceholder) {
            if (MyApplication.myPreferences.readToken().equals("-1")) {
                login();
            } else {
                Intent intent = new Intent(getActivity(), PersonDeviceActivity.class);
                startActivity(intent.putExtra("fromHome", true));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        }
    }

    private void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        MyApplication.source = 1;
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.pop_enter_anim, 0);
    }

    /**
     * 获取手表位置信息
     */
    private void getLocation() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.WHATCH_LOCATION, map, GetLocationBean.class, new
                HttpUtils.OnxHttpCallBack<GetLocationBean>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {
                    }

                    @Override
                    public void onSuccess(GetLocationBean getLocation) {
                        /*得到了经纬度*/
                        try {
                            String lat = getLocation.getLat();
                            String lng = getLocation.getLng();
                            openMap(lng, lat, Constant.Watch_Location);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int code, String msg) {
                    }
                });
    }

    /**
     * 打开地图
     */
    private void openMap(String longitude, String latitude, String name) {
        // mLongitude = "37.583092";
        // mLatitude = "112.685562";

        Intent intent = new Intent(mActivity, AMapActivity.class);
        intent.putExtra(Constant.LONGITUDE, longitude);
        intent.putExtra(Constant.LATITUDE, latitude);
        intent.putExtra(Constant.NAME, name);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            // position start index 2
            LogUtils.d("onItemClick-position", i + "");
            ArticleListResult article = articleList.get(i - 2);
            startActivity(new Intent(getActivity(), ZiXunInfoActivity.class).putExtra("article_result", article));
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
