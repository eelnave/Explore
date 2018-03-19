package edu.byui.cit.kindness;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class CategoryFragment extends InfoFragment {

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
					Bundle args =new Bundle();
					args.putInt("id",id);
					fragment.setArguments(args);
					//this is a random ID I gave it. Why does it need an ID? Beats me.
					fragment.setDescripID(1011);
				}
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG,
						"cannot instantiate submit fragment", ex);
			}
			switchFragment(fragment);
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
		// Replace whatever is in the fragment_container view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		//this id will be whatever it is in the XML
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}
}
