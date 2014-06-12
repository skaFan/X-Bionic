package com.ska.x_bionic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ska.x_bionic.R;
import com.ska.x_bionic.application.MyApplication;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.image.ImageFetcher;
import com.ska.x_bionic.model.ShoppingCarProduct;
import com.ska.x_bionic.model.Shoppingcar;
import com.ska.x_bionic.model.ShoppingcarSysColor;
import com.ska.x_bionic.model.ShoppingcarSysSize;
import com.ska.x_bionic.util.JsonUtil;
import com.ska.x_bionic.util.ToastUtil;

public class BuyAndInfoActivity extends Activity implements
		android.view.View.OnClickListener {
	private List<Shoppingcar> carList;
	private List<ShoppingCarProduct> productList;
	private List<ShoppingcarSysColor> colorList;
	private List<ShoppingcarSysSize> sizeList;
	private ListView mListView;
	private Button btPay;
	private TextView tvAllMoney;
	private int Carposition;
	private int status, sum = 0;;
	private Myadapter adapter;
	private ImageButton ibBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_and_info);
		mListView = (ListView) findViewById(R.id.lv_shopingcar);
		ibBack = (ImageButton) findViewById(R.id.ib_bl_back);
		ibBack.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				BuyAndInfoActivity.this.finish();

			}
		});
		btPay = (Button) findViewById(R.id.pay);
		tvAllMoney = (TextView) findViewById(R.id.tv_allmoney);
		btPay.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BuyAndInfoActivity.this,ProductListActivity.class);
				startActivity(intent);
			}});
		new GetShoppingCar().execute();

	}

	class GetShoppingCar extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			productList = new ArrayList<ShoppingCarProduct>();
			colorList = new ArrayList<ShoppingcarSysColor>();
			sizeList = new ArrayList<ShoppingcarSysSize>();
			String url = "shoppingcart/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("token", MyApplication.token);
			map.put("userId", MyApplication.userId);
			RequestEntity entity = new RequestEntity(url, HttpMethod.POST, map);
			String json = null;
			try {
				json = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(json);
				if (jsonEntity.getStatus() == 200) {
					String data = jsonEntity.getData();
					carList = JsonUtil.toObjectList(data, Shoppingcar.class);
					for (int i = 0; i < carList.size(); i++) {
						ShoppingCarProduct product = JsonUtil.toObject(
								carList.get(i).product,
								ShoppingCarProduct.class);
						productList.add(product);
					}
					for (ShoppingCarProduct product : productList) {
						ShoppingcarSysColor color = JsonUtil.toObject(
								product.sysColor, ShoppingcarSysColor.class);
						ShoppingcarSysSize size = JsonUtil.toObject(
								product.sysSize, ShoppingcarSysSize.class);
						colorList.add(color);
						sizeList.add(size);
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter = new Myadapter();
			mListView.setAdapter(adapter);
			Log.i("ListViewcount", carList.size() + "");
			for (int i = 0; i < carList.size(); i++) {

				sum = sum + productList.get(i).price * carList.get(i).qty;
			}
			tvAllMoney.setText("￥" + sum);
			super.onPostExecute(result);
		}

	}

	class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return carList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return carList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = null;
			if (view == null) {
				holder = new ViewHolder();
				view = getLayoutInflater().inflate(R.layout.view_shoppingcar,
						null);
				holder.ivprotuct = (ImageView) view
						.findViewById(R.id.iv_product);
				holder.ivcolor = (ImageView) view.findViewById(R.id.iv_color);
				holder.tvName = (TextView) view
						.findViewById(R.id.tv_product_name);
				holder.tvPrice = (TextView) view.findViewById(R.id.tv_money);
				holder.tvNum = (TextView) view.findViewById(R.id.tv_num2);
				holder.tvDate = (TextView) view.findViewById(R.id.tv_date2);
				holder.ibdelete = (ImageButton) view
						.findViewById(R.id.ib_delete);
				holder.ibdelete.setTag(position);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.tvName.setText(productList.get(position).name);
			holder.tvPrice.setText("￥ " + productList.get(position).price
					* carList.get(position).qty);
			holder.tvNum.setText(carList.get(position).qty + "件");
			holder.tvDate.setText(carList.get(position).pickupDate);
			new ImageFetcher().fetch(
					HttpHelper.DOMAIN_URL_IMAGE2
							+ productList.get(position).imageUrl + "_L.jpg",
					holder.ivprotuct);
			new ImageFetcher().fetch(
					HttpHelper.DOMAIN_URL_IMAGE2
							+ colorList.get(position).colorImage + ".jpg",
					holder.ivcolor);
			holder.ibdelete.setOnClickListener(BuyAndInfoActivity.this);

			return view;
		}

		class ViewHolder {
			private ImageButton ibdelete;
			private ImageView ivprotuct, ivcolor;
			private TextView tvName, tvPrice, tvNum, tvDate;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buy_and_info, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Carposition = (Integer) (v.getTag());
		switch (v.getId()) {
		case R.id.ib_delete:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("X-Bionic").setMessage("从购物车删除此商品？");
			builder.setPositiveButton("确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					new DeleteProduct().execute(carList.get(Carposition).id);

				}
			}).setNegativeButton("取消", null).create().show();

			break;

		default:
			break;
		}

	}

	class DeleteProduct extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int position = params[0];
			String url = "shoppingcart/delete.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("token", MyApplication.token);
			map.put("userId", MyApplication.userId);
			map.put("cartId", position);
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
				ToastUtil.showToast(BuyAndInfoActivity.this, "删除成功");
				MyApplication.shopingCarProNum = MyApplication.shopingCarProNum - 1;
				DrawerActivity.btcount.setText(String
						.valueOf(MyApplication.shopingCarProNum));
				sum = sum
						- (productList.get(Carposition).price * carList
								.get(Carposition).qty);
				carList.remove(Carposition);
				tvAllMoney.setText("￥" + sum);
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
			}

			super.onPostExecute(result);
		}

	}

}
