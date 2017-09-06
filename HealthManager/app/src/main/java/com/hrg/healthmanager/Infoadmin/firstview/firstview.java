package com.hrg.healthmanager.Infoadmin.firstview;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.hrg.healthmanager.Infoadmin.database.*;
import  com.hrg.healthmanager.Infoadmin.newuser.newuser;
import com.hrg.healthmanager.Infoadmin.olduser.*;
import com.hrg.healthmanager.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.NetworkInterface;

import static android.view.Window.FEATURE_NO_TITLE;

public class firstview extends Activity
{
    Button btn_olduser;
    CreateUserDialog createUserDialog;
    private MyDatabaseHelper dbHelper;
    String ip = null;
    String macAddress = null;
    Button nubtn;
    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        public void onClick(View paramView)
        {
            switch (paramView.getId())
            {
                default:
                    break;
                case R.id.enterpassword_btn:
                    if (firstview.this.CheckThePassWord(firstview.this.createUserDialog.etx_password.getText().toString(), firstview.this.macAddress))
                    {
                        Log.e("onClick: i111111fxl11", firstview.this.createUserDialog.etx_password.getText().toString());
                        Log.e("onClick: i111111fxl11",firstview.this.macAddress);
                        Toast.makeText(firstview.this.getBaseContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                        firstview.this.SaveThePassWord(firstview.this.createUserDialog.etx_password.getText().toString());
                        firstview.this.nubtn.setEnabled(true);
                        firstview.this.btn_olduser.setEnabled(true);
                        firstview.this.createUserDialog.dismiss();

                    }
                    else
                    {
                        Toast.makeText(firstview.this.getBaseContext(), "注册失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }

        }
    };
    String[] strMacAddress;
    String strMacAddresstext = "";
    String[] strgetpassword;

    public boolean CheckPasswordfromSQL(String paramString)
    {

        boolean j = false;
        String str = null;
        this.dbHelper = new MyDatabaseHelper(this, "liangzijiami.db", null, 2);
        Cursor localCursor = this.dbHelper.getReadableDatabase().rawQuery("select * from liangzijiami", null);
        boolean i = j;
        if (localCursor != null)
            localCursor.moveToFirst();
        while (true)
        {
            if (localCursor.isAfterLast())
            {
                localCursor.close();
                i = j;
                if (str != null)
                {
                    i = j;
                    if (str.length() > 0)
                    {
                        i = j;
                        if (CheckThePassWord(str, paramString))
                            i = true;
                    }
                }
                return i;
            }
            str = localCursor.getString(localCursor.getColumnIndex("jiami"));
            localCursor.moveToNext();
        }
    }

  boolean CheckThePassWord(String paramString1, String paramString2)
  {
    Log.e("CheckThePassWord Str1",paramString1);//1#3#6
    Log.e("CheckThePassWord Str2",paramString2);//1C:CD:E5:68:E2:E6
    int[] arrayOfInt1 = new int[3];
    int[] arrayOfInt2 = new int[6];
    boolean n = false;
    String[] stringArr = paramString1.split("#");
    arrayOfInt1[0] = Integer.valueOf(stringArr[0]).intValue();//1
    arrayOfInt1[1] = Integer.valueOf(stringArr[1]).intValue();//3
    arrayOfInt1[2] = Integer.valueOf(stringArr[2]).intValue();//6
    stringArr = paramString2.split(":");
    arrayOfInt2[0] = Integer.valueOf(stringArr[0], 16).intValue();//1C
    arrayOfInt2[1] = Integer.valueOf(stringArr[1], 16).intValue();//CD
    arrayOfInt2[2] = Integer.valueOf(stringArr[2], 16).intValue();//E5
    arrayOfInt2[3] = Integer.valueOf(stringArr[3], 16).intValue();//68
    arrayOfInt2[4] = Integer.valueOf(stringArr[4], 16).intValue();//E2
    arrayOfInt2[5] = Integer.valueOf(stringArr[5], 16).intValue();//E6
    int i = (arrayOfInt2[5] + arrayOfInt2[2]) / 2;
      Log.e("CheckThePassWord i",i+"");//229
    int j = (arrayOfInt2[3] + arrayOfInt2[4]) / 2;
      Log.e("CheckThePassWord j",j+"");//165
    int k = (arrayOfInt2[1] + arrayOfInt2[0]) / 2;
      Log.e("CheckThePassWord k",k+"");//116
    boolean m = n;
    if (i + 1 == arrayOfInt1[0])
    {
      m = n;
      if (j + 3 == arrayOfInt1[1])
      {
        m = n;
        if (k + 5 == arrayOfInt1[2])
          m = true;
      }
    }
    return m;
  }

    void SaveThePassWord(String paramString)
    {
        this.dbHelper = new MyDatabaseHelper(this, "liangzijiami.db", null, 2);
        SQLiteDatabase localSQLiteDatabase = this.dbHelper.getWritableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("jiami", paramString);
        localSQLiteDatabase.insert("liangzijiami", null, localContentValues);
    }

    public String callCmd(String paramString1, String paramString2)
    {
        String str2 = "";
        String str1 = str2;
        try
        {
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(paramString1).getInputStream()));
            while (true)
            {
                str1 = str2;
                paramString1 = localBufferedReader.readLine();
                if (paramString1 != null)
                {
                    str1 = str2;
                    if (!paramString1.contains(paramString2));
                }
                else
                {
                    str1 = paramString1;
                    Log.i("test", "result: " + paramString1);
                    return paramString1;
                }
                str1 = str2;
                Log.i("test", "line: " + paramString1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return str1;
    }

    public String getMacAddress()
    {
        String str2 = callCmd("busybox ifconfig", "HWaddr");
        if (str2 == null)
            return "网络出错，请检查网络";
        String str1 = str2;
        if (str2.length() > 0)
        {
            str1 = str2;
            if (str2.contains("HWaddr"))
            {
                String str3 = str2.substring(str2.indexOf("HWaddr") + 6, str2.length() - 1);
                str1 = str2;
                if (str3.length() > 1)
                    str1 = str3.toLowerCase();
            }
        }
        return str1.trim();
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.firstview);

        btn_olduser = ((Button)findViewById(R.id.Olduser_btn));// public static final int Olduser_btn = 2131165220;
        btn_olduser.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(firstview.this, olduser.class);
                firstview.this.startActivity(intent);
            }
        });
        this.nubtn = ((Button)findViewById(R.id.Newuser00_btn));   // public static final int Newuser00_btn = 2131165219;
        this.nubtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Intent intent = new Intent(firstview.this, newuser.class);
                intent.putExtra("theuser", "newuser");
                firstview.this.startActivity(intent);
            }
        });
        WifiManager wifiManager =(WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo=wifiManager.getConnectionInfo();
        macAddress = wifiInfo.getMacAddress();
        if (!CheckPasswordfromSQL(this.macAddress)) {
            this.strMacAddress = this.macAddress.split(":");
            int len = strMacAddress.length;
            for (int i = 0; i < len; i++)
            {
                this.strMacAddresstext += this.strMacAddress[i];
            }
            this.createUserDialog = new CreateUserDialog(this, R.style.AppTheme, "本机注册串号：" + this.strMacAddresstext, this.onClickListener);
            this.createUserDialog.show();
        }



          this.nubtn.setEnabled(true);
          this.btn_olduser.setEnabled(true);
    }

}