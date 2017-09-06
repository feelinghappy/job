package com.hrg.healthmanager.Infoadmin.olduser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.hrg.healthmanager.Infoadmin.database.MyDatabaseHelper;
import com.hrg.healthmanager.R;

import java.util.ArrayList;
import java.util.List;
import static android.view.Window.FEATURE_NO_TITLE;

public class olduser extends Activity
{
    private List<UserInfo> ALLUserInfoList = new ArrayList();
    private List<UserInfo> OldUserInfoList = new ArrayList();
    private userListAdapter UserListAdapter;
    private olduser.UserInfo getuserinfo;

    private MyDatabaseHelper dbHelper;
    private EditText searchEt;

    private void getmDataSub()
    {
        String str = this.searchEt.getText().toString();
        int j = this.ALLUserInfoList.size();
        this.OldUserInfoList.clear();
        int i = 0;
        while (true)
        {
            if (i >= j)
                return;
            if (((UserInfo)this.ALLUserInfoList.get(i)).getusername().contains(str))
                this.OldUserInfoList.add((UserInfo)this.ALLUserInfoList.get(i));
            i += 1;
        }
    }

    public void init_userinfo() {
        ALLUserInfoList.clear();
        OldUserInfoList.clear();
        UserInfo info = new UserInfo("", "", "", "", 0, "", "");
        dbHelper = new MyDatabaseHelper(this, "LiangZiUser.db", null, 2);
        SQLiteDatabase readdata = dbHelper.getWritableDatabase();
        Cursor cursor = readdata.rawQuery("select * from liangziuser", null);
        if (cursor != null) {
            if (!(cursor.isFirst())) {
                cursor.moveToFirst();
            }
            if (cursor.isFirst()) {
                do  {
                    info.userage = cursor.getInt(cursor.getColumnIndex("age"));
                    info.username = cursor.getString(cursor.getColumnIndex("name"));
                    info.usersex = cursor.getString(cursor.getColumnIndex("sex"));
                    info.userheight = cursor.getString(cursor.getColumnIndex("height"));
                    info.userweight = cursor.getString(cursor.getColumnIndex("weight"));
                    info.usertel = cursor.getString(cursor.getColumnIndex("tel"));
                    info.useraddr = cursor.getString(cursor.getColumnIndex("addr"));
                    ALLUserInfoList.add(info);
                    OldUserInfoList.add(info);
                }while(cursor.moveToNext());
                cursor.close();
                dbHelper.close();
            }
        }
    }


    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        requestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.olduser);
        init_userinfo();
        UserListAdapter = new userListAdapter(this, R.layout.userlist, this.ALLUserInfoList);
        this.searchEt = ((EditText)findViewById(R.id.searchEt));
        ListView list = (ListView)findViewById(R.id.lv_olduserlist);
        list.setAdapter(UserListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
            {
                String str = ((olduser.UserInfo)olduser.this.OldUserInfoList.get(paramInt)).getusername();
                Intent intent = new Intent(olduser.this, selecttomanager.class);
                intent.putExtra(selecttomanager.username, str);
                olduser.this.startActivity(intent);
                olduser.this.finish();
            }
        });
        this.searchEt.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable paramEditable)
            {
        		olduser.this.getmDataSub();
        		olduser.this.UserListAdapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
            {
            }

            public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
            {
            }
        });
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

  private class userListAdapter extends ArrayAdapter
  {
      private LayoutInflater layoutInflater;


      public userListAdapter( Context context, int textViewResourceId, List<olduser.UserInfo> objects) {
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
          olduser.UserInfo localUserInfo = (olduser.UserInfo) getItem(paramInt);
          olduser.userListAdapter.viewuserinfo Viewuserinfo;
          View view;

          if (paramView == null)
          {
              LayoutInflater inflater=(LayoutInflater)paramViewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              paramView = inflater.inflate(R.layout.userlist, null);
              Viewuserinfo = new olduser.userListAdapter.viewuserinfo();
              Viewuserinfo.tv_uname = ((TextView) paramView.findViewById(R.id.tv_username));
              Viewuserinfo.tv_usex = ((TextView) paramView.findViewById(R.id.tv_usersex));
              Viewuserinfo.tv_uheight = ((TextView) paramView.findViewById(R.id.tv_userheight));
              Viewuserinfo.tv_uweight = ((TextView) paramView.findViewById(R.id.tv_userweight));
              Viewuserinfo.tv_uage = ((TextView) paramView.findViewById(R.id.tv_userage));
              Viewuserinfo.tv_utel = ((TextView) paramView.findViewById(R.id.tv_usertel));
              Viewuserinfo.tv_uaddr = ((TextView) paramView.findViewById(R.id.tv_useraddr));
              paramView.setTag(Viewuserinfo);
          } else {
              Viewuserinfo = (olduser.userListAdapter.viewuserinfo) paramView.getTag();
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
    }
}