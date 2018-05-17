package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public final class HowTo extends CITFragment {
	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.howTo);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.how_to, container, false);
		Button toMain = view.findViewById(R.id.toMainMenu);
		toMain.setOnClickListener(new MainMenuHandler());
		return view;
	}


	private final class MainMenuHandler implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			getActivity().onBackPressed();
		}
	}
}
