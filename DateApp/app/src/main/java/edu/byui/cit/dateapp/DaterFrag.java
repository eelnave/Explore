package edu.byui.cit.dateapp;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DaterFrag extends CITFragment {
	public static final String TAG = "DaterFrag";

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_menu, container, false);
		Button randBtn = view.findViewById(R.id.randomBtn);
		Button filterBtn = view.findViewById(R.id.filterBtn);
		Button addBtn = view.findViewById(R.id.addBtn);

		randBtn.setOnClickListener(new RandomListener());
		filterBtn.setOnClickListener(new FilterListener());
		addBtn.setOnClickListener(new AddListener());

		return view;
	}

	// This creates a listener for the add date button so it will switch the
	// fragment
	private final class AddListener implements View.OnClickListener {
		CITFragment fragment;

		@Override
		public void onClick(View view) {
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = new NewDate();
				}
			}
			catch (Exception ex) {
				// Log.e(KindnessActivity.TAG,
				//            "cannot instantiate Categories fragment", ex);
			}
			switchFragment(fragment);
		}
	}

	//This method gives functionality for the Filter Button
	private final class FilterListener implements View.OnClickListener {
		CITFragment fragment;

		@Override
		public void onClick(View view) {
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = new FilterList();
				}
			}
			catch (Exception ex) {
				// Log.e(KindnessActivity.TAG,
				//            "cannot instantiate Categories fragment", ex);
			}
			switchFragment(fragment);
		}
	}

	// This creates a listener for the random button so it will switch the
	// fragment
	private final class RandomListener implements View.OnClickListener {
		CITFragment fragment;

		@Override
		public void onClick(View view) {
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = new RandomResult();
				}
			}
			catch (Exception ex) {
				// Log.e(KindnessActivity.TAG,
				//            "cannot instantiate Categories fragment", ex);
			}
			switchFragment(fragment);
		}
	}

	// This is the function that switches between the the different fragments
	public void switchFragment(CITFragment fragment) {
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}
}
