package com.ska.x_bionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ska.x_bionic.R;

public class ShareFragment extends Fragment implements OnCheckedChangeListener {
	private int id;
	private List<Fragment> fList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_share, null);
		listfragment();
		Bundle bundle = getArguments();
		id = bundle.getInt("id");
		RadioGroup mGroup = (RadioGroup) view.findViewById(R.id.rg_share);
		mGroup.setOnCheckedChangeListener(this);
		RadioButton Usercomment = (RadioButton) view
				.findViewById(R.id.rb_usercomment);
		Usercomment.setChecked(true);
		Fragment fragment = fList.get(0);
		Bundle b = new Bundle();
		b.putInt("id", id);
		fragment.setArguments(b);
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.fl_share, fragment).commit();
		return view;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Fragment fragment = null;
		if (checkedId == R.id.rb_usercomment) {
			fragment = fList.get(0);
		} else if (checkedId == R.id.rb_news) {
			fragment = fList.get(1);
		} else if (checkedId == R.id.rb_award) {
			fragment = fList.get(2);
		}
		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		fragment.setArguments(bundle);
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.fl_share, fragment).commit();

	}

	// @Override
	// public void onCheckedChanged(RadioGroup group, int checkedId) {
	// switch (checkedId) {
	// case R.id.rb_usercomment:
	// UsercommentFragment fragment = new UsercommentFragment();
	// Bundle b = new Bundle();
	// b.putInt("id", id);
	// fragment.setArguments(b);
	// getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_share,
	// fragment).commit();
	// break;
	// case R.id.rb_news:
	// NewsFragment fragment1 = new NewsFragment();
	// Bundle b1 = new Bundle();
	// b1.putInt("id", id);
	// fragment1.setArguments(b1);
	// getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_share,
	// fragment1).commit();
	// case R.id.rb_award:
	// AwardFragment fragment2 = new AwardFragment();
	// Bundle b2 = new Bundle();
	// b2.putInt("productId", id);
	// fragment2.setArguments(b2);
	// getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_share,
	// fragment2).commit();
	//
	// default:
	// break;
	// }
	//
	// }

	private void listfragment() {
		fList = new ArrayList<Fragment>();
		fList.add(new UsercommentFragment());
		fList.add(new NewsFragment());
		fList.add(new AwardFragment());
	}

}
