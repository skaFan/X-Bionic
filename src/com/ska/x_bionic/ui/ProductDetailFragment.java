package com.ska.x_bionic.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.ska.x_bionic.R;
import com.ska.x_bionic.application.MyApplication;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.image.ImageFetcher;
import com.ska.x_bionic.model.DetailProductInformation2;
import com.ska.x_bionic.model.Kjd;
import com.ska.x_bionic.model.LeaveNum;
import com.ska.x_bionic.model.Mcolor;
import com.ska.x_bionic.model.MyProductSize;
import com.ska.x_bionic.model.SizeCloth;
import com.ska.x_bionic.model.SizeStand;
import com.ska.x_bionic.util.ConnectivityUtil;
import com.ska.x_bionic.util.JsonUtil;
import com.ska.x_bionic.util.ToastUtil;

public class ProductDetailFragment extends Fragment implements OnClickListener {
	private ViewPager mViewPager;
	private int id, colorId = 98765, SizeId = 98765, qty, productQuantityId,
			haveNum;
	private TextView tvName, tvPrice, tvNum, btAdd;
	private ProgressDialog pd;
	private EditText etNum;
	private DetailProductInformation2 mInfo;
	private MyProductSize mSize;
	private LeaveNum mNum;
	private List<Mcolor> McolorList;
	private List<SizeCloth> mSizeList;
	private List<SizeStand> mSiseStandList;
	private int status;
	private Object object = new Object();
	private int count;
	private LinearLayout ll, llsize, llkjd;
	private Activity thisActivity;
	private TableLayout table;
	private List<Kjd> mKjdList;
	private Bitmap bm;
	private Button btBuy;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_productdetail, null);
		tvPrice = (TextView) view.findViewById(R.id.price);
		tvName = (TextView) view.findViewById(R.id.tv_pname);
		tvNum = (TextView) view.findViewById(R.id.tv_leftnum);
		etNum = (EditText) view.findViewById(R.id.et_num);
		btAdd = (TextView) view.findViewById(R.id.tv_addtocar);
		btAdd.setOnClickListener(this);
		// 尺码
		table = (TableLayout) view.findViewById(R.id.tbl_size);
		table.setStretchAllColumns(true);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 50;
		bm = BitmapFactory
				.decodeResource(getResources(), R.drawable.line, opts);
		table.setDividerDrawable(new BitmapDrawable(bm));
		table.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE

		);

		ll = (LinearLayout) view.findViewById(R.id.ll_color);
		llsize = (LinearLayout) view.findViewById(R.id.ll_size);
		llkjd = (LinearLayout) view.findViewById(R.id.ll_kjd);
		btBuy = (Button) view.findViewById(R.id.bt_buy);
		btBuy.setOnClickListener(this);
		thisActivity = getActivity();
		mViewPager = (ViewPager) view.findViewById(R.id.vp_productdetail);
		mViewPager.setAdapter(new MyAdapter());
		mViewPager.setOnPageChangeListener(mViewPagerListener);
		Bundle b = getArguments();
		id = b.getInt("id");
		mViewPager.setOnPageChangeListener(mViewPagerListener);

		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		bundle.putInt("colorId", colorId);
		bundle.putInt("sizeId", SizeId);
		ProductDetailFragment2 fragment2 = new ProductDetailFragment2();
		fragment2.setArguments(bundle);
		getActivity().getSupportFragmentManager().beginTransaction()
				.add(R.id.right_drawer, fragment2).commit();

		if (ConnectivityUtil.isOnline(getActivity())) {
			new GetMsg().execute(id);
			new GetSize().execute(id);
			new Getkjd().execute(id);
		}

		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("X-bionic").setMessage("网络不可用")
					.setPositiveButton("确定", null).create().show();
		}

		return view;
	}

	// 获取图片，产品名等
	class GetMsg extends AsyncTask<Integer, Void, Void> {

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(getActivity(), "请稍后", "数据加载中...");
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Integer... arg0) {
			int pid = arg0[0];
			String url = "product/get.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", pid);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jason = "";
			try {
				jason = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				status = jsonEntity.getStatus();
				if (status == 200) {
					MyApplication.clearColorList();
					String data = jsonEntity.getData();
					mInfo = JsonUtil.toObject(data,
							DetailProductInformation2.class);
					String colorJason = mInfo.sysColorList;
					Log.i("colorJason", colorJason);
					McolorList = JsonUtil
							.toObjectList(colorJason, Mcolor.class);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			pd.dismiss();
			if (status == 200) {
				tvName.setText(mInfo.name);
				tvPrice.setText("￥" + " " + String.valueOf(mInfo.price));
			}

			for (int i = 0; i < McolorList.size(); i++) {
				String colorUrl = McolorList.get(i).colorImage;
				new GetAbsUrl().execute(HttpHelper.DOMAIN_URL_IMAGE3 + colorUrl
						+ ".jpg");
			}
			super.onPostExecute(result);
		}

	}

	// 根据图片绝对路径的到图片
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
					MyApplication.addColorUrl(result);

				}
			}
			if (count == McolorList.size()) {
				for (int i = 0; i < count; i++) {
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							80, 80);
					ImageView ivColor = new ImageView(thisActivity);
					lp.leftMargin = 30;
					lp.topMargin = 10;
					// ivColor.setId(McolorList.get(i).id);
					ivColor.setLayoutParams(lp);

					new ImageFetcher().fetch(MyApplication.getColorUrl(i),
							ivColor);
					ll.addView(ivColor);

					final ImageView iv = (ImageView) ll.getChildAt(i);
					final int m = i;
					iv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							colorId = McolorList.get(m).id;
							if (colorId != 0 && SizeId != 0) {
								new GetNum().execute(id, colorId, SizeId);
							}
							for (int j = 0; j < ll.getChildCount(); j++) {
								if (iv == ll.getChildAt(j)) {
									iv.setBackgroundResource(R.drawable.imageview_background);
								} else {
									ll.getChildAt(j)
											.setBackgroundResource(
													R.drawable.imageview_background_normal);
								}
							}

						}
					});

				}

			}

			super.onPostExecute(result);
		}

	}

	// 尺码S M L XL等
	class GetSize extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... arg0) {
			int pid = arg0[0];

			String url = "product/size/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", pid);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jason = null;
			try {
				jason = HttpHelper.execute(entity);
				Log.i("logdfdf", jason);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				status = jsonEntity.getStatus();
				Log.i("statussss", status + "");
				if (status == 200) {
					mSize = new MyProductSize();
					String data = jsonEntity.getData();
					mSize = JsonUtil.toObject(data, MyProductSize.class);
					Log.i("sizesdfasf", data);

					String sizeJason = mSize.sysSizeList;
					String standJason = mSize.sizeStandardDetailList;
					// 表格的尺寸信息
					mSiseStandList = JsonUtil.toObjectList(standJason,
							SizeStand.class);
					Log.i("standJason", mSiseStandList.get(1).size);
					// 尺码大小即尺码中的 S M L的集合
					mSizeList = JsonUtil.toObjectList(sizeJason,
							SizeCloth.class);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@SuppressLint({ "InlinedApi", "NewApi" })
		@Override
		protected void onPostExecute(Void result) {
			if (status == 200) {
				// 动态加载TextView并在其上显示尺码大小
				for (int i = 0; i < mSizeList.size(); i++) {
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							150, 80);
					TextView tvColor = new TextView(thisActivity);
					lp.leftMargin = 30;
					lp.topMargin = 10;
					tvColor.setText(mSizeList.get(i).size);
					tvColor.setGravity(Gravity.CENTER);// 居中
					tvColor.setTextSize(15);
					tvColor.setTextColor(Color.WHITE);
					tvColor.setLayoutParams(lp);
					tvColor.setBackgroundResource(R.drawable.sizeunchoose);
					llsize.addView(tvColor);

					final TextView tv = (TextView) llsize.getChildAt(i);
					final int m = i;
					tv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							SizeId = mSizeList.get(m).id;
							// Toast.makeText(thisActivity, SizeId + "",
							// Toast.LENGTH_SHORT).show();
							if (colorId != 0 && SizeId != 0) {
								new GetNum().execute(id, colorId, SizeId);
							}
							for (int j = 0; j < llsize.getChildCount(); j++) {
								if (tv == llsize.getChildAt(j)) {
									tv.setBackgroundResource(R.drawable.sizechoose);
								} else {
									llsize.getChildAt(j).setBackgroundResource(
											R.drawable.sizeunchoose);
								}
							}

						}
					});

				}

				// 加载尺码表格

				for (int i = 0; i < mSiseStandList.size(); i++) {
					TableLayout.LayoutParams tableLparams = new TableLayout.LayoutParams(
							TableLayout.LayoutParams.MATCH_PARENT,
							TableLayout.LayoutParams.WRAP_CONTENT);

					TableRow tablerow = new TableRow(thisActivity);
					tablerow.setDividerDrawable(new BitmapDrawable(bm));
					tablerow.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE

					);
					SizeStand product = mSiseStandList.get(i);
					TableRow.LayoutParams lp = new TableRow.LayoutParams(
							TableRow.LayoutParams.WRAP_CONTENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					tablerow.addView(addTextView(thisActivity, product.size),
							lp);
					tablerow.addView(addTextView(thisActivity, product.p1), lp);
					tablerow.addView(addTextView(thisActivity, product.p2), lp);
					tablerow.addView(addTextView(thisActivity, product.p4), lp);
					tablerow.addView(addTextView(thisActivity, product.p5), lp);
					tablerow.addView(addTextView(thisActivity, product.p6), lp);
					table.addView(tablerow, lp);

				}

				table.invalidate();

			}

			super.onPostExecute(result);
		}

	}

	// 表格
	private TextView addTextView(Context context, String text) {
		TextView textView = new TextView(thisActivity);

		LinearLayout.LayoutParams ivLp = new LinearLayout.LayoutParams(

		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		textView.setText(text);// 赋值
		textView.setSingleLine();
		textView.setPadding(3, 3, 3, 3);
		textView.setGravity(Gravity.CENTER);// 居中

		textView.setTextSize(15);

		textView.setTextColor(Color.WHITE);

		textView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);

		// textView.setBackgroundResource(R.color.black);

		textView.setLayoutParams(ivLp);

		return textView;

	}

	// 获得库存信息
	class GetNum extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... arg0) {
			String url = "product/quantity/get.do";
			int pid = arg0[0];
			int colorId = arg0[1];
			int sizeId = arg0[2];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", pid);
			map.put("colorId", colorId);
			map.put("sizeId", sizeId);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jason = null;
			try {
				jason = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				status = jsonEntity.getStatus();
				if (status == 200) {
					mNum = new LeaveNum();
					String data = jsonEntity.getData();
					mNum = JsonUtil.toObject(data, LeaveNum.class);
					productQuantityId = mNum.id;
					haveNum = mNum.qty;

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			tvNum.setText("(" + "库存" + String.valueOf(haveNum) + "件" + ")");
			super.onPostExecute(result);
		}

	}

	// 科技点
	class Getkjd extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... arg0) {
			int pid = arg0[0];
			String url = "product/labs/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", pid);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jason = "";
			try {
				jason = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				status = jsonEntity.getStatus();
				if (status == 200) {
					String data = jsonEntity.getData();
					Log.i("mKjdList", data);
					mKjdList = JsonUtil.toObjectList(data, Kjd.class);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (status == 200) {
				for (int i = 0; i < mKjdList.size(); i++) {
					LinearLayout.LayoutParams lp0 = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT);
					View lay = thisActivity.getLayoutInflater().inflate(
							R.layout.view_listkjs, null);
					RelativeLayout kjd = (RelativeLayout) lay
							.findViewById(R.id.kjdzbj);
					ImageView image = (ImageView) lay.findViewById(R.id.iv_kjd);
					TextView textview = (TextView) lay
							.findViewById(R.id.tv_kjd);

					new ImageFetcher().fetch("http://www.bulo2bulo.com"
							+ mKjdList.get(i).imageUrl + "_S.jpg", image);
					textview.setText(mKjdList.get(i).title);
					llkjd.addView(kjd, lp0);

				}

			}

			super.onPostExecute(result);
		}

	}

	// viewpager的适配器

	class MyAdapter extends FragmentStatePagerAdapter {

		public MyAdapter() {
			super(getActivity().getSupportFragmentManager());
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			ProductDetailFragmentImage fragment = new ProductDetailFragmentImage();
			Bundle bundle = new Bundle();
			bundle.putInt("position", arg0);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return MyApplication.getPictureUrlListSize();
		}

	}

	// 滑动Viewpager的监听
	private OnPageChangeListener mViewPagerListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			mViewPager.setCurrentItem(arg0);

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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_buy:
			Intent intent = new Intent(thisActivity, BuyAndInfoActivity.class);
			intent.putExtra("productId", id);
			intent.putExtra("colorId", colorId);
			intent.putExtra("SizeId", SizeId);
			startActivity(intent);
			break;
		case R.id.tv_addtocar:
			if (colorId == 98765 || SizeId == 98765
					|| etNum.getText().toString() == null
					|| etNum.getText().toString().equals("")
					|| Integer.valueOf(etNum.getText().toString()) > haveNum) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						thisActivity);
				builder.setTitle("X-Bionic").setMessage("请输入正确的商品信息")
						.setPositiveButton("确定", null).create().show();

			} else {
				if (ConnectivityUtil.isOnline(getActivity())) {
					new AddToCar().execute(productQuantityId);
				}

				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("X-bionic").setMessage("网络不可用")
							.setPositiveButton("确定", null).create().show();
				}
			}

		}

	}

	class AddToCar extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int productQuantityId = params[0];
			String url = "shoppingcart/update.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("token", MyApplication.token);
			map.put("userId", MyApplication.userId);
			map.put("productQuantityId", productQuantityId);
			map.put("qty", etNum.getText().toString());
			RequestEntity entity = new RequestEntity(url, HttpMethod.POST, map);
			String json = null;
			try {
				json = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(json);
				status = jsonEntity.getStatus();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (status == 200) {
				Toast.makeText(thisActivity, "添加成功", Toast.LENGTH_SHORT).show();
				MyApplication.shopingCarProNum = MyApplication.shopingCarProNum + 1;
				DrawerActivity.btcount.setText(String
						.valueOf(MyApplication.shopingCarProNum));
			} else {
				Toast.makeText(thisActivity, "未能添加成功", Toast.LENGTH_SHORT)
						.show();
			}
			super.onPostExecute(result);
		}

	}

}
