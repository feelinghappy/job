package com.longcai.medical.ui.activity.person.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.AddrManageAdapter;
import com.longcai.medical.bean.AddrGetList;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AddressManageActivity extends BaseActivity implements AddrManageAdapter.IEditAddress,
        AddrManageAdapter.IDeleteAddress,AddrManageAdapter.IGetDefaultAddress, AdapterView.OnItemClickListener{
    @BindView(R.id.address_lv)
    ListView addressLv;
    @BindView(R.id.address_add_btn)
    Button addressAddBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    AddrManageAdapter adapter;
    private List<AddrGetList> addrGetLists;
    private PopupWindow pop;
    private int address_type = -1;
    private final int REQUEST_CODE_ADD_ADDRESS = 201;
    private final int REQUEST_CODE_UPDATE_ADDRESS = 202;
    private boolean isEmpty = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("管理收货地址");
        titleRightTv.setVisibility(View.GONE);
        address_type = getIntent().getIntExtra("address_type", -1);
        initListData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //获取地址列表
    private void initListData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPost(AddressManageActivity.this, MyUrl.ADDR_GET_LIST, map, AddrGetList.class,
                new HttpUtils.OnxHttpWithListResultCallBack<AddrGetList>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {
                    }

                    @Override
                    public void onSuccess(final List<AddrGetList> addrList) {
                        if (null != addrList && addrList.size() > 0){
                            isEmpty = false;
                            addrGetLists = addrList;
                            Collections.reverse(addrGetLists);//倒序
                            adapter = new AddrManageAdapter(addrGetLists, AddressManageActivity.this);
                            addressLv.setAdapter(adapter);
                            adapter.setOnDeleteAddress(AddressManageActivity.this);
                            adapter.setOnEditAddress(AddressManageActivity.this);
                            adapter.setOnGetDefaultAddress(AddressManageActivity.this);
                            if (address_type == 4) {
                                addressLv.setOnItemClickListener(AddressManageActivity.this);
                            }
                        }
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        if (code == 10330) {
                            isEmpty = true;
                            if (!addrGetLists.isEmpty()) {
                                addrGetLists.clear();
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.address_add_btn, R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.address_add_btn://添加地址
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ADDRESS && resultCode == RESULT_OK) {
            initListData();
        }
        if (requestCode == REQUEST_CODE_UPDATE_ADDRESS && resultCode == RESULT_OK) {
            initListData();
        }
    }

    //编辑地址
    @Override
    public void editAddress(int position) {

    }

    //删除地址
    @Override
    public void deleteAddress(int position,int address_id) {
        View view = View.inflate(this,R.layout.popop_delete_member,null);
        TextView pop_ok = (TextView) view.findViewById(R.id.delete_ok);
        TextView pop_cancel = (TextView) view.findViewById(R.id.delete_cancel);
        pop = new ShowPopupWindow(this).showPopup(view);
        pop.showAtLocation(addressLv, Gravity.CENTER,0,0);
        initPop(pop_ok,pop_cancel,position,address_id);
    }

    private void initPop(TextView pop_ok, TextView pop_cancel, final int position, final int address_id) {
        pop_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                processAddress(position,address_id);
            }
        });
        pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }
    //删除地址
    public void processAddress(final int position, int address_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("address_id", String.valueOf(address_id));
        HttpUtils.xOverallHttpPost(this, MyUrl.ADDR_DELETE_ADDR, map, new HttpUtils.OnxHttpCallBack<List<?>>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(List<?> t) {
                initListData();
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    //获取 选中的地址
    @Override
    public void getAddress(int address_id) {
        List beSelectedData = adapter.getBeSelectedData();
        for (int i = 0; i < beSelectedData.size(); i++) {
            int selectPosition = (int) beSelectedData.get(i);
            LogUtils.d(beSelectedData.size()+"存放选为默认地址的position：--"+beSelectedData.get(i));
            AddrGetList addr = addrGetLists.get(selectPosition);
            // TODO
            setDefaultAddress(addr);
        }
        LogUtils.d("存放选为默认地址的address_id：--"+address_id);
    }

    private void setDefaultAddress(AddrGetList addr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("address_id", String.valueOf(addr.getAddress_id()));
        HttpUtils.xOverallHttpPost(this, MyUrl.ADDR_SET_DEFAULT, map, new HttpUtils.OnxHttpCallBack<List<?>>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(List<?> t) {

            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (address_type == 4) {
            AddrGetList addr = addrGetLists.get(i);
            Intent data = new Intent();
            data.putExtra("address", addr);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
