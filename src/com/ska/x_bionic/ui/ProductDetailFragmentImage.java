package com.ska.x_bionic.ui;

import com.ska.x_bionic.R;
import com.ska.x_bionic.application.MyApplication;
import com.ska.x_bionic.image.ImageFetcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ProductDetailFragmentImage extends Fragment{
	private ImageView imageview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_productdetail_image, null);
		imageview = (ImageView)view.findViewById(R.id.iv_productdetail);
		Bundle b=getArguments();
		int position = b.getInt("position");
		String absUrl = MyApplication.getPictureUrl(position);
		
		new ImageFetcher().fetch(absUrl, imageview);
		
		
		return view;
	}

}
