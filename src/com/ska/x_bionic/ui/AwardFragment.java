package com.ska.x_bionic.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ska.x_bionic.R;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.image.ImageFetcher;
import com.ska.x_bionic.model.Award;
import com.ska.x_bionic.util.JsonUtil;

public class AwardFragment extends Fragment {
	private List<Award> awardList;
	private ListView mListview;
	private int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_award, null);
		Bundle b = getArguments();
		id = b.getInt("id");
		mListview = (ListView) view.findViewById(R.id.lv_award);
		new GetAward().execute(id);

		return view;
	}

	class GetAward extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int id = params[0];
			String url = "honor/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", id);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String json = "";
			try {
				json = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(json);
				if (jsonEntity.getStatus() == 200) {
					String data = jsonEntity.getData();
					awardList = JsonUtil.toObjectList(data, Award.class);
					Log.i("awardList", awardList.size()+"");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mListview.setAdapter(new MyAdapter());
			super.onPostExecute(result);
		}

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return awardList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return awardList.get(position);
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
				view = getActivity().getLayoutInflater().inflate(
						R.layout.view_award, null);
				holder.mImageview = (ImageView) view
						.findViewById(R.id.iv_award);
				holder.tvhonor = (TextView) view.findViewById(R.id.tv_award);
				holder.tvdescription = (TextView) view
						.findViewById(R.id.tv_desc);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.tvhonor.setText(awardList.get(position).title);
			holder.tvdescription.setText(awardList.get(position).body);
			new ImageFetcher().fetch(
					HttpHelper.DOMAIN_URL_IMAGE2
							+ awardList.get(position).imageUrl + "_S.jpg",
					holder.mImageview);

			return view;
		}

		class ViewHolder {
			private ImageView mImageview;
			private TextView tvhonor, tvdescription;
		}
	}

}
