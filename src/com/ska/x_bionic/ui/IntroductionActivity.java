package com.ska.x_bionic.ui;

import com.ska.x_bionic.R;
import com.ska.x_bionic.R.layout;
import com.ska.x_bionic.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class IntroductionActivity extends ActionBarActivity implements OnClickListener{
	private ImageButton ibBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduction);
		ibBack=(ImageButton)findViewById(R.id.ib_it_back);
		ibBack.setOnClickListener(this);
		getSupportActionBar().hide();
		getSupportFragmentManager().beginTransaction().add(R.id.main_content, new IntroductionFragment()).commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.ib_it_back:
			this.finish();
			break;
		
		}
		
	}

}
