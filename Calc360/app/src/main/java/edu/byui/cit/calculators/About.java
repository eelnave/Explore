package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.byui.cit.calc360.OmniFragment;
import edu.byui.cit.calc360.R;


public final class About extends OmniFragment {
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about, container, false);
		String nameAndVersion = getString(R.string.appName) + " " + getString(R.string.versionName);
		TextView version = view.findViewById(R.id.txtVersion);
		version.setText(nameAndVersion);
		return view;
	}
}
