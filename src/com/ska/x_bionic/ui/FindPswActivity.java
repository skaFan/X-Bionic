package com.ska.x_bionic.ui;

import com.ska.x_bionic.R;
import com.ska.x_bionic.R.layout;
import com.ska.x_bionic.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class FindPswActivity extends Activity implements OnClickListener{
	private TextView tvFindbyEmail,tvFindbyMobile;
	private ImageButton fpBack;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_psw);
		tvFindbyEmail=(TextView)findViewById(R.id.tv_findpsw_by_em);
		tvFindbyMobile=(TextView)findViewById(R.id.tv_findpsw_by_mb);
		fpBack=(ImageButton)findViewById(R.id.ib_fp_back);
		tvFindbyEmail.setOnClickListener(this);
		tvFindbyMobile.setOnClickListener(this);
		fpBack.setOnClickListener(this);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forget_psw, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.tv_findpsw_by_em:
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			break;
		case R.id.ib_fp_back:
			this.finish();
			break;
		}
		
	}

}
