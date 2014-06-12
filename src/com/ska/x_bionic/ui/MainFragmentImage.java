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

public class MainFragmentImage extends Fragment implements OnClickListener {
	private int position;
	private int[] picture = new int[] { R.drawable.xactivtypage,
			R.drawable.xactivtypage2, R.drawable.xactivtypage3,
			R.drawable.xactivtypage4 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_image, null);
		ImageView imageview = (ImageView) view.findViewById(R.id.iv_main);
		Bundle bundle = getArguments();
		position = bundle.getInt("position");
		imageview.setImageResource(picture[position]);
		imageview.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View arg0) {
		switch (position) {

		case 0:
			// X的活动
			break;
		case 1:
			Intent intent = new Intent(getActivity(),IntroductionActivity.class);
            startActivity(intent);
			break;
		case 2:
			Intent intent1 = new Intent(getActivity(), ProductMainActivity.class);
			startActivity(intent1);
			break;
		case 3:
			// 达人故事
			break;
		default:
			break;
		}

	}

}
