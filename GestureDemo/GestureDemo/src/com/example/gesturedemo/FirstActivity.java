package com.example.gesturedemo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_01);
	}

	@Override
	public void next(View view) {
		startActivity(new Intent(this, SecondActivity.class));
		finish();
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void pre(View view) {
		startActivity(new Intent(this, MainActivity.class));
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
}
