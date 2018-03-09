package edu.byui.cit.calc360;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.byui.cit.calc360.InfoFragment;
import edu.byui.cit.calc360.R;


/**
 * Created by ibdch on 3/9/2018.
 */

public class Feedback extends InfoFragment {
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about, container, false);
		String feedBack = getString(R.string.feedMessage)+ " " +(R.string.feedLink);
		TextView version = view.findViewById(R.id.txtVersion);
		version.setText(feedBack);
		return view;
	}
}
