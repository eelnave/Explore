package edu.byui.cit.kindness;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CategoryFragment extends InfoFragment {

	public class categoryListener implements View.OnClickListener
	{
		private InfoFragment fragment;
		private int id;

		public categoryListener(int id) {

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
		Button emotion = view.findViewById(R.id.emotional);
		emotion.setOnClickListener(new categoryListener(R.id.emotional));
		Button food = view.findViewById(R.id.food);
		food.setOnClickListener(new categoryListener(R.id.food));
		Button labor = view.findViewById(R.id.labor);
		labor.setOnClickListener(new categoryListener(R.id.labor));
		Button travel = view.findViewById(R.id.travel);
		travel.setOnClickListener(new categoryListener(R.id.travel));
		Button money = view.findViewById(R.id.money);
		money.setOnClickListener(new categoryListener(R.id.money));
		Button other = view.findViewById(R.id.other);
		other.setOnClickListener(new categoryListener(R.id.other));

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
