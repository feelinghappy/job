package com.hrg.healthmanager.Infoadmin.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper
{
    public static final String CREATE_BOOK = "create table liangziuser (id integer primary key autoincrement, name text ,height text, weight text,age integer,tel text,addr text,sex text )";
    public static final String CREATE_CATEGORY = "create table liangziresult (id integer primary key autoincrement, name text ,time text ,degree text ,result text)";
    public static final String CREATE_PASSWORD = "create table liangzijiami (id integer primary key autoincrement, jiami text)";
    private Context mContext;

    public MyDatabaseHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
    {
        super(paramContext, paramString, paramCursorFactory, paramInt);
        this.mContext = paramContext;
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
        paramSQLiteDatabase.execSQL("create table liangziuser (id integer primary key autoincrement, name text ,height text, weight text,age integer,tel text,addr text,sex text )");
        paramSQLiteDatabase.execSQL("create table liangziresult (id integer primary key autoincrement, name text ,time text ,degree text ,result text)");
        paramSQLiteDatabase.execSQL("create table liangzijiami (id integer primary key autoincrement, jiami text)");
        Toast.makeText(this.mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
        paramSQLiteDatabase.execSQL("drop table if exists Book");
        paramSQLiteDatabase.execSQL("drop table if exists Category");
        onCreate(paramSQLiteDatabase);
    }
}