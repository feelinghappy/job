package com.hrg.anew;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.Window.FEATURE_NO_TITLE;

public class newuser extends Activity {
    private MyDatabaseHelper dbHelper;
    private String gettheuser = "";
    TheUseInfo theuserinfo = new TheUseInfo();
    private String username = "";


    public void GetTheUseInfo(String paramString, TheUseInfo paramTheUseInfo) {
        Cursor cursor;
        this.dbHelper = new MyDatabaseHelper(this, "LiangZiUser.db", null, 2);
        cursor = this.dbHelper.getReadableDatabase().rawQuery("select * from liangziuser where name=?", new String[]{paramString});
        if (cursor == null) {
            Log.e("init_userinfo ", "localCursor == null");
            cursor.close();
            return;
        } else if (cursor.moveToFirst()) {
            do {
                paramTheUseInfo.uname = cursor.getString(cursor.getColumnIndex("name"));
                paramTheUseInfo.usex = cursor.getString(cursor.getColumnIndex("sex"));
                paramTheUseInfo.uheight = cursor.getString(cursor.getColumnIndex("height"));
                paramTheUseInfo.uweight = cursor.getString(cursor.getColumnIndex("weight"));
                paramTheUseInfo.utel = cursor.getString(cursor.getColumnIndex("tel"));
                paramTheUseInfo.uaddr = cursor.getString(cursor.getColumnIndex("addr"));
                paramTheUseInfo.uage = cursor.getInt(cursor.getColumnIndex("age"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
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
        super.onCreate(paramBundle);
        requestWindowFeature(FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.newuser);//public static final int laodnewuser = 2130903046;
        hideVirtualKey();
        hideNavigationBar();
        gettheuser = getIntent().getStringExtra("theuser");
        final Button nubtn = (Button) findViewById(R.id.login);
        final Button out_btn = (Button) findViewById(R.id.newuser_out);
        final EditText userName_etx = (EditText) findViewById(R.id.username);
        final EditText userheight_etx = (EditText) findViewById(R.id.userheight);
        final EditText userweight_etx = (EditText) findViewById(R.id.userweight);
        final EditText userage_etx = (EditText) findViewById(R.id.userage);
        final EditText usertel_etx = (EditText) findViewById(R.id.usertel);
        final EditText useraddr_etx = (EditText) findViewById(R.id.useraddr);
        final RadioButton rbnMan = (RadioButton) findViewById(R.id.selectman);
        final RadioButton rbnwoman = (RadioButton) findViewById(R.id.selectwuman);
        dbHelper = new MyDatabaseHelper(this, "LiangZiUser.db", null, 2);
        if (gettheuser.endsWith("olduser")) {
            username = getIntent().getStringExtra("username");
            GetTheUseInfo(username, theuserinfo);
            userName_etx.setText(username);
            userName_etx.setEnabled(false);
            userheight_etx.setText(theuserinfo.uheight);
            userweight_etx.setText(theuserinfo.uweight);
            userage_etx.setText(String.valueOf(theuserinfo.uage));
            usertel_etx.setText(theuserinfo.utel);
            useraddr_etx.setText(theuserinfo.uaddr);
            if (theuserinfo.usex.endsWith("男")) {
                rbnMan.setChecked(true);
            } else {
                rbnwoman.setChecked(true);
            }
        }
        out_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(newuser.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        nubtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((userName_etx.getText().toString().length() != 0) && (userweight_etx.getText().toString().length() != 0) && (userheight_etx.getText().toString().length() != 0)) {
                    if ((userage_etx.getText().toString().length() != 0) && (Integer.parseInt(userage_etx.getText().toString()) > 3)) {
                        if (gettheuser.endsWith("olduser")) {
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("height", userheight_etx.getText().toString());
                            values.put("weight", userweight_etx.getText().toString());
                            values.put("tel", usertel_etx.getText().toString());
                            values.put("addr", useraddr_etx.getText().toString());
                            values.put("age", Integer.valueOf(Integer.parseInt(userage_etx.getText().toString())));
                            if (rbnMan.isChecked()) {
                                values.put("sex", "男");
                            } else {
                                values.put("sex", "女");
                            }
                            db.update("liangziuser", values, "name = ?", new String[]{userName_etx.getText().toString()});
                            db.close();
                            Intent intent = new Intent(newuser.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            Cursor cursor = db.rawQuery("select * from liangziuser where name=?", new String[]{userName_etx.getText().toString()});
                            if (cursor.moveToFirst()) {
                                Toast.makeText(newuser.this, "用户已存在", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ContentValues values = new ContentValues();
                            values.put("name", userName_etx.getText().toString());
                            values.put("height", userheight_etx.getText().toString());
                            values.put("weight", userweight_etx.getText().toString());
                            values.put("tel", usertel_etx.getText().toString());
                            values.put("addr", useraddr_etx.getText().toString());
                            values.put("age", Integer.valueOf(Integer.parseInt(userage_etx.getText().toString())));
                            if (rbnMan.isChecked()) {
                                values.put("sex", "男");
                            } else {
                                values.put("sex", "女");
                            }
                            db.insert("liangziuser", null, values);
                            cursor.close();
                            db.close();
                            Intent intent = new Intent(newuser.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }

                }

                if ((userage_etx.getText().toString().length() != 0) && (Integer.parseInt(userage_etx.getText().toString()) > 3)) {
                    Toast.makeText(newuser.this, "年龄不得小于4岁", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(newuser.this, "信息输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


    }


    public class TheUseInfo {
        public String uaddr = "";
        public int uage = 0;
        public String uheight = "";
        public String usex = "";
        public String utel = "";
        public String uname = "";
        public String uweight = "";

        public TheUseInfo() {
        }
    }
}