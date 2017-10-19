package com.longcai.medical.ui.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcai.medical.R;
import com.longcai.medical.adapter.ArticleAdapter;
import com.longcai.medical.bean.ArticleListResult;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ZiXunActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.rl_article_lv)
    PullToRefreshListView rlArticleLv;

    private HashMap<String, String> map = new HashMap<>();
    private Unbinder unbinder;
    private List<ArticleListResult> articles = new ArrayList<>();
    private int currentPage = 1;
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        titleTv.setText("资讯");
        titleRightTv.setVisibility(View.GONE);
    }

    private void initData() {
        //设置可上拉加载和下拉刷新
        rlArticleLv.setMode(PullToRefreshBase.Mode.BOTH);
        //获取资讯列表
        getArticleList(currentPage);

        rlArticleLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //获取资讯列表
                currentPage = 1;
                getArticleList(currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //获取资讯列表
                currentPage++;
                getArticleList(currentPage);
            }
        });
    }

    //获取资讯列表
    private void getArticleList(final int currentPage) {
        map.put("page", Integer.toString(currentPage));
        HttpUtils.xOverallHttpPost(this, MyUrl.GET_ARTICLE_LIST, map, ArticleListResult.class, new HttpUtils.OnxHttpWithListResultCallBack<ArticleListResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(final List<ArticleListResult> articleList) {
                if (currentPage == 1) {
                    if (null != articles && articles.size() > 0) {
                        articles.clear();
                    }
                    articles.addAll(articleList);
                } else if (currentPage > 1) {
                    articles.addAll(articleList);
                }
                if (null == adapter) {
                    adapter = new ArticleAdapter(ZiXunActivity.this, articles);
                    rlArticleLv.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                rlArticleLv.onRefreshComplete();
                initListener(articles);
            }

            @Override
            public void onFail(int code, String msg) {
                if (rlArticleLv.isRefreshing()) {
                    rlArticleLv.onRefreshComplete();
                }
                if (code == 10150) {
                    ToastUtils.showToast(ZiXunActivity.this, "没有更多了~");
                }
            }
        });
    }

    private void initListener(final List<ArticleListResult> articleList) {
        rlArticleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d("PullToRefresh", position + "");
                ArticleListResult article = articleList.get(position - 1);
                startActivity(new Intent(ZiXunActivity.this, ZiXunInfoActivity.class)
                        .putExtra("article_result", article));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
