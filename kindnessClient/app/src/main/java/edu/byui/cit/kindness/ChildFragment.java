package edu.byui.cit.kindness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/** The parent class for all fragments that are "children" of the
 * DisplayFragment, or in other words come after the DisplayFragment. */
public abstract class ChildFragment extends CITFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		AppCompatActivity act = getCompatActivity();
		act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		act.findViewById(R.id.fabAdd).setVisibility(View.GONE);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		AppCompatActivity act = getCompatActivity();
		act.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		act.findViewById(R.id.fabAdd).setVisibility(View.VISIBLE);

		//known issue: when you open a fragment (ReportedFragment, HowTo, Privacy)
		//and return to DisplayFragment, the reports are no longer on the map.
		// TODO: need to get the reports back on the map after accessing a fragment
	}
}
