package com.ska.x_bionic.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ska.x_bionic.R;
import com.ska.x_bionic.application.MyApplication;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.image.ImageFetcher;
import com.ska.x_bionic.model.ProductDetailImage;
import com.ska.x_bionic.util.ConnectivityUtil;
import com.ska.x_bionic.util.JsonUtil;

public class ProductDetailActivity extends DrawerActivity {
	private int id;
	private List<ProductDetailImage> list;
	private int status;
	private Object object = new Object();
	private int count;
    private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);
		initDrawerLayout();
		ivList = (ImageView) findViewById(R.id.iv_list);
		ivList.setOnClickListener(this);
		Bundle bundle = this.getIntent().getExtras();
		id = bundle.getInt("pid", 0);
		

		if (ConnectivityUtil.isOnline(this)) {

			new GetImageUrl().execute(id);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请检查网络").setMessage("网络未连接！")
					.setPositiveButton("确定", null).create().show();
		}

	}

	class GetImageUrl extends AsyncTask<Integer, Void, Void> {
		
		
		@Override
		protected void onPreExecute() {
			pd=ProgressDialog.show(ProductDetailActivity.this, "请稍后", "数据加载中...");
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Integer... arg0) {
			int productId = arg0[0];
			String url = "product/images/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", productId);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jason = "";

			try {
				jason = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				status = responseJsonEntity.getStatus();
				if (status == 200) {
					MyApplication.clearPictureUrlList();
					String data = responseJsonEntity.getData();
					list = JsonUtil
							.toObjectList(data, ProductDetailImage.class);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			for (int i = 0; i < list.size(); i++) {
				String Imageurl = list.get(i).image;
				new GetAbsUrl().execute(HttpHelper.DOMAIN_URL_IMAGE3 + Imageurl
						+ "_XL.jpg");
			}

			super.onPostExecute(result);

		}
	}

	class GetAbsUrl extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {

			if (status == 200) {
				ImageFetcher.downLoadImage(arg0[0]);
			}
			return arg0[0];
		}

		@Override
		protected void onPostExecute(String result) {
			
			
			if (status == 200) {
				synchronized (object) {
					count++;
					MyApplication.addPictureUrl(result);
				}
			}
			if (count == list.size()) {
				ProductDetailFragment frg = new ProductDetailFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("id", id);
				frg.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.add(R.id.main_content, frg).commit();
				pd.dismiss();
			}

			super.onPostExecute(result);
		}

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
		// Log.i("111", String.valueOf(list.size()));

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
		getMenuInflater().inflate(R.menu.product_detail, menu);
		return true;
	}

}
