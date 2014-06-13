package com.ska.x_bionic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ska.x_bionic.R;

public class IntroductionFragment extends Fragment implements OnClickListener {
	private ImageView ivaward;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction, null);
		ivaward=(ImageView)view.findViewById(R.id.iv_aware);
		ivaward.setOnClickListener(this);
		
		return view;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(),AwardActivity.class);
		startActivity(intent);
		
	}

}
