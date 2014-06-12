package com.ska.x_bionic.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ska.x_bionic.R;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.image.ImageFetcher;
import com.ska.x_bionic.model.DetailProductInformation;
import com.ska.x_bionic.util.ConnectivityUtil;
import com.ska.x_bionic.util.JsonUtil;
import com.ska.x_bionic.util.ToastUtil;

public class ProductListFragment extends Fragment{
	private List<DetailProductInformation> list;
	private GridView mGridView;
	private ProgressDialog pd;
	private int snid, nid, id,desc;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_detale_productlist, null);
		mGridView = (GridView) view.findViewById(R.id.detail_product_list);
		Bundle b = getArguments();
		snid = b.getInt("subNavId");
		nid = b.getInt("navId");
		//id = b.getInt("id");
		desc=b.getInt("desc");
		Log.i("desc", desc+"");
		if (!ConnectivityUtil.isOnline(getActivity())) {
			ToastUtil.showToast(getActivity(), "没有网络连接");
		} else {
			//new getInformation().execute(nid, snid, id,desc);
			new getInformation().execute(nid, snid,desc);
		}

		return view;
	}

	class getInformation extends AsyncTask<Integer, Void, Void> {

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(getActivity(), "请稍后", "数据加载中");
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Integer... arg0) {
			int navId = arg0[0];
			int subNavId = arg0[1];
			//int cid = arg0[2];
			int desc = arg0[2];
			String url = "category/products.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("navId", navId);
			map.put("subNavId", subNavId);
			//map.put("id", cid);
			map.put("orderBy", "price");
			map.put("desc", desc);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String json = "";
			try {
				json = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(json);
				if (responseJsonEntity.getStatus() == 200) {
					String data = responseJsonEntity.getData();
					list = JsonUtil.toObjectList(data,
							DetailProductInformation.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			mGridView.setAdapter(new Myadapt());
			mGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					int pid = list.get(arg2).id;
					String name = list.get(arg2).name;
					Intent intent = new Intent(getActivity(),ProductDetailActivity.class);
					Bundle b = new Bundle();
					b.putInt("pid", pid);
					b.putString("name", name);
					intent.putExtras(b);
					startActivity(intent);
				}
			});

			super.onPostExecute(result);
		}

	}

	class Myadapt extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = arg1;
			viewHolder holder = null;
			if (view == null) {
				holder = new viewHolder();
				view = getActivity().getLayoutInflater().inflate(
						R.layout.view_secondproduct, null);
				holder.ivImage=(ImageView)view.findViewById(R.id.iv_2protuct);
				holder.tvName=(TextView)view.findViewById(R.id.tv_2name);
				holder.tvPrice=(TextView)view.findViewById(R.id.tv_2price);
				view.setTag(holder);
			}else{
				holder=(viewHolder)view.getTag();
			}

			new ImageFetcher().fetch(
					HttpHelper.DOMAIN_URL_IMAGE2
							+ list.get(arg0).imageUrl + "_L.jpg",
							holder.ivImage);
			holder.tvName.setText(list.get(arg0).name);
			holder.tvPrice.setText("￥"+String.valueOf(list.get(arg0).price));

			
			return view;
		}

		class viewHolder {
			private ImageView ivImage;
			private TextView tvName;
			private TextView tvPrice;
		}

	}

}
