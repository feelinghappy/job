package com.longcai.medical.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.RemindContentActivity;
import com.longcai.medical.ui.activity.RemindTargetActivity;
import com.longcai.medical.ui.activity.remind.RemindRepeatActivity;
import com.longcai.medical.ui.view.popupwindow.TimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/10.
 * 事项提醒
 */

public class RemindItemFragment extends BaseFragment implements TimePicker.ITimePicker {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.title_right_iv)
    ImageView titleRightIv;
    @BindView(R.id.remind_item_content)
    TextView remindItemContent;
    @BindView(R.id.remind_item_time)
    TextView remindItemTime;
    @BindView(R.id.remind_item_repeat)
    TextView remindItemRepeat;
    @BindView(R.id.remind_item_target)
    TextView remindItemTarget;
    Unbinder unbinder;
    private final int REQUEST_CODE_CONTENT = 301;
    private final int REQUEST_CODE_REPEAT = 302;
    private final int REQUEST_CODE_TARGET = 303;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_remind_item, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        titleTv.setText("添加提醒");
        titleRightTv.setVisibility(View.GONE);
        titleRightIv.setVisibility(View.VISIBLE);
        titleRightIv.setImageResource(R.mipmap.icon_remind_delete);
    }

    @Override
    public void LoadData() {
        super.LoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title_back,R.id.title_right_iv,R.id.remind_item_content, R.id.remind_item_time, R.id.remind_item_repeat, R.id.remind_item_target, R.id.remind_item_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                getActivity().finish();
                break;
            case R.id.title_right_iv://删除

                break;
            case R.id.remind_item_content:
                startActivityForResult(new Intent(getActivity(), RemindContentActivity.class), REQUEST_CODE_CONTENT);
                break;
            case R.id.remind_item_time:
                TimePicker timePicker = new TimePicker(getActivity());
                timePicker.showPopupWindow(remindItemTime);
                timePicker.setITimePicker(this);
                break;
            case R.id.remind_item_repeat:
                startActivityForResult(new Intent(getActivity(), RemindRepeatActivity.class), REQUEST_CODE_REPEAT);
                break;
            case R.id.remind_item_target://选择提醒对象
                startActivityForResult(new Intent(getActivity(),RemindTargetActivity.class), REQUEST_CODE_TARGET);
                break;
            case R.id.remind_item_save://保存

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE_CONTENT && resultCode == Activity.RESULT_OK) {
                String content = data.getStringExtra("remind_content");
                remindItemContent.setText(content);
            }
            if (requestCode == REQUEST_CODE_REPEAT && resultCode == Activity.RESULT_OK) {
                String[] repeatArr = data.getStringArrayExtra("remind_repeat");
                StringBuilder builder = new StringBuilder();
                for (String week : repeatArr) {
                    builder.append(week);
                    builder.append(",");
                }
                int capacity = builder.capacity();
                if (capacity > 0 ) {
                    builder.deleteCharAt(capacity - 1);
                }
                remindItemRepeat.setText(builder.toString());
            }
            if (requestCode == REQUEST_CODE_TARGET && resultCode == Activity.RESULT_OK) {

            }
    }

    @Override
    public void timePicker(String time) {
        remindItemTime.setText(time);
    }
}
