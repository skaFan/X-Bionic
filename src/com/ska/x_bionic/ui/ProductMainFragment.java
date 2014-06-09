package com.ska.x_bionic.ui;

import java.util.ArrayList;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ska.x_bionic.R;
import com.ska.x_bionic.http.HttpHelper;
import com.ska.x_bionic.http.HttpMethod;
import com.ska.x_bionic.http.RequestEntity;
import com.ska.x_bionic.http.ResponseJsonEntity;
import com.ska.x_bionic.image.ImageFetcher;
import com.ska.x_bionic.model.ChildProductList;
import com.ska.x_bionic.util.JsonUtil;

public class ProductMainFragment extends Fragment {
	private ExpandableListView mExpandableListView;
	private List<Integer> groupList;
	private List<ChildProductList> list;
//	private List<ChildProductList> list2;

	private List<List<ChildProductList>> childList;
	private ProgressDialog pd;
	private int groupId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragement_productmain, null);
		mExpandableListView = (ExpandableListView) view
				.findViewById(R.id.ep_product);
		groupList = new ArrayList<Integer>();
		childList = new ArrayList<List<ChildProductList>>();
		groupList.add(R.drawable.upbackground);
		groupList.add(R.drawable.downbackground);

		new getProductList().execute(100001);
		new getProductList().execute(100002);
		
		mExpandableListView.setAdapter(new mExpandableListViewAdapt());
		mExpandableListView.setOnGroupClickListener(onGroupClickListener);

		return view;
	}
	
	private OnGroupClickListener onGroupClickListener = new OnGroupClickListener() {
		
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			groupId = groupPosition;
			for (int i = 0; i < groupList.size(); i++) {
				System.out.println("i:" + i);
				if (i == groupPosition) {
					if (mExpandableListView.isGroupExpanded(groupPosition)) {
						mExpandableListView.collapseGroup(groupPosition);
					}else {
						mExpandableListView.expandGroup(groupPosition);
					}
				} else {
					mExpandableListView.collapseGroup(i);
				}

			}
			return true;
		}
	};

	class getProductList extends AsyncTask<Integer, Void, Void> {

		private int productListid;

		// @Override
		// protected void onPreExecute() {
		// pd = ProgressDialog
		// .show(getActivity(), "Loading", "wait a monment");
		//
		// super.onPreExecute();
		// }

		@Override
		protected Void doInBackground(Integer... params) {
			productListid = params[0];
			String url = "category/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("navId", productListid);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jason = "";

			try {
				jason = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(jason);
				int status = responseJsonEntity.getStatus();
				if (status == 200) {
					String data = responseJsonEntity.getData();
					if (productListid == 100001) {
						list = JsonUtil.toObjectList(data,
								ChildProductList.class);
						childList.add(list);
					}
					if (productListid == 100002) {
						list = JsonUtil.toObjectList(data,
								ChildProductList.class);
						childList.add(list);

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
			// pd.dismiss();
			super.onPostExecute(result);
		}

	}

	
	
	
	
	
	class mExpandableListViewAdapt extends BaseExpandableListAdapter {

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return groupList.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childList.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {

			return childPosition;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupList.size();
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = convertView;
			GroupViewHolder gvh = null;
			if (view == null) {
				view = getActivity().getLayoutInflater().inflate(
						R.layout.view_expendview_group, null);
				gvh = new GroupViewHolder();
				gvh.mImageView = (ImageView) view
						.findViewById(R.id.iv_ep_group);
				view.setTag(gvh);
			} else {
				gvh = (GroupViewHolder) view.getTag();
			}
			gvh.mImageView.setImageResource(groupList.get(groupPosition));

			return view;
		}

		class GroupViewHolder {
			ImageView mImageView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = convertView;
			 ChildViewHolder cvh = null;
			 if (view == null) {
			view = getActivity().getLayoutInflater().inflate(
					R.layout.view_expendview_child, null);
			 cvh = new ChildViewHolder();
			cvh.mGridView  = (GridView) view.findViewById(R.id.gv_product);
			 view.setTag(cvh);
			 } else {
			 cvh = (ChildViewHolder) view.getTag();
			 }
			 cvh.mGridView.setAdapter(new ChildViewAdapt(groupPosition));
			 cvh.mGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
				int	ProductId= childList.get(groupId).get(arg2).getId();
				Log.i("IDDD", ProductId+"");
					Intent intent = new Intent(getActivity(),ProductListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("ProductId", ProductId);
					int listid=100001+groupId;
					bundle.putInt("ListId",listid);
					Log.i("ididididid", listid+"");
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
			return view;
		}

		class ChildViewHolder {
			GridView mGridView;
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		class ChildViewAdapt extends BaseAdapter {
			private int groupPosition;
			 

			ChildViewAdapt(int groupPosition) {
				this.groupPosition = groupPosition;
			}

			@Override
			public int getCount() {

				return childList.get(groupPosition).size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return childList.get(groupPosition).get(position);
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				
				GvViewHolder gvv = null;
				
				if (view == null) {
					view = getActivity().getLayoutInflater().inflate(
							R.layout.view_expendview_child_item, null);
					gvv = new GvViewHolder();
					gvv.mImageView = (ImageView) view
							.findViewById(R.id.iv_child_item);
					gvv.mTextView = (TextView) view
							.findViewById(R.id.tv_child_item);
					view.setTag(gvv);
				} else {
					gvv = (GvViewHolder) view.getTag();
				}

				ImageFetcher image = new ImageFetcher();
				image.fetch(
						HttpHelper.DOMAIN_URL_IMAGE
								+ childList.get(groupPosition).get(position)
										.getImageUrl() + "_L.png",
						gvv.mImageView);
				
				
				gvv.mTextView.setText(childList.get(groupPosition)
						.get(position).getName());
				

				return view;
			}

			class GvViewHolder {
				TextView mTextView;
				ImageView mImageView;

			}

		}

	}

}
