package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public final class About extends CITFragment {
	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.about);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about, container, false);
		String nameAndVersion = getString(R.string.appName)
				+ " " + getString(R.string.versionName);
		TextView version = view.findViewById(R.id.txtVersion);
		version.setText(nameAndVersion);
		return view;
	}
}
