package com.longcai.medical.ui.fragment;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.SearchFamilyAdapter;
import com.longcai.medical.bean.Search;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 家庭搜索界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class FamilySearchFg extends BaseFragment implements OnClickListener, SearchFamilyAdapter.IApplyFamily {

    private List<Search> searchLists;
    private ListView lv;
    private RelativeLayout rl;
    protected String TAG = "FamilySearchFg";
    // String uid = "29DDE30B1E90AB027FC72EDF4631F036";
    private EditText content;
    TextView titleTv, title_ok;
    ImageView returIv, search_none_iv;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.family_search, null);
        lv = (ListView) view.findViewById(R.id.family_search_lv);
        rl = (RelativeLayout) view.findViewById(R.id.family_search_rl);
        TextView searchTv = (TextView) view.findViewById(R.id.family_search_search);
        content = (EditText) view.findViewById(R.id.family_search_content);
        ImageView clear = (ImageView) view.findViewById(R.id.family_search_clear);
        searchTv.setOnClickListener(FamilySearchFg.this);
        clear.setOnClickListener(FamilySearchFg.this);
        search_none_iv = (ImageView) view.findViewById(R.id.family_search_none_iv);

        returIv = (ImageView) view.findViewById(R.id.title_back);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        title_ok = (TextView) view.findViewById(R.id.title_right_tv);
        returIv.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        titleTv.setText("搜索家庭");
        title_ok.setVisibility(View.GONE);
        searchLists = new ArrayList<>();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(content, 0);
                           }
                       },
                450);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.family_search_search://搜索
                String keyWord = content.getText().toString().trim();
                rl.setVisibility(View.GONE);
                LogUtils.i(TAG, "family_search" + keyWord);
                getServiceData(keyWord);
                break;
            case R.id.family_search_clear:
                content.setText("");
                break;
            case R.id.title_back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
        }
    }

    /**
     * 搜索获取网络数据
     */
    private void getServiceData(String keyword) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("keyword", keyword);
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.SEARCH_FAMILY, map, Search.class, new HttpUtils.OnxHttpWithListResultCallBack<Search>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(List<Search> mSearchList) {
                LogUtils.d("onSuccess", "onSuccess");
                if (mSearchList != null && mSearchList.size() > 0) {
                    lv.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.GONE);
                    searchLists = mSearchList;
                    SearchFamilyAdapter searchFamilyAdapter = new SearchFamilyAdapter(searchLists, getActivity());
                    lv.setAdapter(searchFamilyAdapter);
                    searchFamilyAdapter.setiApplyFamily(FamilySearchFg.this);
                } else if (mSearchList == null || mSearchList.size() <= 0) {
                    lv.setVisibility(View.GONE);
                    rl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.e("Search_list:" + code + msg);
            }
        });
    }

    private void apply(String familyId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", familyId);
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.APPLY_ADD_FAMILY, map, String.class, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showToast(getActivity(), "申请加入该家庭");
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 11032) {
                    ToastUtils.showToast(getActivity(), "您已加入该家庭");
                } else if (code == 11034) {
                    ToastUtils.showToast(getActivity(), "已发送消息");
                }
            }
        });
    }

    @Override
    public void applyFamily(String familyId) {
        apply(familyId);
    }
}
