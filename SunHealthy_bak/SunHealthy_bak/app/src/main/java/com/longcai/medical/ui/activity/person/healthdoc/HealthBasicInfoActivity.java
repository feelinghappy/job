package com.longcai.medical.ui.activity.person.healthdoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.BasicInfoResult;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.popupwindow.AgePicker;
import com.longcai.medical.ui.view.popupwindow.CustomPopupWindow;
import com.longcai.medical.ui.view.popupwindow.HeightPicker;
import com.longcai.medical.ui.view.popupwindow.MedicalTypePicker;
import com.longcai.medical.ui.view.popupwindow.ProfessionPicker;
import com.longcai.medical.ui.view.popupwindow.SexPicker;
import com.longcai.medical.ui.view.popupwindow.WeightPicker;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/3.
 */

public class HealthBasicInfoActivity extends BaseActivity implements SexPicker.ISexPicker, AgePicker.IAgePicker,
        HeightPicker.IHeightPicker, WeightPicker.IWeightPicker, ProfessionPicker.IProfessionPicker, MedicalTypePicker.IMedicalTypePicker,
        CustomPopupWindow.IOKBack {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.tv_basicinfo_name)
    TextView tvBasicinfoName;
    @BindView(R.id.rl_basicinfo_name)
    RelativeLayout rlBasicinfoName;
    @BindView(R.id.tv_basicinfo_sex)
    TextView tvBasicinfoSex;
    @BindView(R.id.rl_basicinfo_sex)
    RelativeLayout rlBasicinfoSex;
    @BindView(R.id.tv_basicinfo_age)
    TextView tvBasicinfoAge;
    @BindView(R.id.rl_basicinfo_age)
    RelativeLayout rlBasicinfoAge;
    @BindView(R.id.tv_basicinfo_height)
    TextView tvBasicinfoHeight;
    @BindView(R.id.rl_basicinfo_height)
    RelativeLayout rlBasicinfoHeight;
    @BindView(R.id.tv_basicinfo_weight)
    TextView tvBasicinfoWeight;
    @BindView(R.id.rl_basicinfo_weight)
    RelativeLayout rlBasicinfoWeight;
    @BindView(R.id.tv_basicinfo_profession)
    TextView tvBasicinfoProfession;
    @BindView(R.id.rl_basicinfo_profession)
    RelativeLayout rlBasicinfoProfession;
    @BindView(R.id.tv_basicinfo_medical)
    TextView tvBasicinfoMedical;
    @BindView(R.id.rl_basicinfo_medical)
    RelativeLayout rlBasicinfoMedical;

    private Unbinder unbinder;
    private String trueName = "";
    private int age = -1;
    private int sex = -1;
    private int height = -1;
    private int weight = -1;
    private int profession = -1;
    private int medicalType = -1;
    private final int REQUEST_CODE_UPDATE_NAME = 103;
    private boolean isEdited = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        titleRightTv.setText("保存");
        titleTv.setText("基本资料");
        tvBasicinfoName.setHintTextColor(ContextCompat.getColor(this, R.color.gray));
    }

    private void initData() {
        BasicInfoResult basicInfo = (BasicInfoResult) getIntent().getSerializableExtra("basic_info");
        if (null != basicInfo) {
            String true_name = basicInfo.getTrue_name();
            if (!TextUtils.isEmpty(true_name)) {
                trueName = true_name;
                tvBasicinfoName.setEnabled(false);
                tvBasicinfoName.setText(true_name);
            } else {
                tvBasicinfoName.setEnabled(true);
            }
            String member_sex = basicInfo.getMember_sex();
            if (!TextUtils.isEmpty(member_sex)) {
                int sex = StringUtils.parseInt(member_sex);
                this.sex = sex;
                if (sex == 1) {
                    tvBasicinfoSex.setText("男");
                } else if (sex == 2) {
                    tvBasicinfoSex.setText("女");
                } else if (sex == -1) {
                    tvBasicinfoSex.setText("男");
                }
            }
            String member_age = basicInfo.getMember_age();
            if (!TextUtils.isEmpty(member_age)) {
                this.age = StringUtils.parseInt(member_age);
                tvBasicinfoAge.setText(member_age);
            }
            String member_height = basicInfo.getMember_height();
            if (!TextUtils.isEmpty(member_height)) {
                this.height = StringUtils.parseInt(member_height);
                tvBasicinfoHeight.setText(member_height);
            }
            String member_weight = basicInfo.getMember_weight();
            if (!TextUtils.isEmpty(member_weight)) {
                this.weight = StringUtils.parseInt(member_weight);
                tvBasicinfoWeight.setText(member_weight);
            }
            String profession = basicInfo.getProfession();
            if (!TextUtils.isEmpty(profession)) {
                this.profession = StringUtils.parseInt(profession);
                setProfession(profession);
            }
            String medical_type = basicInfo.getMedical_type();
            if (!TextUtils.isEmpty(medical_type)) {
                this.medicalType = StringUtils.parseInt(medical_type);
                setMedicalType(medical_type);
            }
        }
    }

    private void setMedicalType(String medicalType) {
        switch (medicalType) {
            case "0":
                tvBasicinfoMedical.setText("城镇职工医保");
                break;
            case "1":
                tvBasicinfoMedical.setText("新农合医保");
                break;
            case "2":
                tvBasicinfoMedical.setText("无");
                break;
            case "3":
                tvBasicinfoMedical.setText("其他");
                break;
        }
    }

    private void setProfession(String profession) {
        switch (profession) {
            case "0":
                tvBasicinfoProfession.setText("无");
                break;
            case "1":
                tvBasicinfoProfession.setText("国家公务员");
                break;
            case "2":
                tvBasicinfoProfession.setText("专业技术人员");
                break;
            case "3":
                tvBasicinfoProfession.setText("职员");
                break;
            case "4":
                tvBasicinfoProfession.setText("企业管理人员");
                break;
            case "5":
                tvBasicinfoProfession.setText("工人");
                break;
            case "6":
                tvBasicinfoProfession.setText("农民");
                break;
            case "7":
                tvBasicinfoProfession.setText("学生");
                break;
            case "8":
                tvBasicinfoProfession.setText("现役军人");
                break;
            case "9":
                tvBasicinfoProfession.setText("自由职业者");
                break;
            case "10":
                tvBasicinfoProfession.setText("个体经营者");
                break;
            case "11":
                tvBasicinfoProfession.setText("退(离)休人员");
                break;
        }
    }

    private void setTrueName() {
        String name = tvBasicinfoName.getText().toString();
//        if (!TextUtils.isEmpty(name)) {
            Intent intent = new Intent(this, HealthEditNameActivity.class);
            intent.putExtra("true_name", name);
            startActivityForResult(intent, REQUEST_CODE_UPDATE_NAME);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_NAME && resultCode == RESULT_OK) {
            String updateTrueName = data.getStringExtra("update_true_name");
            if (!TextUtils.isEmpty(updateTrueName)) {
                isEdited = true;
                trueName = updateTrueName;
                tvBasicinfoName.setText(updateTrueName);
            }
        }
    }

    @OnClick({R.id.title_back, R.id.rl_basicinfo_sex, R.id.rl_basicinfo_age, R.id.rl_basicinfo_height, R.id.rl_basicinfo_weight,
            R.id.rl_basicinfo_profession, R.id.rl_basicinfo_medical, R.id.title_right_tv, R.id.rl_basicinfo_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                if (isEdited) {
                    CustomPopupWindow customPopupWindow = new CustomPopupWindow(this);
                    customPopupWindow.showPopupWindow(rlBasicinfoSex);
                    customPopupWindow.setIOKBack(this);
                    customPopupWindow.setTitle("您有编辑过的内容未保存，确定要退出吗？");
                } else {
                    finish();
                }
                break;
            case R.id.rl_basicinfo_name:
                setTrueName();
                break;
            case R.id.rl_basicinfo_sex:
                SexPicker sexPicker = new SexPicker(this);
                sexPicker.showPopupWindow(rlBasicinfoSex);
                sexPicker.setISexPicker(this);
                break;
            case R.id.rl_basicinfo_age:
                AgePicker agePicker = new AgePicker(this);
                agePicker.showPopupWindow(rlBasicinfoAge);
                agePicker.setIAgePicker(this);
                break;
            case R.id.rl_basicinfo_height:
                HeightPicker heightPicker = new HeightPicker(this);
                heightPicker.showPopupWindow(rlBasicinfoHeight);
                heightPicker.setIHeightPicker(this);
                break;
            case R.id.rl_basicinfo_weight:
                WeightPicker weightPicker = new WeightPicker(this);
                weightPicker.showPopupWindow(rlBasicinfoWeight);
                weightPicker.setIWeightPicker(this);
                break;
            case R.id.rl_basicinfo_profession:
                ProfessionPicker professionPicker = new ProfessionPicker(this);
                professionPicker.showPopupWindow(rlBasicinfoProfession);
                professionPicker.setIProfessionPicker(this);
                break;
            case R.id.rl_basicinfo_medical:
                MedicalTypePicker medicalTypePicker = new MedicalTypePicker(this);
                medicalTypePicker.showPopupWindow(rlBasicinfoMedical);
                medicalTypePicker.setIMedicalTypePicker(this);
                break;
            case R.id.title_right_tv:
                editBasicInfo();
                break;
        }
    }

    private boolean checkData() {
        trueName = tvBasicinfoName.getText().toString();
        if (TextUtils.isEmpty(trueName)) {
            ToastUtils.showToast(this, "请输入真实姓名");
            return false;
        } else if (sex == -1) {
            ToastUtils.showToast(this, "请选择性别");
            return false;
        } else if (age == -1) {
            ToastUtils.showToast(this, "请选择年龄");
            return false;
        } else if (height == -1) {
            ToastUtils.showToast(this, "请选择身高");
            return false;
        } else if (weight == -1) {
            ToastUtils.showToast(this, "请选择体重");
            return false;
        } else if (profession == -1) {
            ToastUtils.showToast(this, "请选择职业");
            return false;
        } else if (medicalType == -1) {
            ToastUtils.showToast(this, "请选择医保类型");
            return false;
        }
        return true;
    }

    private void editBasicInfo() {
        if (checkData()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("token", MyApplication.myPreferences.readToken());
            map.put("true_name", trueName);
            map.put("member_sex", String.valueOf(sex));
            map.put("member_age", String.valueOf(age));
            map.put("member_height", String.valueOf(height));
            map.put("member_weight", String.valueOf(weight));
            map.put("profession", String.valueOf(profession));
            map.put("medical_type", String.valueOf(medicalType));
            HttpUtils.xOverallHttpPost(this, MyUrl.HEALTH_BASIC_INFO, map, BasicInfoResult.class, new HttpUtils.OnxHttpCallBack<BasicInfoResult>() {
                @Override
                public void onSuccessMsg(String successMsg) {
                    ToastUtils.showToast(HealthBasicInfoActivity.this, "保存成功");
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onSuccess(BasicInfoResult basicInfoResult) {

                }

                @Override
                public void onFail(int code, String msg) {
                        ToastUtils.showToast(HealthBasicInfoActivity.this,"修改失败，请填写正确的信息");
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void agePicker(int item) {
        // item + 1
        LogUtils.d("AgePicker", String.valueOf(item));
        if (age != item) {
            isEdited = true;
        }
        age = item;
        tvBasicinfoAge.setText(String.valueOf(item));
    }

    @Override
    public void sexPicker(int item, String itemName) {
        LogUtils.d("SexPicker", item + itemName);
        if (sex != item + 1) {
            isEdited = true;
        }
        sex = item + 1;
        tvBasicinfoSex.setText(itemName);
    }

    @Override
    public void heightPicker(int item) {
        LogUtils.d("heightPicker", String.valueOf(item));
        if (height != item) {
            isEdited = true;
        }
        height = item+100;
        tvBasicinfoHeight.setText(String.valueOf(height));
    }

    @Override
    public void weightPicker(int item) {
        LogUtils.d("weightPicker", String.valueOf(item));
        if (weight != item) {
            isEdited = true;
        }
        weight = item+20;
        tvBasicinfoWeight.setText(String.valueOf(weight));
    }

    @Override
    public void professionPicker(int item, String itemName) {
        LogUtils.d("professionPicker", item + itemName);
        if (profession != item) {
            isEdited = true;
        }
        profession = item;
        tvBasicinfoProfession.setText(itemName);
    }

    @Override
    public void medicalTypePicker(int item, String itemName) {
        LogUtils.d("medicalTypePicker", item + itemName);
        if (medicalType != item) {
            isEdited = true;
        }
        medicalType = item;
        tvBasicinfoMedical.setText(itemName);
    }

    @Override
    public void okBack() {
        finish();
    }
}
