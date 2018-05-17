package edu.byui.cit.kindness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public class ReportFragment extends CITFragment {
	private Button map;


	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.reportKindness);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.report_fragment, container,
				false);

		final int[] views = {
				R.id.service,
				R.id.time,
				R.id.words,
				R.id.touch,
				R.id.gifts
		};
		final int[] animations = {
				R.anim.service_animate,
				R.anim.time_animate,
				R.anim.words_animate,
				R.anim.touch_animate,
				R.anim.gifts_animate
		};
		Activity act = getActivity();
		for (int i = 0;  i < views.length;  ++i) {
			Button button = view.findViewById(views[i]);
			button.setOnClickListener(new CategoryListener());
			button.getBackground().setAlpha(100);
			Animation animation =
					AnimationUtils.loadAnimation(act, animations[i]);
			button.startAnimation(animation);
		}

		map = view.findViewById(R.id.logo);
		map.setOnClickListener(new SeeListener());
		return view;
	}


	private final class CategoryListener implements View.OnClickListener {
		private CITFragment fragment;

		@Override
		public void onClick(View button) {
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = new SubmitFragment();
				}
			}
			catch (Exception ex) {
				Log.e(MainActivity.TAG,
						"cannot instantiate submit fragment", ex);
			}
			Animation buttonAnimate = AnimationUtils.loadAnimation(
					getActivity(), R.anim.button_click);
			button.startAnimation(buttonAnimate);
			button.postDelayed(new Runnable() {
				@Override
				public void run() {
					((MainActivity)getActivity()).switchFragment(fragment);
				}
			}, buttonAnimate.getDuration());
		}
	}


	private final class SeeListener implements View.OnClickListener {
		@Override
		public void onClick(final View button) {
			try {
				Animation logoAnimate = AnimationUtils.loadAnimation(
						getActivity(), R.anim.icon_zoom_in);
				map.bringToFront();
				map.startAnimation(logoAnimate);
				final Intent goToMap = new Intent(getActivity(),
						MapActivity.class);
				button.postDelayed(new Runnable() {
					@Override
					public void run() {
						getActivity().startActivity(goToMap);
					}
				}, logoAnimate.getDuration() - 250);

			}
			catch (Exception ex) {
				Log.e(MainActivity.TAG,
						"cannot instantiate MapActivity ActivityFragment", ex);
			}
		}
	}
}
