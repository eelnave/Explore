package edu.byui.cit.kindness;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public final class MainFragment extends InfoFragment {
	public static final String TAG = "Kindness";

	private InfoFragment about;
	private InfoFragment howto;

	SharedPreferences firstTime;
	SharedPreferences.Editor editor;
	String firstTimeKey = "FirstTimeCheck";


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		firstTime = getActivity().getPreferences(Context.MODE_PRIVATE);
		editor = firstTime.edit();
		firstTimeTest();

		View view = inflater.inflate(R.layout.main, container, false);
		Button seekindness = view.findViewById(R.id.see_kindness);
		Button reportkindness = view.findViewById(R.id.report_kindness);

		seekindness.setOnClickListener(new SeeListener());
		reportkindness.setOnClickListener(new ReportListener());

		return view;
	}

	private final class SeeListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			try {
				Intent goToMap = new Intent(getActivity(), KindnessMap.class);
				getActivity().startActivity(goToMap);

			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG,
						"cannot instantiate KindnessMap ActivityFragment", ex);
			}
		}
	}

	private final class ReportListener implements View.OnClickListener {
		InfoFragment fragment;
		@Override
		public void onClick(View view) {
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = CategoryFragment.class.newInstance();
					//this is a random ID I gave it. Why does it need an ID? Beats me.
					fragment.setDescripID(1010);
				}
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG,
						"cannot instantiate Categories fragment", ex);
			}
			switchFragment(fragment);
		}
	}

	public void switchFragment(InfoFragment fragment) {
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}

	public void firstTimeTest() {
		InfoFragment fragment = null;

		if (firstTime.contains(firstTimeKey)) {
			//if it is not the first time
		}
		// If first time
		else{

			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = CategoryFragment.class.newInstance();
					//this is a random ID I gave it. Why does it need an ID? Beats me.
					fragment.setDescripID(1010);
				}
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG,
						"cannot instantiate Categories fragment", ex);
			}
			switchFragment(fragment);

			saveFirstTimeTest();
		}
	}

	public void saveFirstTimeTest() {
		editor.putFloat(firstTimeKey, 1);
		editor.apply();
	}

}














