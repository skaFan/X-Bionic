package com.ska.x_bionic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ska.x_bionic.R;

public class MainFragment extends Fragment implements OnClickListener {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
		TextView tvBuy = (TextView) view.findViewById(R.id.tv_buy);
		TextView tvStore = (TextView) view.findViewById(R.id.tv_story);
		TextView tvXactivity = (TextView) view.findViewById(R.id.tv_x_activity);
		TextView tvXdsecrip = (TextView) view
				.findViewById(R.id.tv_x_description);
		tvBuy.setOnClickListener(this);
		tvStore.setOnClickListener(this);
		tvXactivity.setOnClickListener(this);
		tvXdsecrip.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_buy:
			Intent intent = new Intent(getActivity(), ProductMainActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_story:
			//
			break;
		case R.id.tv_x_activity:
			//
			break;
		case R.id.tv_x_description:
			//
			break;
		default:
			break;
		}

	}

}
