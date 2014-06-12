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
import android.widget.ListView;
import android.widget.TextView;

import com.ska.x_bionic.R;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.model.UserComment;
import com.ska.x_bionic.util.JsonUtil;

public class UsercommentFragment extends Fragment {
	private List<UserComment> commentList;
	private ListView mListview;
	private int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_usercomment, null);
		mListview = (ListView) view.findViewById(R.id.lv_usercomment);
		Bundle b = getArguments();
		id = b.getInt("id");
		new GetUserComment().execute(id);
		return view;
	}

	class GetUserComment extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			String url = "product/comments/list.do";
			int id = params[0];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jason = "";
			try {
				jason = HttpHelper.execute(entity);
				ResponseJsonEntity jsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				int status = jsonEntity.getStatus();
				Log.i("fhb", status + "");
				if (status == 200) {
					String data = jsonEntity.getData();
					commentList = JsonUtil
							.toObjectList(data, UserComment.class);
					Log.i("commentList", commentList.size() + "");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// TODO Auto-generated method stub
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

			return commentList.size();
		}

		@Override
		public Object getItem(int position) {

			return commentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			view = getActivity().getLayoutInflater().inflate(
					R.layout.view_comment, null);
			TextView name = (TextView) view.findViewById(R.id.tv_name);
			TextView comment = (TextView) view.findViewById(R.id.tv_comment);
			name.setText(commentList.get(position).displayName + "  "
					+ commentList.get(position).firstName + "  :");
			comment.setText(commentList.get(position).comment);
			return view;
		}

	}

}
