package com.ska.x_bionic.ui;

import com.ska.x_bionic.R;
import com.ska.x_bionic.R.layout;
import com.ska.x_bionic.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RegisterVipActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_vipctivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_vipctivity, menu);
		return true;
	}

}
