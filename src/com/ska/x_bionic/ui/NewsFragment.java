package com.ska.x_bionic.ui;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.ska.x_bionic.model.News;
import com.ska.x_bionic.util.JsonUtil;

public class NewsFragment extends Fragment {
	private List<News> newsList;
	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, null);
		mListView = (ListView) view.findViewById(R.id.lv_news);

		new GetNewMsg().execute();

		return view;
	}

	class GetNewMsg extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			String url = "news/list.do";
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, null);
			String jason = null;
			try {
				jason = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				int status = jsonEntity.getStatus();
				if (status == 200) {
					String data = jsonEntity.getData();
					newsList = JsonUtil.toObjectList(data, News.class);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mListView.setAdapter(new MyAdapter());
			super.onPostExecute(result);
		}

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return newsList.get(position);
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
						R.layout.view_news, null);
				holder.newsImage = (ImageView) view.findViewById(R.id.iv_news);
				holder.tvTitle = (TextView) view
						.findViewById(R.id.tv_newstitle);
				holder.tvUpdata = (TextView) view.findViewById(R.id.tv_updata);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.tvTitle.setText(newsList.get(position).title);
			holder.tvUpdata.setText(newsList.get(position).updateDate);
			new ImageFetcher().fetch(
					HttpHelper.DOMAIN_URL_IMAGE2
							+ newsList.get(position).imageUrl + "_S.jpg",
					holder.newsImage);

			return view;
		}

		class ViewHolder {
			private ImageView newsImage;
			private TextView tvTitle, tvUpdata;

		}

	}

}
