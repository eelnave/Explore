package edu.byui.cit.kindness;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;


public final class MainFragment extends InfoFragment {
	public static final String TAG = "Kindness";

	private KindnessMap kindMap;
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

		@Override
		public void onClick(View view) {

		}
	}

	private final class ReportListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {

		}
	}
}




