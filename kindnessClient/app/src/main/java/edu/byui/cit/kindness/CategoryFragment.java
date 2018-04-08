package edu.byui.cit.kindness;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class CategoryFragment extends InfoFragment {
	private Button map;

	public class categoryListener implements View.OnClickListener
	{
		private InfoFragment fragment;
		private int id;

		private categoryListener(int id) {

			this.id = id;
		}

		@Override
		public void onClick(View v)
		{

			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = SubmitFragment.class.newInstance();
					Bundle args = new Bundle();
					args.putInt("id",id);
					fragment.setArguments(args);
				}
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG,
						"cannot instantiate submit fragment", ex);
			}
			Animation buttonAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.button_click);
			v.startAnimation(buttonAnimate);
			v.postDelayed(new Runnable() {
				@Override
				public void run() {
					switchFragment(fragment);
				}
			}, buttonAnimate.getDuration());
		}
	}

	private final class SeeListener implements View.OnClickListener {
		@Override
		public void onClick(final View view) {
			try {
				Animation logoAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.icon_zoom_in);
				map.bringToFront();
				map.startAnimation(logoAnimate);
				final Intent goToMap = new Intent(getActivity(), KindnessMap.class);
				view.postDelayed(new Runnable() {
					@Override
					public void run() {
						getActivity().startActivity(goToMap);
					}
				}, logoAnimate.getDuration()-250);

			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG,
						"cannot instantiate KindnessMap ActivityFragment", ex);
			}
		}
	}

	public CategoryFragment() {
		// Required empty public constructor
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.categories, container,
				false);

		//Save buttons from view, add event listeners
			map = view.findViewById(R.id.logo);
			map.setOnClickListener(new SeeListener());
		Button service = view.findViewById(R.id.service);
			service.setOnClickListener(new categoryListener(R.id.service));
		Button time = view.findViewById(R.id.time);
			time.setOnClickListener(new categoryListener(R.id.time));
		Button touch = view.findViewById(R.id.touch);
			touch.setOnClickListener(new categoryListener(R.id.touch));
		Button words = view.findViewById(R.id.words);
			words.setOnClickListener(new categoryListener(R.id.words));
		Button gift = view.findViewById(R.id.gift);
			gift.setOnClickListener(new categoryListener(R.id.gift));

		Animation timeAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.time_animate);
		Animation giftAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.gift_animate);
		Animation serviceAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.service_animate);
		Animation wordsAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.words_animate);
		Animation touchAnimate = AnimationUtils.loadAnimation(getActivity(),R.anim.touch_animate);

		time.startAnimation(timeAnimate);
		gift.startAnimation(giftAnimate);
		service.startAnimation(serviceAnimate);
		touch.startAnimation(touchAnimate);
		words.startAnimation(wordsAnimate);

		return view;
	}

	public void switchFragment(InfoFragment fragment) {
		System.out.println(fragment.toString());
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}
}
