package com.hrg.healthmanager.Infoadmin.firstview;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.hrg.healthmanager.R;

public class CreateUserDialog extends Dialog
{
    Button btn_Save;
    Activity context;
    EditText etx_password;
    private View.OnClickListener mClickListener;
    String strmacaddrress;
    TextView txv_macAddress;
    TextView txv_srcz;

    public CreateUserDialog(Activity paramActivity)
    {
        super(paramActivity);
        this.context = paramActivity;
    }

    public CreateUserDialog(Activity paramActivity, int paramInt, String paramString, View.OnClickListener paramOnClickListener)
    {
        super(paramActivity, paramInt);
        this.context = paramActivity;
        this.mClickListener = paramOnClickListener;
        this.strmacaddrress = paramString;
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user_dialog);
        this.txv_macAddress = ((TextView)findViewById(R.id.macaddress_text));//macaddress_text
        this.txv_macAddress.setText(this.strmacaddrress);
        this.etx_password = ((EditText)findViewById(R.id.SoftPassword_etx));//2131165216    SoftPassword_etx
        this.txv_srcz = ((TextView)findViewById(R.id.srzc_txv)); //2131165215   srzc_txv
        Display localDisplay = this.context.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.height = (int)(localDisplay.getHeight() * 0.4D);
        localLayoutParams.width = (int)(localDisplay.getWidth() * 0.8D);
        this.btn_Save = ((Button)findViewById(R.id.enterpassword_btn));//2131165217   enterpassword_btn
        this.btn_Save.setOnClickListener(this.mClickListener);
        setCancelable(true);
    }
}