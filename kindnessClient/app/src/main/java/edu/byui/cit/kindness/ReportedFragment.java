package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ReportedFragment extends ChildFragment {

	@Override
	protected String getTitle() {
		return getString(R.string.reportKindness);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.reported_frag, container,
				false);

		view.findViewById(R.id.btnToMap).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().onBackPressed();
					}
				});

		return view;
	}
}
