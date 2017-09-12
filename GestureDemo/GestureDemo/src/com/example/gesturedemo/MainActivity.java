package com.example.gesturedemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void next(View view) {
		startActivity(new Intent(this, FirstActivity.class));
		finish();
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}
//  进来的第一个界面 没有 上一个界面
	@Override
	public void pre(View view) {
		// TODO Auto-generated method stub
		
	}

}
