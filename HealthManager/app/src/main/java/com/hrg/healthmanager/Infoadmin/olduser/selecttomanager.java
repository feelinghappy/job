package com.hrg.healthmanager.Infoadmin.olduser;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.hrg.healthmanager.MainActivity;
import com.hrg.healthmanager.Infoadmin.firstview.firstview;
import com.hrg.healthmanager.Infoadmin.database.MyDatabaseHelper;
import com.hrg.healthmanager.Infoadmin.newuser.newuser;
import com.hrg.healthmanager.R;

import static android.view.Window.FEATURE_NO_TITLE;

public class selecttomanager extends Activity
{
    public static String userage;
    public static String userheight;
    public static String username;
    public static String usersex;
    public static String userweight;
    private Bundle b;
    private MyDatabaseHelper dbHelper;
    private String uname;

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        requestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.oldusermanager);   // 2130903049 oldusermanager
        this.b = getIntent().getExtras();
        this.uname = this.b.getString(username);
        Button localButton4= (Button)findViewById(R.id.IntoTest_btn );   //public static final int IntoTest_btn = 2131165239;
        Button localButton1 = (Button)findViewById(R.id.Querry_btn);   //  public static final int Querry_btn = 2131165240;
        Button localButton2 = (Button)findViewById(R.id.Change_btn); //public static final int Change_btn = 2131165241;
        Button localButton3 = (Button)findViewById(R.id.Delet_btn); //public static final int Delet_btn = 2131165242;
        this.dbHelper = new MyDatabaseHelper(this, "LiangZiUser.db", null, 2);
        localButton4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Intent intent = new Intent(selecttomanager.this, MainActivity.class);
                intent.putExtra(MainActivity.GetUsername, selecttomanager.this.uname);
                selecttomanager.this.startActivity(intent);
                try
                {
                    selecttomanager.this.startActivity(intent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        localButton1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Intent intent = new Intent(selecttomanager.this, SelecteTheDataTime.class);
                intent.putExtra("GetUsername", selecttomanager.this.uname);
                selecttomanager.this.startActivity(intent);
            }
        });
        localButton2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Intent intent  = new Intent(selecttomanager.this, newuser.class);
                intent.putExtra("theuser", "olduser");
                intent.putExtra("username", selecttomanager.this.uname);
                selecttomanager.this.startActivity(intent);
            }
        });
       localButton3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
               new AlertDialog.Builder(selecttomanager.this).setTitle("系统提示").setMessage("请确认是否删除用户  '" + selecttomanager.this.uname + "'").setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                        selecttomanager.this.dbHelper.getWritableDatabase().delete("LiangZiUser", "name = ?", new String[] { uname });
                        selecttomanager.this.finish();
                        Intent intent = new Intent(selecttomanager.this, firstview.class);
                        selecttomanager.this.startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                    }
                }).show();
            }
        });
   }
}