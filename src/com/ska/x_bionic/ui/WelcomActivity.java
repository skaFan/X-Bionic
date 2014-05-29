package com.ska.x_bionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import android.widget.ImageView;

import com.ska.x_bionic.R;

public class WelcomActivity extends ActionBarActivity {

	private ViewPager viewpager;
	// private ImageView iv1,iv2,iv3;
	private List<ImageView> imgList;
	private viewpagerAdapert adapert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		SharedPreferences sp = getSharedPreferences("hadlogin", 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("key", "pass");
		editor.commit();

		viewpager = (ViewPager) findViewById(R.id.view_viewpager);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		pointPicture();
		adapert = new viewpagerAdapert();
		viewpager.setAdapter(adapert);
		viewpager.setOnPageChangeListener(mViewPagerListener);
	}

	class viewpagerAdapert extends FragmentStatePagerAdapter {

		public viewpagerAdapert() {
			super(getSupportFragmentManager());
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			WelcomFragment welcome = new WelcomFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("position", arg0);
			welcome.setArguments(bundle);
			return welcome;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

	}

	private OnPageChangeListener mViewPagerListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < imgList.size(); i++) {
				if (arg0 == i) {
					imgList.get(i).setBackgroundResource(R.drawable.yes);

				} else {
					imgList.get(i).setBackgroundResource(R.drawable.no);
				}

			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation, menu);
		return true;
	}

	private void pointPicture() {
		imgList = new ArrayList<ImageView>();
		imgList.add((ImageView) findViewById(R.id.one));
		imgList.add((ImageView) findViewById(R.id.two));
		imgList.add((ImageView) findViewById(R.id.three));

	}

}
