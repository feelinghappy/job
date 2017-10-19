package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.longcai.medical.R;
import com.longcai.medical.adapter.ZiXunAdapter;
import com.longcai.medical.bean.NewsList;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.activity.Web2Activity;
import com.longcai.medical.ui.view.recyclerview.EndlessRecyclerOnScrollListener;
import com.longcai.medical.ui.view.recyclerview.LoadingFooter;
import com.longcai.medical.ui.view.recyclerview.RecyclerViewStateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 咨询碎片
 * Created by LC-XC on 2016/11/17.
 */

public class FragmentInfoData extends Fragment {

    private static final String TAG = "FragmentInfoData";
    @BindView(R.id.info_list)
    RecyclerView infoList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    Unbinder unbinder;
    private String NowPage = "-1";//当前页码
    private String PageSize = "-1";//每页条数
    private String TotalPage = "-2";//总页数


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Collect.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInfoData newInstance(String param1, String param2) {
        FragmentInfoData fragment = new FragmentInfoData();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentInfoData() {

    }

    private ZiXunAdapter ziXunAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_data, null);
        unbinder = ButterKnife.bind(this, view);
        ziXunAdapter = new ZiXunAdapter(getActivity());
        infoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        infoList.setAdapter(ziXunAdapter);
       // getServiceData(1);
      //  addScrollContorl();
        return view;
    }



    String pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        pos = mParam1;
    }

    @Override
    public void onResume() {
        super.onResume();
        getServiceData(1);
        addScrollContorl();
    }

    private void getServiceData(int page) {
        RequestParams params = new RequestParams(MyUrl.NewsListData);
        params.addParameter("page", page + "");
        params.addParameter("order", "time");
        params.addParameter("typeId", pos);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getData: onSuccess   " + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "getData: onError   " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "getData: onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "getData: onFinished   ");

            }
        });

    }
    private List<NewsList.DataInfo> list;
    protected void processData(String result) {
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("status");
            if (code == 1) {
                Gson gson = new Gson();
                NewsList listData = gson.fromJson(result, NewsList.class);
                Log.i(TAG, "processData: " + listData.toString());
                TotalPage = listData.data.total;
                NowPage = String.valueOf(listData.data.pages);
                PageSize = String.valueOf(listData.data.next);
                RecyclerViewStateUtils.setFooterViewState(getActivity(),
                        infoList, Integer.parseInt(PageSize),
                        LoadingFooter.State.Normal, null);
                list = listData.data.infos;
                ziXunAdapter.setData(list);
                ziXunAdapter.setOnItemClickLitener(new ZiXunAdapter.ItemClickListener() {
                    @Override
                    public void OnClick(int position, String url, String id) {
                        startActivity(new Intent(getActivity(), Web2Activity.class).putExtra("title",
                                "资讯详情").putExtra("url", url).putExtra("id", id));
                    }
                });
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void addScrollContorl() {

        infoList.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState
                        (infoList);
                if (state == LoadingFooter.State.Loading) {
                    Log.d("@Cundong", "the state is Loading, just wait..");
                    return;
                } else if (state == LoadingFooter.State.TheEnd) {
                    Log.d("@Cundong", "the state is end.");
                    return;
                }

                if (!TotalPage.equals(NowPage)) {
                    // loading more

                    RecyclerViewStateUtils.setFooterViewState(getActivity(),
                            infoList, Integer.parseInt(PageSize), LoadingFooter.State.Loading,
                            null);
                    getServiceData(Integer.parseInt(NowPage) + 1);
                } else {
                    //the end
                    RecyclerViewStateUtils.setFooterViewState(getActivity(),
                            infoList, Integer.parseInt(PageSize), LoadingFooter.State.TheEnd,
                            null);

                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
