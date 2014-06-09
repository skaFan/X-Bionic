package com.ska.x_bionic.ui;

import com.ska.x_bionic.R;
import com.ska.x_bionic.R.layout;
import com.ska.x_bionic.R.menu;
import com.ska.x_bionic.ui.DrawerActivity.MyAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class ProductMainActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
	
		   ivList=(ImageView)findViewById(R.id.iv_list);
	        ivList.setOnClickListener(this);
			initDrawerLayout();
			getSupportFragmentManager().beginTransaction().add(R.id.main_content,new ProductMainFragment()).commit();
		
	}
	
	private void initDrawerLayout() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		View view = getLayoutInflater().inflate(R.layout.main_listview_head,
				null);
		mDrawerList.addHeaderView(view);
		mDrawerList.setAdapter(new MyAdapter());
		mDrawerList.setOnItemClickListener(onItemClickListener);
		initializeDrawerListener();
		Log.i("111", String.valueOf(list.size()));

	}
	private void initializeDrawerListener() {
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_launcher, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				
				super.onDrawerOpened(drawerView);
			}

		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_list, menu);
		return true;
	}

}
