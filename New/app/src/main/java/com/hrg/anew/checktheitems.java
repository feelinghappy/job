package com.hrg.anew;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.hrg.anew.R;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.Window.FEATURE_NO_TITLE;

public class checktheitems extends Activity {
    public static String GetUsername;
    double BMI;
    String CheckTime;
    public String SenduserResult = "";
    double StandarHeigt;
    double StandarWeigt;
    CheckItemAdapter adapter;
    private Bundle b;
    private List<checkitem> checkboxList = new ArrayList();
    private MyDatabaseHelper dbHelper;
    String dismodeString = "newdisplay";
    public List<Integer> isSelected = new ArrayList();
    double scrose;
    private String senduserDegree = "";
    double tixingscrose;
    double tizhi;
    TextView tv_checdate;
    TextView tv_userage;
    TextView tv_userheight;
    TextView tv_username;
    TextView tv_usersex;
    TextView tv_userweight;
    private String uname;
    int userage;
    String userheight;
    String username;
    String usersex;
    String userweight;
    String DegreeResult;
    String Result;

 /*   private void GetTheScrose(String usersex, int userage, String userweight, String userheight) {
        double weight = Double.parseDouble(userweight);
        double height = Double.parseDouble(userheight);
        int age = userage;
        String sex = usersex;
        BMI = 10000 * ((weight) / (height * height));
        if (sex.equals("男")) {
            StandarWeigt = ((height - 100.0) * 0.9);
            StandarHeigt = ((weight / 0.9) + 100.0);
            tizhi = ((((BMI * 1.3) + (0.23 * (double) age)) - 5.4) - 10.8);
        } else {
            double temp = height / 75.0;
            StandarWeigt = ((height - 100.0) * 0.9);
            StandarWeigt = (StandarWeigt - temp);
            StandarHeigt = (((90.0 + weight) * 75.0) / 66.5);
            tizhi = (((BMI * 1.3) + (0.23 * (double) age)) - 5.4);
        }
        if (sex.equals("男")) {
            if ((BMI > 18.0) && (BMI < 24.0)) {
                scrose = (((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0);
            } else if ((BMI >= 24.0) && (BMI < 28.0)) {
                scrose = ((((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0) * 0.85);
            } else if ((BMI >= 28.0) && (BMI < 32.0)) {
                scrose = ((((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0) * 0.75);
            } else if (BMI >= 32.0) {
                scrose = ((((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0) * 0.65);
            }
        } else {
            if ((BMI > 18.0) && (BMI < 22.0)) {
                scrose = (((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0);
            } else if ((BMI >= 22.0) && (BMI < 28.0)) {
                scrose = ((((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0) * 0.85);
            } else if ((BMI >= 28.0) && (BMI < 32.0)) {
                scrose = ((((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0) * 0.75);
            } else if (BMI >= 32.0) {
                scrose = ((((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0) * 0.65);
            } else {
                scrose = ((((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0) * 0.9);
            }
        }
        tixingscrose = scrose;
        if (StandarWeigt <= weight) {
            if ((scrose >= 61.5) && (scrose <= 72.0)) {
                if (age < 24) {
                    scrose = (scrose - 10.0);
                } else if ((age >= 24) && (age <= 32)) {
                    scrose = ((scrose - 10.0) - ((double) (age - 24) * 0.5));
                } else if ((age > 32) && (age <= 44)) {
                    scrose = ((scrose - 13.0) - ((double) (age - 32) * 0.25));
                } else if ((age > 44) && (age <= 50)) {
                    scrose = ((scrose - 16.0) - ((double) (age - 44) * 0.5));
                } else if ((age > 50) && (age <= 60)) {
                    scrose = ((scrose - 19.0) - ((double) (age - 50) * 0.3));
                } else if ((age > 60) && (age <= 75)) {
                    scrose = ((scrose - 22.0) - ((double) (age - 60) * 0.4));
                } else if ((age > 75) && (age <= 80)) {
                    scrose = ((scrose - 28.0) - ((double) (age - 75) * 1.2));
                } else if (age > 80) {
                    scrose = ((scrose - 34.0) - ((double) (age - 80) * 0.25));
                }
            } else if ((scrose >= 41.2) && (scrose < 61.5)) {
                if ((age > 31) && (age <= 43)) {
                    scrose = (scrose - ((double) (age - 31) * 0.25));
                } else if ((age > 43) && (age <= 49)) {
                    scrose = ((scrose - 3.0) - ((double) (age - 43) * 0.5));
                } else if ((age > 49) && (age <= 59)) {
                    scrose = ((scrose - 6.0) - ((double) (age - 49) * 0.3));
                } else if ((age > 59) && (age <= 74)) {
                    scrose = ((scrose - 9.0) - ((double) (age - 59) * 0.2));
                } else if ((age > 74) && (age <= 79)) {
                    scrose = ((scrose - 12.0) - ((double) (age - 74) * 1.2));
                } else if (age > 79) {
                    scrose = ((scrose - 18.0) - (double) ((age - 79) * 1));
                }
            } else if (scrose < 41.2) {
                if ((age > 35) && (age <= 40)) {
                    scrose = (scrose - ((double) (age - 35) * 0.3));
                } else if ((age > 40) && (age <= 44)) {
                    scrose = ((scrose - 1.5) - ((double) (age - 40) * 0.5));
                } else if ((age > 44) && (age <= 50)) {
                    scrose = ((scrose - 3.5) - ((double) (age - 44) * 0.5));
                } else if ((age > 50) && (age <= 60)) {
                    scrose = ((scrose - 6.5) - ((double) (age - 0x32) * 0.3));
                } else if ((age > 60) && (age <= 75)) {
                    scrose = ((scrose - 9.5) - ((double) (age - 0x3c) * 0.1));
                } else if ((age > 74) && (age <= 80)) {
                    scrose = ((scrose - 11.0) - ((double) (age - 75) * 0.4));
                } else if (age > 80) {
                    scrose = ((scrose - 13.0) - ((double) (age - 80) * 0.1));
                }
            } else if (age > 45) {
                scrose = (72.0 - ((double) (age - 45) * 0.8));
            }
        } else {
            scrose = (scrose * 0.9);
            if (scrose > 57.0) {
                if (age > 45) {
                    scrose = (57.0 - ((double) (age - 45) * 0.25));
                }
            }
        }
        ComputeTheResult computeresuResult = new ComputeTheResult();
        computeresuResult.ComputeTheResult(scrose, height, weight, StandarHeigt, StandarWeigt, age);
        double[] theresult = computeresuResult.result;
        double[] thedegree = computeresuResult.DegreeResult;
        for (int i = 0; i < theresult.length; i++) {
            SenduserResult += String.format("%.3f", Double.valueOf(theresult[i])) + ",";

            senduserDegree += String.format("%.1f", Double.valueOf(theresult[i]), Double.valueOf(thedegree[i])) + ",";

        }
    }*/
 private void GetTheScrose(String usersex, int userage, String userweight, String userheight) {
     double weight = Double.parseDouble(userweight);
     double height = Double.parseDouble(userheight);
     int age = userage;
     String sex = usersex;
     BMI = ((10000.0 * weight) / (height * height));
     if(sex.equals("男")) {
         StandarWeigt = ((height - 100.0) * 0.9);
         StandarHeigt = ((weight / 0.9) + 100.0);
         tizhi = ((((BMI * 1.3) + (0.23 * (double)age)) - 5.4) - 10.8);
     } else {
         double temp = height / 75.0;
         StandarWeigt = ((height - 100.0) * 0.9);
         StandarWeigt = (StandarWeigt - temp);
         StandarHeigt = (((90.0 + weight) * 75.0) / 66.5);
         tizhi = (((BMI * 1.3) + (0.23 * (double)age)) - 5.4);
     }
     if(sex.equals("男")) {
             if ((BMI > 18.0) && (BMI < 24.0)) {
                 scrose = (((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0);
             } else if ((BMI >= 24.0) && (BMI < 28.0)) {
                 scrose = ((((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0) * 0.85);
             } else if ((BMI >= 28.0) && (BMI < 32.0)) {
                 scrose = ((((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0) * 0.75);
             } else if (BMI >= 32.0) {
                 scrose = ((((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0) * 0.65);
             } else {
                 scrose = ((((22.0 - Math.abs((BMI - 22.0))) * 100.0) / 22.0) * 0.9);
             }
        }
         else {
             if ((BMI > 18.0) && (BMI < 22.0)) {
                 scrose = (((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0);
             } else if ((BMI >= 22.0) && (BMI < 28.0)) {
                 scrose = ((((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0) * 0.85);
             } else if ((BMI >= 28.0) && (BMI < 32.0)) {
                 scrose = ((((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0) * 0.75);
             } else if (BMI >= 32.0) {
                 scrose = ((((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0) * 0.65);
             } else {
                 scrose = ((((20.0 - Math.abs((BMI - 20.0))) * 100.0) / 20.0) * 0.9);
             }

     }
     tixingscrose = scrose;
     if(StandarWeigt <= weight) {
         if((scrose >= 61.5) && (scrose <= 72.0)) {
             if(age < 24) {
                 scrose = (scrose - 10.0);
             } else if((age >= 24) && (age <= 32)) {
                 scrose = ((scrose - 10.0) - ((double)(age - 24) * 0.5));
             } else if((age > 32) && (age <= 44)) {
                 scrose = ((scrose - 13.0) - ((double)(age - 32) * 0.25));
             } else if((age > 44) && (age <= 50)) {
                 scrose = ((scrose - 16.0) - ((double)(age -44) * 0.5));
             } else if((age > 50) && (age <= 60)) {
                 scrose = ((scrose - 19.0) - ((double)(age - 50) * 0.3));
             } else if((age > 60) && (age <= 75)) {
                 scrose = ((scrose - 22.0) - ((double)(age - 60) * 0.4));
             } else if((age >75) && (age <= 80)) {
                 scrose = ((scrose - 28.0) - ((double)(age - 75) * 1.2));
             } else if(age > 80) {
                 scrose = ((scrose - 34.0) - ((double)(age - 80) * 0.25));
             }
         } else if((scrose >= 41.2) && (scrose < 61.5)) {
             if((age > 31) && (age <= 43)) {
                 scrose = (scrose - ((double)(age - 0x1f) * 0.25));
             } else if((age > 43) && (age <= 49)) {
                 scrose = ((scrose - 3.0) - ((double)(age - 43) * 0.5));
             } else if((age > 49) && (age <=59)) {
                 scrose = ((scrose - 6.0) - ((double)(age - 49) * 0.3));
             } else if((age > 59) && (age <= 74)) {
                 scrose = ((scrose - 9.0) - ((double)(age - 59) * 0.2));
             } else if((age > 74) && (age <= 79)) {
                 scrose = ((scrose - 12.0) - ((double)(age - 74) * 1.2));
             } else if(age > 79) {
                 scrose = ((scrose - 18.0) - (double)((age - 79) * 0x1));
             }
         } else if(scrose < 41.2) {
             if((age > 35) && (age <= 40)) {
                 scrose = (scrose - ((double)(age - 35) * 0.3));
             } else if((age > 40) && (age <= 44)) {
                 scrose = ((scrose - 1.5) - ((double)(age - 40) * 0.5));
             } else if((age > 44) && (age <= 50)) {
                 scrose = ((scrose - 3.5) - ((double)(age - 44) * 0.5));
             } else if((age > 50) && (age <= 60)) {
                 scrose = ((scrose - 6.5) - ((double)(age - 50) * 0.3));
             } else if((age > 60) && (age <= 75)) {
                 scrose = ((scrose - 9.5) - ((double)(age - 60) * 0.1));
             } else if((age > 75) && (age <= 80)) {
                 scrose = ((scrose - 11.0) - ((double)(age - 75) * 0.4));
             } else if(age > 80) {
                 scrose = ((scrose - 13.0) - ((double)(age - 80) * 0.1));
             }
         } else if(age > 69) {
             scrose = (72.0 - ((double)(age - 45) * 0.8));
         }
     } else {
         scrose = (scrose * 0.9);
         if(scrose > 57.0) {
             if(age > 45) {
                 scrose = (57.0 - ((double)(age - 45) * 0.25));
             }
         }
     }
     ComputeTheResult computeresuResult = new ComputeTheResult();
     computeresuResult.ComputeTheResult(scrose, height, weight, StandarHeigt, StandarWeigt, age);
     double[] theresult = computeresuResult.result;
     double[] thedegree = computeresuResult.DegreeResult;
     Log.e(" theresult.length", theresult.length+"");
     for (int i = 0; i < theresult.length; i++) {
         //Log.e("SenduserResult",SenduserResult);
         SenduserResult += String.format("%.3f", Double.valueOf(theresult[i])) + ",";

         senduserDegree += String.format("%.1f", Double.valueOf(thedegree[i])) + ",";
         //Log.e("senduserDegree",senduserDegree);

     }
     checktheitems.this.DegreeResult = senduserDegree;
     checktheitems.this.Result = SenduserResult;



}

    private void SaveTheResult() {
        this.dbHelper = new MyDatabaseHelper(this, "liangziresult.db", null, 2);
        SQLiteDatabase localSQLiteDatabase = this.dbHelper.getWritableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("name", this.uname);
        localContentValues.put("time", this.CheckTime);
        localContentValues.put("result", this.SenduserResult);
        localContentValues.put("degree", this.senduserDegree);
        localSQLiteDatabase.insert("liangziresult", null, localContentValues);
        localSQLiteDatabase.close();
    }

    private void initFruits() {
        if (this.userage > 12) {
            checkitem localcheckitem = new checkitem("综合报表", 33);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("心脑血管", 0);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("脑神经", 1);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("肝功能", 2);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("胃肠功能", 3);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("胆功能", 4);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("肺功能", 5);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("肾脏功能", 6);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("肾功能", 7);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("微量元素", 8);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("维生素", 9);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("风湿骨病", 10);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("骨密度", 11);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("骨病", 12);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("内分泌系统", 13);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("免疫系统", 14);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("基本体质", 15);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("氨基酸", 16);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("蛋白质", 17);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("胶原蛋白", 33);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("眼部", 18);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("皮肤", 19);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("胰腺功能", 20);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("血糖", 21);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("辅酶", 22);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("人体毒素", 23);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("人体成分", 34);
            this.checkboxList.add(localcheckitem);
            if (this.usersex.equals("男")) {
                localcheckitem = new checkitem("男性性功能", 24);
                this.checkboxList.add(localcheckitem);
                localcheckitem = new checkitem("前列腺", 25);
                this.checkboxList.add(localcheckitem);
                return;
            }
            localcheckitem = new checkitem("乳腺", 26);
            this.checkboxList.add(localcheckitem);
            localcheckitem = new checkitem("妇科", 27);
            this.checkboxList.add(localcheckitem);
            return;
        }
        checkitem localcheckitem = new checkitem("微量元素", 28);
        this.checkboxList.add(localcheckitem);
        localcheckitem = new checkitem("维生素", 29);
        this.checkboxList.add(localcheckitem);
        localcheckitem = new checkitem("少儿多动症", 30);
        this.checkboxList.add(localcheckitem);
        localcheckitem = new checkitem("少儿生长指数", 31);
        this.checkboxList.add(localcheckitem);
        localcheckitem = new checkitem("青少年智力", 32);
        this.checkboxList.add(localcheckitem);
    }

    void GetTheUseInfo() {
        this.dbHelper = new MyDatabaseHelper(this, "LiangZiUser.db", null, 2);
        Cursor localCursor = this.dbHelper.getReadableDatabase().rawQuery("select * from liangziuser where name=?", new String[]{this.uname});
        if (localCursor != null)
            localCursor.moveToFirst();
        while (true) {
            if (localCursor.isAfterLast()) {
                localCursor.close();
                return;
            }
            this.username = localCursor.getString(localCursor.getColumnIndex("name"));
            this.usersex = localCursor.getString(localCursor.getColumnIndex("sex"));
            this.userheight = localCursor.getString(localCursor.getColumnIndex("height"));
            this.userweight = localCursor.getString(localCursor.getColumnIndex("weight"));
            this.userage = localCursor.getInt(localCursor.getColumnIndex("age"));
            localCursor.moveToNext();
        }
    }
    private  void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        int uiOpions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE |SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOpions);

    }
    /**
     * 隐藏Android底部的虚拟按键
     */
    private void hideVirtualKey(){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);
    }
    protected void onCreate(Bundle paramBundle) {
        Log.e("onCreate", "onCreate");
        super.onCreate(paramBundle);
        requestWindowFeature(FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.result);//  public static final int checktodisplay = 2130903043;
        hideNavigationBar();
        hideVirtualKey();
        this.b = getIntent().getExtras();
        this.uname = this.b.getString(GetUsername);
        this.dismodeString = getIntent().getStringExtra("displaymode");
        this.tv_username = ((TextView) findViewById(R.id.tex_username));//   public static final int tex_username = 2131165206;
        this.tv_userage = ((TextView) findViewById(R.id.tex_userage));// public static final int tex_userage = 2131165207;
        this.tv_usersex = ((TextView) findViewById(R.id.tex_usersex));// public static final int tex_usersex = 2131165208;
        this.tv_userheight = ((TextView) findViewById(R.id.tex_userheight));//public static final int tex_userheight = 2131165209;
        this.tv_userweight = ((TextView) findViewById(R.id.tex_userweight));//    public static final int tex_userweight = 2131165210;
        this.tv_checdate = ((TextView) findViewById(R.id.tex_checkdate));//  public static final int tex_checkdate = 2131165211;
        GetTheUseInfo();
        if (this.dismodeString.endsWith("newdisplay")) {
            this.CheckTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Log.e("this.usersex", this.usersex);
            Log.e("this.userage", this.userage + "");
            Log.e("this.userweight", this.userweight);
            Log.e("this.userheight", this.userheight);

            GetTheScrose(this.usersex, this.userage, this.userweight, this.userheight);
            SaveTheResult();
            this.tv_checdate.setText(this.CheckTime);
            this.tv_userage.setText(String.valueOf(this.userage));
            this.tv_username.setText(this.username);
            this.tv_userheight.setText(this.userheight);
            this.tv_userweight.setText(this.userweight);
            this.tv_usersex.setText(this.usersex);
            initFruits();
            this.adapter = new CheckItemAdapter(this, R.layout.result, this.checkboxList); // public static final int checkitems = 2130903042;
            ListView listview = (ListView) findViewById(R.id.list_view);     //  public static final int list_view = 2131165212;
            listview.setAdapter(this.adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                    checkitem item = (checkitem) checktheitems.this.checkboxList.get(paramInt);
                    Intent intent = new Intent(checktheitems.this, displayresult.class);
                    checktheitems.this.isSelected = checktheitems.this.adapter.isSelected;
                    intent.putExtra("GetisSelected", CheckItemAdapter.listToString(checktheitems.this.isSelected));
                    intent.putExtra("GetUsername", checktheitems.this.uname);
                    intent.putExtra("GetResult", checktheitems.this.Result);
                    if(SenduserResult==null)
                    {

                    }
                    else {
                        Log.e("onCreate SenduserResult", checktheitems.this.Result);
                    }
                    intent.putExtra("GetItemName", item.getName());
                    intent.putExtra("Getusex", checktheitems.this.usersex);
                    intent.putExtra("Getuheight", checktheitems.this.userheight);
                    intent.putExtra("Getuweight", checktheitems.this.userweight);
                    intent.putExtra("Getuage", Integer.toString(checktheitems.this.userage));
                    intent.putExtra("GetTime", checktheitems.this.CheckTime);
                    intent.putExtra("GetDegree", checktheitems.this.DegreeResult);
                    Log.e("onCreate senduserDegree", checktheitems.this.DegreeResult);
                    checktheitems.this.startActivity(intent);
                }
            });
            this.SenduserResult = getIntent().getStringExtra("OldResult");
            this.CheckTime = getIntent().getStringExtra("OldDateTime");
            this.senduserDegree = getIntent().getStringExtra("OlduserDegree");
        } else {
            //  public static final int title_edit = 2131165254;
    /*        ((Button) findViewById(R.id.title_edit)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramView) {
                    Intent intent = new Intent(checktheitems.this, MainActivity.class);
                    checktheitems.this.startActivity(intent);
                    checktheitems.this.finish();
                }
            });*/
            this.tv_checdate.setText(this.CheckTime);
            this.tv_userage.setText(String.valueOf(this.userage));
            this.tv_username.setText(this.username);
            this.tv_userheight.setText(this.userheight);
            this.tv_userweight.setText(this.userweight);
            this.tv_usersex.setText(this.usersex);
            initFruits();
            this.adapter = new CheckItemAdapter(this, R.layout.result, this.checkboxList); // public static final int checkitems = 2130903042;
            ListView listview = (ListView) findViewById(R.id.list_view);     //  public static final int list_view = 2131165212;
            listview.setAdapter(this.adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                    checkitem item = (checkitem) checktheitems.this.checkboxList.get(paramInt);
                    Intent intent = new Intent(checktheitems.this, displayresult.class);
                    checktheitems.this.isSelected = checktheitems.this.adapter.isSelected;
                    intent.putExtra("GetisSelected", CheckItemAdapter.listToString(checktheitems.this.isSelected));
                    intent.putExtra("GetUsername", checktheitems.this.uname);
                    intent.putExtra("GetResult", checktheitems.this.SenduserResult);
                    intent.putExtra("GetItemName", item.getName());
                    intent.putExtra("Getusex", checktheitems.this.usersex);
                    intent.putExtra("Getuheight", checktheitems.this.userheight);
                    intent.putExtra("Getuweight", checktheitems.this.userweight);
                    intent.putExtra("Getuage", Integer.toString(checktheitems.this.userage));
                    intent.putExtra("GetTime", checktheitems.this.CheckTime);
                    intent.putExtra("GetDegree", checktheitems.this.senduserDegree);
                    checktheitems.this.startActivity(intent);
                }
            });
            this.SenduserResult = getIntent().getStringExtra("OldResult");
            this.CheckTime = getIntent().getStringExtra("OldDateTime");
            this.senduserDegree = getIntent().getStringExtra("OlduserDegree");
        }
    }
}