package edu.byui.cit.kindness;

import android.app.Activity;
import android.content.Context;
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


public final class ReportFragment extends ChildFragment {
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
	private static final Report.Category[] categories = {
			Report.Category.Gifts,
			Report.Category.Service,
			Report.Category.Time,
			Report.Category.Touch,
			Report.Category.Words
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
			button.setOnClickListener(new CategoryClickHandler(categories[i]));
			button.getBackground().setAlpha(100);
			Animation anim = AnimationUtils.loadAnimation(act, animations[i]);
			button.startAnimation(anim);
		}

//		btnLogo = view.findViewById(R.id.logo);
//		btnLogo.setOnClickListener(new SeeListener());
		return view;
	}


	// Handles a click on one of the category buttons.
	private final class CategoryClickHandler
			implements View.OnClickListener, Runnable {
		private final Report.Category category;

		CategoryClickHandler(Report.Category category) {
			this.category = category;
		}

		@Override
		public void onClick(View button) {
			try {
				Animation buttonAnimate = AnimationUtils.loadAnimation(
						getActivity(), R.anim.button_click);
				button.startAnimation(buttonAnimate);
				button.postDelayed(this, buttonAnimate.getDuration());
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG, "cannot submit report", ex);
				getCompatActivity().onBackPressed();
			}
		}


		@Override
		public void run() {
			try {
				AppCompatActivity act = getCompatActivity();
				Context ctx = act.getApplicationContext();
				Location loc = LocationTracker.getInstance().getLocation(ctx);
				Report report = new Report(category, loc);
				report.submit();

				if (fragReported == null || fragReported.isDetached()) {
					fragReported = new ReportedFragment();
				}
				FragmentManager mgr = act.getSupportFragmentManager();
				mgr.popBackStack();
				((KindnessActivity)act).switchFragment(fragReported);
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG, "cannot submit report", ex);
				getCompatActivity().onBackPressed();
			}
		}
	}
}
