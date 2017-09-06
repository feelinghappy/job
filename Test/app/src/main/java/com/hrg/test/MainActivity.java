package com.hrg.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.File;
import com.hrg.test.HttpDownloader;

import static android.os.Environment.getRootDirectory;


public class MainActivity extends AppCompatActivity {
    Button btnOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btngenerate =(Button)findViewById(R.id.generate_qrcode);
        btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GenerateActivity.class));
            }
        });
        Button btnscan = (Button)findViewById(R.id.scan_qrcode);
        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ScanActivity.class));
            }
        });
        btnOpen = (Button)findViewById(R.id.open);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getRootDirectory().getAbsolutePath();//获取手机根目录
                path = path +"/"+ "报告模板——3D-CELL宇航员体检3大系统9项.pdf";
                Intent intent = getPdfFileIntent(path);
                startActivity(intent);

            }
        });

    }

    public Intent getPdfFileIntent(String path){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri=Uri.fromFile(new File(path));
        i.setDataAndType(uri,"application/pdf");
        return i;
    }
}



