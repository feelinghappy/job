package com.hrg.video;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;
import com.wilddog.video.Conversation;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class UserListActivity extends AppCompatActivity {

    @BindView(R.id.lv_user_list) ListView lvUsers;
    private SyncReference mRef;

    private List<String> userList = new ArrayList<>();
    private ChildEventListener childEventListener;
    private String mUid;
    private String participantId;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_user_list);
        Log.e("UserListActivity","activity_user_list");
        hideVirtualKey();
        hideNavigationBar();
        ButterKnife.bind(this);
        String uid = WilddogAuth.getInstance().getCurrentUser().getUid();
        //初始化Video
        TextView tv = (TextView)findViewById(R.id.videoid) ;
        tv.setText(uid);
        mRef = WilddogSync.getInstance().getReference("users");
        mUid = WilddogAuth.getInstance().getCurrentUser().getUid();
        lvUsers = (ListView) findViewById(R.id.lv_user_list);
        TextView textViewButton = (TextView)findViewById(R.id.NavigateBack) ;
        textViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(UserListActivity.this, ConversationActivity.class), 0);
            }
        });

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    //获取用户Wilddog ID并添加到用户列表中
                    String uid = dataSnapshot.getKey();
                    if (!mUid.equals(uid)) {
                        userList.add(uid);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    //用户离开时，从用户列表中删除用户数据
                    String uid = dataSnapshot.getKey();
                    if (!mUid.equals(uid)) {
                        userList.remove(uid);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(SyncError wilddogError) {

            }
        };
        //监听users节点
        mRef.addChildEventListener(childEventListener);


        adapter = new MyAdapter(userList, this);
        lvUsers.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.removeEventListener(childEventListener);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);

    }

    class MyAdapter extends BaseAdapter {
        private List<String> mList = new ArrayList<>();
        private LayoutInflater mInflater;

        MyAdapter(List<String> userList, Context context) {
            mList = userList;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {


            view = mInflater.inflate(R.layout.layout_participent_list, null);
            Button invite = (Button) view.findViewById(R.id.btn_item_invite);
            TextView id = (TextView) view.findViewById(R.id.tv_item_participent);
            id.setText(mList.get(i));
            invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    participantId = mList.get(i);
                    Intent intent = new Intent();
                    intent.putExtra("participant", participantId);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            return view;
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

}
