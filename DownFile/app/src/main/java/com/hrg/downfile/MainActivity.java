package com.hrg.downfile;

import android.app.Activity;
import android.app.DownloadManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.hrg.downfile.DownloadService;

public class MainActivity extends Activity {
    Button btnDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = (Button)findViewById(R.id.button);
        final Intent intent = new Intent(this,DownloadService.class);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });
    }
    protected void onClose()
    {

    }
    protected void onDestry()
    {

    }



}

