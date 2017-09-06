package com.hrg.healthmanager.Infoadmin.olduser;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.hrg.healthmanager.Infoadmin.checkitem.*;
import com.hrg.healthmanager.Infoadmin.database.MyDatabaseHelper;
import java.util.ArrayList;
import java.util.List;
import com.hrg.healthmanager.R;

public class SelecteTheDataTime extends Activity
{
    private MyDatabaseHelper dbHelper;
    private List<String> listDegree = new ArrayList();
    private List<String> listdatatime = new ArrayList();
    private List<String> listresult = new ArrayList();
    private String username;

    private void initlistview()
    {
        this.dbHelper = new MyDatabaseHelper(this, "liangziresult.db", null, 2);
        Cursor localCursor = this.dbHelper.getWritableDatabase().rawQuery("select * from liangziresult where name=?", new String[] { this.username });
        if (localCursor != null)
            localCursor.moveToFirst();
        while (true)
        {
            if (localCursor.isAfterLast())
            {
                localCursor.close();
                return;
            }
            String str1 = localCursor.getString(localCursor.getColumnIndex("result"));
            String str2 = localCursor.getString(localCursor.getColumnIndex("time"));
            String str3 = localCursor.getString(localCursor.getColumnIndex("degree"));
            this.listdatatime.add(str2);
            this.listresult.add(str1);
            this.listDegree.add(str3);
            localCursor.moveToNext();
        }
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.selectedaytime);//  public static final int selectedaytime = 2130903052;
        this.username = getIntent().getStringExtra("GetUsername");
        initlistview();
        ArrayAdapter da = new ArrayAdapter(this, android.R.layout.simple_list_item_2, this.listdatatime);
        ListView localListView = (ListView)findViewById(R.id.lv_datetime);//public static final int lv_datetime = 2131165251;
        localListView.setAdapter(da);
        localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
            {
                Intent intent = new Intent(SelecteTheDataTime.this, checktheitems.class);
                intent.putExtra(checktheitems.GetUsername, SelecteTheDataTime.this.username);
                intent.putExtra("displaymode", "olddisplay");
                intent.putExtra("OldResult", (String)SelecteTheDataTime.this.listresult.get(paramInt));
                intent.putExtra("OldDateTime", (String)SelecteTheDataTime.this.listdatatime.get(paramInt));
                intent.putExtra("OlduserDegree", (String)SelecteTheDataTime.this.listDegree.get(paramInt));
                SelecteTheDataTime.this.startActivity(intent);
            }
        });
    }
}