package com.ska.x_bionic.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ska.x_bionic.R;

public class NewUserActivity extends Activity implements OnClickListener {
	
	private ImageButton ibBack; 
	private TextView tvQQReg,tvSinaReg,tvMobleReg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		tvMobleReg=(TextView)findViewById(R.id.tv_mobile_reg);
		ibBack=(ImageButton)findViewById(R.id.ib_user_back);
		ibBack.setOnClickListener(this);
		tvMobleReg.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.ib_user_back:
			this.finish();
			break;
		case R.id.tv_mobile_reg:
			Intent intent = new Intent(this,RegisterByPhoneActivity.class);
			startActivity(intent);
		}
		
	}

}
