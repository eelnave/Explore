package edu.byui.cit.kindness;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public final class MainFragment extends InfoFragment {
	public static final String TAG = "Kindness";
	private InfoFragment about;
	private Button seekindness;
	private Button reportkindness;

	SharedPreferences firstTime;
	SharedPreferences.Editor editor;
	String firstTimeKey = "FirstTimeCheck";


	@SuppressLint("CommitPrefEdits")
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		firstTime = getActivity().getPreferences(Context.MODE_PRIVATE);
		editor = firstTime.edit();
		firstTimeTest();

		View view = inflater.inflate(R.layout.main, container, false);
		seekindness = view.findViewById(R.id.see_kindness);
		reportkindness = view.findViewById(R.id.report_kindness);
		seekindness.setOnClickListener(new SeeListener());
		reportkindness.setOnClickListener(new ReportListener());



		return view;
	}

	private final class SeeListener implements View.OnClickListener {
//		InfoFragment fragment;
//		FragmentActivity mapFragment;
		@Override
		public void onClick(View view) {
			try {
				final Intent goToMap = new Intent(getActivity(), KindnessMap.class);
				Animation buttonAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.icon_zoom_in);
				seekindness.bringToFront();
				seekindness.startAnimation(buttonAnimate);
				view.postDelayed(new Runnable() {
					@Override
					public void run() {
						getActivity().startActivity(goToMap);
					}
				}, buttonAnimate.getDuration()-250);
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG,
						"cannot instantiate KindnessMap fragment", ex);
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
			Animation buttonAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.button_click);
			reportkindness.startAnimation(buttonAnimate);
			view.postDelayed(new Runnable() {
				@Override
				public void run() {
					switchFragment(fragment);
				}
			}, buttonAnimate.getDuration());
		}
	}

	public void switchFragment(InfoFragment fragment) {
		System.out.println(fragment.toString());
		// Replace whatever is in the fragment_container view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		//this id will be whatever it is in the XML
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}

	public void firstTimeTest() {
		InfoFragment fragment = null;

		if (!firstTime.contains(firstTimeKey)) {
			//if first time
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = HowTo.class.newInstance();
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

			//clearFirstTime() //used to reset first time file for testing
		}
	}

	public void saveFirstTimeTest() {
		editor.putFloat(firstTimeKey, 1);
		editor.apply();
	}

	public void clearFirstTime() {
		editor.remove(firstTimeKey);
		editor.apply();
	}

}














