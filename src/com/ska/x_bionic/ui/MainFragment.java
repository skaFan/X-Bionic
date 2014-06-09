package com.ska.x_bionic.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ska.x_bionic.R;

public class MainFragment extends Fragment{
	private ViewPager mViewpPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
		mViewpPager = (ViewPager) view.findViewById(R.id.vp_main);
		mViewpPager.setAdapter(new MyAdapter());
		mViewpPager.setOnPageChangeListener(mViewPagerListener);
		


		return view;
	}
	
	class MyAdapter extends FragmentStatePagerAdapter{

		public MyAdapter() {
			super(getActivity().getSupportFragmentManager());
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			MainFragmentImage fi = new MainFragmentImage();
			Bundle bundle = new Bundle();
			bundle.putInt("position", arg0);
			fi.setArguments(bundle);
			return fi;
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}
		
	}

	private OnPageChangeListener mViewPagerListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			mViewpPager.setCurrentItem(arg0);
			
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


}
