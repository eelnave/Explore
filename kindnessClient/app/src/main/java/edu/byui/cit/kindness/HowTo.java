package edu.byui.cit.kindness;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public final class HowTo extends InfoFragment {
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.howto, container, false);

		Button toMain = view.findViewById(R.id.toMainMenu);
		toMain.setOnClickListener(new MainMenuListener());


		return view;
	}

	private final class MainMenuListener implements View.OnClickListener {
		InfoFragment fragment;
		@Override
		public void onClick(View view) {
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = MainFragment.class.newInstance();
					//this is a random ID I gave it. Why does it need an ID? Beats me.
					fragment.setDescripID(1019);
				}
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG,
						"cannot instantiate Main fragment", ex);
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


