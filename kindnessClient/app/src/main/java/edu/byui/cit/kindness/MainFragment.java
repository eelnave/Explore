package edu.byui.cit.kindness;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public final class MainFragment extends InfoFragment {
	public static final String TAG = "Kindness";
	private InfoFragment about;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main, container, false);
		Button seekindness = view.findViewById(R.id.see_kindness);
		Button reportkindness = view.findViewById(R.id.report_kindness);

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
				Intent goToMap = new Intent(getActivity(), KindnessMap.class);
				getActivity().startActivity(goToMap);
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
			switchFragment(fragment);
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
}














