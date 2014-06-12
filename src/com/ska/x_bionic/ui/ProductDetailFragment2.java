package com.ska.x_bionic.ui;

import com.ska.x_bionic.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ProductDetailFragment2 extends Fragment implements
		OnCheckedChangeListener {
	private int id, colorId, sizeId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_productdetail2, null);
		RadioGroup mGroup = (RadioGroup) view.findViewById(R.id.rg_fun);
		mGroup.setOnCheckedChangeListener(this);
		RadioButton rbShare = (RadioButton) view.findViewById(R.id.rb_share);
		Bundle b = getArguments();
		id = b.getInt("id");
		colorId = b.getInt("colorId");
		sizeId = b.getInt("sizeId");
		ShareFragment sf = new ShareFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		sf.setArguments(bundle);
		getActivity().getSupportFragmentManager().beginTransaction()
				.add(R.id.fl_main_fragment, sf).commit();
		rbShare.setChecked(true);
		return view;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_share:
			ShareFragment sf = new ShareFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("id", id);
			sf.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_main_fragment, sf).commit();
			break;
		case R.id.rb_buy_car:
			Intent intent = new Intent(getActivity(),BuyAndInfoActivity.class);
			startActivity(intent);

		default:
			break;
		}

	}

}
