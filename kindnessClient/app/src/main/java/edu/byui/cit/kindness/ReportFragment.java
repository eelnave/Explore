package edu.byui.cit.kindness;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public class ReportFragment extends CITFragment {
	private static final int[] views = {
			R.id.gifts,
			R.id.service,
			R.id.time,
			R.id.touch,
			R.id.words
	};
	private static final int[] animations = {
			R.anim.gifts_animate,
			R.anim.service_animate,
			R.anim.time_animate,
			R.anim.touch_animate,
			R.anim.words_animate
	};
	private static final Category[] categories = {
			Category.Gifts,
			Category.Service,
			Category.Time,
			Category.Touch,
			Category.Words
	};


	private ReportedFragment fragReported;


	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.reportKindness);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.report_frag, container,
				false);

		Activity act = getActivity();
		for (int i = 0;  i < views.length;  ++i) {
			Button button = view.findViewById(views[i]);
			button.setOnClickListener(new CategoryListener(categories[i]));
			button.getBackground().setAlpha(100);
			Animation animation =
					AnimationUtils.loadAnimation(act, animations[i]);
			button.startAnimation(animation);
		}

//		map = view.findViewById(R.id.logo);
//		map.setOnClickListener(new SeeListener());
		return view;
	}


	private final class CategoryListener implements View.OnClickListener {
		private final Category category;

		CategoryListener(Category category) {
			this.category = category;
		}

		@Override
		public void onClick(View button) {
			try {
				Animation buttonAnimate = AnimationUtils.loadAnimation(
						getActivity(), R.anim.button_click);
				button.startAnimation(buttonAnimate);
				button.postDelayed(new Runnable() {
					@Override
					public void run() {
						GPSTracker gps = new GPSTracker(getSupportActivity());
						Location loc = gps.getLocation();
						if (loc == null) {
							throw new RuntimeException("cannot get location");
						}
						Report report = new Report(category, loc);
						report.submit();

						if (fragReported == null || fragReported.isDetached()) {
							fragReported = new ReportedFragment();
						}
						fragReported.setReport(report);
						AppCompatActivity act = getSupportActivity();
						FragmentManager mgr = act.getSupportFragmentManager();
						mgr.popBackStack();
						((KindnessActivity)act).switchFragment(fragReported);
					}
				}, buttonAnimate.getDuration());
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG, "cannot submit report", ex);
				getActivity().onBackPressed();
			}
		}
	}
}
