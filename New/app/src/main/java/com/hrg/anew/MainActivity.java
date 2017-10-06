package com.hrg.anew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class MainActivity extends AppCompatActivity {
    Button btnDel;
    ImageView imgaddnew;
    TextView username;
    TextView userage;
    TextView userheight;
    TextView userweight;
    TextView phone;
    TextView address;
    private List<User> ALLUserList = new ArrayList<>();
    private List<UserInfo> ALLUserInfoList = new ArrayList();
    private List<UserInfo> OldUserInfoList = new ArrayList();
    private userListAdapter UserListAdapter;
    private UserInfo getuserinfo;
    private MyDatabaseHelper dbHelper;
    private  Button btnModify;
    private  ListView listname;
    private  Button btnIntoTest;
    private  Button btnQuerry;
    @Override
    protected void onResume()
    {
        super.onResume();
        UpdateData();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if(this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_main);
        userage = (TextView) findViewById(R.id.userage);
        userheight = (TextView)findViewById(R.id.userheight);
        userweight = (TextView)findViewById(R.id.userweight);
        username = (TextView)findViewById(R.id.username);
        address =(TextView)findViewById(R.id.useraddress);
        phone = (TextView)findViewById(R.id.userphonenum);
        hideVirtualKey();
        hideNavigationBar();
        imgaddnew = (ImageView)findViewById(R.id.add_new);
        imgaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, newuser.class);
                intent.putExtra("theuser", "newuser");
                MainActivity.this.startActivity(intent);
            }
        });
        listname = (ListView)findViewById(R.id.list_user);
        listname.setSelected(true);
        userListAdapter adapter = new userListAdapter(MainActivity.this,R.layout.user_item,ALLUserList);
        listname.setAdapter(adapter);
        if(init_user())
        {

            listname.smoothScrollToPosition(0);
            listname.setSelection(0);
            User user = (User) listname.getItemAtPosition(0);
            username.setText(user.getName());
            String name = user.name;
            find_userinfobyusername(name);
        }
        else
        {
            username.setText("");
            userage.setText("");
            userheight.setText("");
            userweight.setText("");
            address.setText("");
            phone.setText("");
        }
        listname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获得选中项的HashMap对象   
                User user = (User) listname.getItemAtPosition(position);
                String name = user.getName();
                username.setText(name);
                if(name==null)
                {
                    Toast.makeText(MainActivity.this,"当前没有选中用户",Toast.LENGTH_SHORT).show();
                }
                else {
                    find_userinfobyusername(name);
                    Log.e("find_userinfobyusername", name);
                }

            }
        });
        btnDel = (Button)findViewById(R.id.Delete_btn);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strname = username.getText().toString();
                Deluserinfobyusername(strname);
            }
        });
        btnModify = (Button)findViewById(R.id.Change_btn);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, newuser.class);
                intent.putExtra("theuser", "olduser");
                intent.putExtra("username", username.getText().toString());
                MainActivity.this.startActivity(intent);

            }
        });
        btnIntoTest = (Button)findViewById(R.id.IntoTest_btn);
        btnIntoTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ble_Activity.class);
                intent.putExtra(BleMainActivity.GetUsername, username.getText().toString());
                MainActivity.this.startActivity(intent);
                try
                {
                    MainActivity.this.startActivity(intent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        btnQuerry = (Button)findViewById(R.id.Querry_btn);
        btnQuerry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(MainActivity.this, SelecteTheDataTime.class);
               // intent.putExtra("GetUsername", selecttomanager.this.uname);
                //selecttomanager.this.startActivity(intent);
            }
        });


    }
    private void Deluserinfobyusername(String strname)
    {
        final String name = strname;
        new AlertDialog.Builder(MainActivity.this).setTitle("系统提示").setMessage("请确认是否删除用户  '" + strname + "'").setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
                MainActivity.this.dbHelper.getWritableDatabase().delete("LiangZiUser", "name = ?", new String[] {name});
                UpdateData();

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
            }
        }).show();
    }
    private void UpdateData()
    {
        if(init_user())
        {
            listname.setSelection(0);
            User user = (User) listname.getItemAtPosition(0);
            String name = user.getName();
            username.setText(name);
            find_userinfobyusername(name);
            userListAdapter adapter = new userListAdapter(MainActivity.this,R.layout.user_item,ALLUserList);
            listname.setAdapter(adapter);
        }
        else
        {
            username.setText("");
            userage.setText("");
            userheight.setText("");
            userweight.setText("");
            address.setText("");
            phone.setText("");
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

    ///////////////////////////list部分处理/////////////////////////////////////

    public Boolean init_user() {
        ALLUserList.clear();
        dbHelper = new MyDatabaseHelper(this, "LiangZiUser.db", null, 2);
        SQLiteDatabase readdata = dbHelper.getWritableDatabase();
        Cursor cursor = readdata.rawQuery("select name from liangziuser", null);
        if (cursor.getCount()==0)
        {
            return false;
        }
        else if (cursor != null) {
            if (!(cursor.isFirst())) {
                cursor.moveToFirst();
            }
            if (cursor.isFirst()) {
                do  {
                    User user = new User("");
                    user.name  = cursor.getString(cursor.getColumnIndex("name"));
                    Log.e("user.name",user.name);
                    ALLUserList.add(user);
                }while(cursor.moveToNext());
                cursor.close();
                dbHelper.close();
            }
        }
        return  true;
    }


    public void find_userinfobyusername(String username) {
        dbHelper = new MyDatabaseHelper(this, "LiangZiUser.db", null, 2);
        SQLiteDatabase readdata = dbHelper.getWritableDatabase();
        Cursor cursor = this.dbHelper.getWritableDatabase().rawQuery("select * from liangziuser where name=?", new String[] { username });
        if (cursor != null) {
            if (!(cursor.isFirst())) {
                cursor.moveToFirst();
            }
            if (cursor.isFirst()) {
                do  {
                    userage.setText(cursor.getInt(cursor.getColumnIndex("age"))+"岁");
                    phone.setText(cursor.getString(cursor.getColumnIndex("tel")));
                    userheight.setText(cursor.getString(cursor.getColumnIndex("height"))+"cm");
                    userweight.setText(cursor.getString(cursor.getColumnIndex("weight"))+"公斤");
                    address.setText(cursor.getString(cursor.getColumnIndex("addr")));
                }while(cursor.moveToNext());
                cursor.close();
                dbHelper.close();
            }
        }
    }
    private class UserInfo
    {
        private String useraddr;
        private int userage;
        private String userheight;
        private String username;
        private String usersex;
        private String usertel;
        private String userweight;

        UserInfo(String paramString1, String paramString2, String paramString3, String paramInt, int paramString4, String paramString5, String paramString6)
        {
            this.username = paramString1;
            this.usersex = paramString2;
            this.userheight = paramInt;
            this.userweight = paramString3;
            this.userage = paramString4;
            this.usertel = paramString5;

            this.useraddr = paramString6;
        }

        public String getuseraddr()
        {
            return this.useraddr;
        }

        public int getuserage()
        {
            return this.userage;
        }

        public String getuserheight()
        {
            return this.userheight;
        }

        public String getusername()
        {
            return this.username;
        }

        public String getusersex()
        {
            return this.usersex;
        }

        public String getusertel()
        {
            return this.usertel;
        }

        public String getuserweight()
        {
            return this.userweight;
        }
    }
    public class User
    {
        private String name;
        public User(String name)
        {
            this.name = name;
        }
        public String getName()
        {
            return name;
        }
    }
    private class userListAdapter extends ArrayAdapter<User> {
 //       private LayoutInflater layoutInflater;
        public userListAdapter(Context context, int resource, List<User> objects) {
            super(context, resource, objects);
  //          this.layoutInflater = layoutInflater;
        }
        class ViewHolder
        {
            TextView tv_uname;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            User user = (User) getItem(position);
            View view = null;
            ViewHolder viewHolder;
            Log.e("getView","User user = (User) getItem(position);");
            if (convertView == null) {
                Log.e("getView","convertView == null");
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.user_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_uname = ((TextView) view.findViewById(R.id.list_user_item_txt));
                view.setTag(viewHolder);
            }
            else
            {
                view = convertView;
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_uname.setText(user.name);
            return view;
        }
    }

/*    private class userListAdapter extends ArrayAdapter
    {
        private LayoutInflater layoutInflater;


        public userListAdapter(Context context, int textViewResourceId, List<UserInfo> objects) {
            super(context,textViewResourceId, objects);
            this.layoutInflater = layoutInflater;
        }
        class viewuserinfo
        {
            TextView tv_uaddr;
            TextView tv_uage;
            TextView tv_uheight;
            TextView tv_uname;
            TextView tv_usex;
            TextView tv_utel;
            TextView tv_uweight;
            viewuserinfo()
            {
            }
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            UserInfo localUserInfo = (UserInfo) getItem(paramInt);
            userListAdapter.viewuserinfo Viewuserinfo;
            View view;

            if (paramView == null)
            {
                LayoutInflater inflater=(LayoutInflater)paramViewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                paramView = inflater.inflate(R.layout.activity_main, null);
                Viewuserinfo = new userListAdapter.viewuserinfo();
      /*          Viewuserinfo.tv_uname = ((TextView) paramView.findViewById(R.id.tv_username));
                Viewuserinfo.tv_usex = ((TextView) paramView.findViewById(R.id.tv_usersex));
                Viewuserinfo.tv_uheight = ((TextView) paramView.findViewById(R.id.tv_userheight));
                Viewuserinfo.tv_uweight = ((TextView) paramView.findViewById(R.id.tv_userweight));
                Viewuserinfo.tv_uage = ((TextView) paramView.findViewById(R.id.tv_userage));
                Viewuserinfo.tv_utel = ((TextView) paramView.findViewById(R.id.tv_usertel));
                Viewuserinfo.tv_uaddr = ((TextView) paramView.findViewById(R.id.tv_useraddr));*/
  /*              paramView.setTag(Viewuserinfo);
            } else {
                Viewuserinfo = (userListAdapter.viewuserinfo) paramView.getTag();
            }
            Viewuserinfo.tv_uname.setText(localUserInfo.getusername());
            Viewuserinfo.tv_usex.setText(localUserInfo.getusersex());
            Viewuserinfo.tv_uheight.setText(localUserInfo.getuserheight());
            Viewuserinfo.tv_uweight.setText(localUserInfo.getuserweight());
            Viewuserinfo.tv_uage.setText((localUserInfo.getuserage() + ""));
            Viewuserinfo.tv_utel.setText(localUserInfo.getusertel());
            Viewuserinfo.tv_uaddr.setText(localUserInfo.getuseraddr());
            return paramView;
        }
    }*/
}



