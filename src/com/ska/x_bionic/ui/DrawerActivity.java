package com.ska.x_bionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ska.x_bionic.R;
/**
 *此程序中有用侧拉的都继承此类，此类包含侧拉的功能代码，功能都可写这
 * @author DELL-CCC
 *
 */



public class DrawerActivity extends ActionBarActivity implements OnClickListener{
	protected DrawerLayout mDrawerLayout;
	protected ActionBarDrawerToggle mDrawerToggle;
	protected ListView mDrawerList;
	protected List<String> list;
	protected ImageView ivList, ivSearch;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);
		functionList();
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
	}
	
	
	// 侧拉适配器
		protected class MyAdapter extends BaseAdapter {

			@Override
			public int getCount() {
				return list.size();
			}

			@Override
			public Object getItem(int position) {
				return list.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View view, ViewGroup parent) {
				view = getLayoutInflater().inflate(
						R.layout.view_navi_drawer_item, null);
				TextView textView = (TextView) view
						.findViewById(R.id.tv_navi_item_text);
				textView.setText(list.get(position));
				return view;
			}
		}
		
		

		public OnItemClickListener onItemClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = null;
				switch (position) {
				case 3:
					intent = new Intent(DrawerActivity.this, LoginActivity.class);
					startActivity(intent);
					break;

				default:
					break;
				}
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		};
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}

			if (mDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
		


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drawer, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_list:
			mDrawerLayout.openDrawer(mDrawerList);
			break;

		default:
			break;
		}
		
	}
	private void functionList() {
		list = new ArrayList<String>();
		list.add("您的订购");
		list.add("账户设置");
		list.add("达人申请");
		list.add("部落社区");
		list.add("购物车");
		list.add("订阅信息");
		list.add("分享设置");

	}
	

}
