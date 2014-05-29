package com.ska.x_bionic.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ska.x_bionic.R;

public class LoginActivity extends Activity implements OnClickListener {
	private TextView tvQQ, tvHelp, tvBl, tvSina, tvNewUser, tvService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		tvQQ = (TextView) findViewById(R.id.tv_qq);
		tvHelp = (TextView) findViewById(R.id.ib_heipitem);
		tvBl = (TextView) findViewById(R.id.tv_bl);
		tvSina = (TextView) findViewById(R.id.tv_sina);
		tvNewUser = (TextView) findViewById(R.id.tv_register);
		tvService = (TextView) findViewById(R.id.ib_serviceitem);

		tvBl.setOnClickListener(this);
		tvHelp.setOnClickListener(this);
		tvQQ.setOnClickListener(this);
		tvSina.setOnClickListener(this);
		tvService.setOnClickListener(this);
		tvNewUser.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// 退出提示
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("确定要退出吗");
			// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);
			// 显示对话框
			isExit.show();

		}

		return false;
	}

	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				// ActivityManager activityMgr= (ActivityManager)
				// this.getSystemService(ACTIVITY_SERVICE);
				//
				// activityMgr.restartPackage(getPackageName());
				System.exit(0);
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_qq:
			Intent intent = new Intent(this, QQloginActivity.class);
			startActivity(intent);
			break;

		case R.id.tv_sina:
			Intent intent2 = new Intent(this, SinaLoginActivity.class);
			startActivity(intent2);
			break;
		case R.id.tv_bl:
			Intent intent3 = new Intent(this, BlLoginActivity.class);
			startActivity(intent3);
			break;
		case R.id.tv_register:
			Intent intent4 = new Intent(this,NewUserActivity.class);
			startActivity(intent4);
			//
			break;
		case R.id.ib_heipitem:
			Intent intent5 = new Intent(this, WelcomActivity.class);
			startActivity(intent5);
			break;
		case R.id.ib_serviceitem:
			Intent intent6 = new Intent();
			break;
		}

	}

}
