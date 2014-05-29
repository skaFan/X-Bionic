package com.ska.x_bionic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ska.x_bionic.R;

public class WelcomFragment extends Fragment implements OnClickListener{
	private ImageView ivImage;
	private TextView btEnter;
	private int[] picture = new int[] { R.drawable.welcompage1,
			R.drawable.welcompage2, R.drawable.welcompage3 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.welcomefregment, null);
		ivImage = (ImageView) view.findViewById(R.id.iv_welcom);
		btEnter=(TextView)view.findViewById(R.id.bt_enterlogin);
		btEnter.setOnClickListener(this);
		Bundle b = getArguments();
		int position = b.getInt("position");
		setImage(position);
		return view;

	}

	private void setImage(int position) {

    	ivImage.setImageResource(picture[position]);
    	if(position!=picture.length-1){

    		btEnter.setVisibility(View.INVISIBLE);
    	}
    	
    	
//    	if(position!=picture.length-1){
//    		ivImage.setImageResource(picture[position]);
//    	}
//    	else{
//    		ivImage.setImageResource(picture[position]);
//    					new Handler().postDelayed(new Runnable() {
//    						@Override
//    						public void run() {
//    							
//    							Intent intent = new Intent(getActivity(),
//    									LoginActivity.class);
//    							startActivity(intent);
//    							getActivity().finish();
//    						}
//    					}, 1000);
//    		
//    	}
    	

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(),
				LoginActivity.class);
		startActivity(intent);
		getActivity().finish();
		
	}

}
