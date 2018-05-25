package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ReportedFragment extends CITFragment {
	//private TextView txtCategory, txtLatitude, txtLongitude;
	//private Report report;


	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.reportKindness);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.reported_frag, container,
				false);

		//txtCategory = view.findViewById(R.id.category);
		//txtLatitude = view.findViewById(R.id.latitude);
		//txtLongitude = view.findViewById(R.id.longitude);

		view.findViewById(R.id.btnToMap).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().onBackPressed();
					}
				});

		return view;
	}


	public void setReport(Report report) {
		//this.report = report;
	}

	@Override
	public void onStart() {
		super.onStart();
		//txtCategory.setText(report.category().toString());
		//txtLatitude.setText(Double.toString(report.getLatitude()));
		//txtLongitude.setText(Double.toString(report.getLongitude()));
	}
}
