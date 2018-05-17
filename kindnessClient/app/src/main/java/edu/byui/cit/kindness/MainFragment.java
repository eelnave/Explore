package edu.byui.cit.kindness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public final class MainFragment extends CITFragment {
	private Button btnSee;
	private Button btnReport;


	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.appName);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment, container, false);

		btnSee = view.findViewById(R.id.see_kindness);
		btnReport = view.findViewById(R.id.report_kindness);
		btnSee.setOnClickListener(new SeeListener());
		btnReport.setOnClickListener(new ReportListener());

		return view;
	}

	private final class SeeListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			try {
				final Intent goToMap = new Intent(getActivity(),
						MapActivity.class);
				Animation buttonAnimate = AnimationUtils.loadAnimation(
						getActivity(), R.anim.icon_zoom_in);
				btnSee.bringToFront();
				btnSee.startAnimation(buttonAnimate);
				view.postDelayed(new Runnable() {
					@Override
					public void run() {
						getActivity().startActivity(goToMap);
					}
				}, buttonAnimate.getDuration() - 250);
			}
			catch (Exception ex) {
				Log.e(MainActivity.TAG,
						"cannot instantiate MapActivity fragment", ex);
			}
		}
	}


	private final class ReportListener implements View.OnClickListener {
		CITFragment fragment;

		@Override
		public void onClick(View view) {
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = new ReportFragment();
				}
				Animation buttonAnimate = AnimationUtils.loadAnimation(
						getActivity(), R.anim.button_click);
				btnReport.startAnimation(buttonAnimate);
				view.postDelayed(new Runnable() {
					@Override
					public void run() {
						((MainActivity)getActivity()).switchFragment(fragment);
					}
				}, buttonAnimate.getDuration());
			}
			catch (Exception ex) {
				Log.e(MainActivity.TAG,
						"cannot instantiate Categories fragment", ex);
			}
		}
	}
}
