package com.ska.x_bionic.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

import android.widget.ImageView;
import android.widget.ListView;

import com.ska.x_bionic.R;

public class MainActivity extends DrawerActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//	functionList();
        ivList=(ImageView)findViewById(R.id.iv_list);
        ivList.setOnClickListener(this);
     
		initDrawerLayout();
		getSupportFragmentManager().beginTransaction().add(R.id.main_content, new MainFragment()).commit();

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
		//Log.i("111", String.valueOf(list.size()));

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

//	// 侧拉适配器
//	protected class MyAdapter extends BaseAdapter {
//
//		@Override
//		public int getCount() {
//			return list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View view, ViewGroup parent) {
//			view = getLayoutInflater().inflate(
//					R.layout.view_navi_drawer_item, null);
//			TextView textView = (TextView) view
//					.findViewById(R.id.tv_navi_item_text);
//			textView.setText(list.get(position));
//			return view;
//		}
//	}
//
//	public OnItemClickListener onItemClickListener = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//			Intent intent = null;
//			switch (position) {
//			case 3:
//				intent = new Intent(MainActivity.this, LoginActivity.class);
//				startActivity(intent);
//				break;
//
//			default:
//				break;
//			}
//			mDrawerLayout.closeDrawer(mDrawerList);
//		}
//	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//
//		if (mDrawerToggle.onOptionsItemSelected(item)) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//	
	
	

//	private void functionList() {
//		list = new ArrayList<String>();
//		list.add("您的订购");
//		list.add("账户设置");
//		list.add("达人申请");
//		list.add("部落社区");
//		list.add("购物车");
//		list.add("订阅信息");
//		list.add("分享设置");
//
//	}

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

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.iv_list:
//			mDrawerLayout.openDrawer(mDrawerList);
//			break;
//
//		default:
//			break;
//		}
//		
//	}

}
