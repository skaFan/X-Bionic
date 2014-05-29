package com.ska.x_bionic.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ProgressBar;

import com.ska.x_bionic.R;

public class FlashActivity extends Activity {
	private String value;
	private ProgressBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash);
		Handler handler = new Handler();
		SharedPreferences sp = getSharedPreferences("hadlogin", 0);
		value = sp.getString("key", "");
		begin();
	}

	public void begin() {
		bar = (ProgressBar) findViewById(R.id.progress_bar);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (bar.getProgress() >= 100) {
						if (value != "" && value != null) {
							Intent intent = new Intent(FlashActivity.this,
									LoginActivity.class);
							startActivity(intent);
						} else {
							Intent intent = new Intent(FlashActivity.this,
									WelcomActivity.class);
							startActivity(intent);
						}
						finish();
						return;
					}
					try {
						Thread.sleep(300);
						bar.incrementProgressBy(5);
					} catch (InterruptedException e) {
						while (true)
							e.printStackTrace();
					}
				}

			}
		}).start();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash, menu);
		return true;
	}

}
