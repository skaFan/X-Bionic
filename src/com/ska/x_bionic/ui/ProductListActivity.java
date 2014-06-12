package com.ska.x_bionic.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ska.x_bionic.R;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.model.DetailProductList;
import com.ska.x_bionic.util.ConnectivityUtil;
import com.ska.x_bionic.util.JsonUtil;

public class ProductListActivity extends ActionBarActivity {
	private int ProductId, ListId;
	private ActionBar actionBar;
	private List<DetailProductList> list;
	private ViewPager viewPager;
	private FragAdapter mFragAdapter;
	private int desc = 0;
	private int cid;
	private ProductListFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detai_product_list);

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		Bundle bundle = this.getIntent().getExtras();
		ProductId = bundle.getInt("ProductId", 0);
		ListId = bundle.getInt("ListId", 0);
		Log.i("afsdfasdfasdf", ProductId + "");
		Log.i("dafdfa", ListId + "");
		// 使自定义的普通View能在title栏显示, actionBar.setCustomView能起作用.
		actionBar.setDisplayShowCustomEnabled(true);
		getView();

		if (ConnectivityUtil.isOnline(this)) {

			new productListTitle().execute(ListId, ProductId);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请检查网络").setMessage("网络未连接！")
					.setPositiveButton("确定", null).create().show();
		}

	}

	// 异步获得点击EXpendview子项后响应得到的list

	class productListTitle extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... arg0) {
			int navid = arg0[0];
			int subNavid = arg0[1];
			String url = "category/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("navId", navid);
			map.put("subNavId", subNavid);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jason = "";

			try {
				jason = HttpHelper.execute(entity);
				Log.i("abcdefg", jason);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				int status = responseJsonEntity.getStatus();
				if (status == 200) {
					String data = responseJsonEntity.getData();
					list = JsonUtil.toObjectList(data, DetailProductList.class);
					Log.i("size", list.size() + "");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// //

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			viewPager = (ViewPager) findViewById(R.id.vp_productlist);
			mFragAdapter = new FragAdapter();
			viewPager.setAdapter(mFragAdapter);
			viewPager.setOnPageChangeListener(mPageChangeListener);

			// actionbar 的tab导航所要有的项，即点击子项后得到list的categoryName
			for (int i = 0; i < list.size(); i++) {
				Tab tab = actionBar.newTab();
				tab.setText(list.get(i).categoryName);
				tab.setTabListener(tabListener);
				actionBar.addTab(tab);
			}

			super.onPostExecute(result);

		}
	}

	// tab 的监听

	private TabListener tabListener = new TabListener() {

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {

		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			viewPager.setCurrentItem(arg0.getPosition());
		}

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

		}
	};

	private class FragAdapter extends FragmentStatePagerAdapter {

		public FragAdapter() {
			super(getSupportFragmentManager());
		}

		@Override
		public Fragment getItem(int arg0) {
			fragment = new ProductListFragment();
			Bundle bundle = new Bundle();
			// cid=list.get(arg0).id;
			bundle.putInt("id", cid);
			bundle.putInt("navId", ListId);
			bundle.putInt("subNavId", ProductId);
			bundle.putInt("desc", desc);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return list.size();

		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}
	}

	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			getSupportActionBar().setSelectedNavigationItem(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detai_product_list, menu);
		return true;
	}

	// 加载排序的
	private void getView() {
		View actionbarView = getLayoutInflater().inflate(R.layout.my_spinner,
				null);
		Spinner spOrder = (Spinner) actionbarView.findViewById(R.id.sp_order);
		// String[] item = new String[]{"",""};
		String[] item = getResources().getStringArray(R.array.order);
		ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this,
				R.layout.support_simple_spinner_dropdown_item, item);
		spOrder.setAdapter(spAdapter);
		// 设置spinner选项的监听事件
		spOrder.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				desc = position;
				Log.i("desc111111", desc + "");

				fragment = new ProductListFragment();
				Bundle bundle = new Bundle();
				// bundle.putInt("id", cid);
				bundle.putInt("navId", ListId);
				bundle.putInt("subNavId", ProductId);
				bundle.putInt("desc", desc);
				fragment.setArguments(bundle);
				if (mFragAdapter != null) {
					mFragAdapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		// 在actionBar上显示定制actionbarView
		getSupportActionBar().setCustomView(actionbarView);

	}

}
