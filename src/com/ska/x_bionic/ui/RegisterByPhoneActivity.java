package com.ska.x_bionic.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ska.x_bionic.R;
import com.ska.x_bionic.util.TextUtil;

public class RegisterByPhoneActivity extends Activity implements
		OnClickListener {
	private ImageButton ibPhoneBack;
	private EditText etPhoneNum;
	private TextView tvGetYzm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ibPhoneBack = (ImageButton) findViewById(R.id.ib_phonereg_back);
		etPhoneNum = (EditText) findViewById(R.id.et_phone_num);
		tvGetYzm = (TextView) findViewById(R.id.tv_getyzm);
		tvGetYzm.setOnClickListener(this);
		ibPhoneBack.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_phonereg_back:
			this.finish();
			break;
		case R.id.tv_getyzm:

			if (!TextUtil.isPhoneNumber(etPhoneNum.getText().toString())) {
				new AlertDialog.Builder(this)

				.setTitle("X-Bionic")

				.setMessage("请输入正确手机号码")

				.setPositiveButton("确定", null)

				.show();
			}
		}

	}
}
