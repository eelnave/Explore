package edu.byui.cit.kindness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class ChildFragment extends CITFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		AppCompatActivity act = getCompatActivity();
		act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		act.findViewById(R.id.fabAdd).setVisibility(View.GONE);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	//instead of destroy, should we use stop and then resume previous view?
	@Override
	public void onDestroyView(){
		super.onDestroyView();

		AppCompatActivity act = getCompatActivity();
		act.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		act.findViewById(R.id.fabAdd).setVisibility(View.VISIBLE);
		//need to get the reports back on the map after accessing a fragment

	}
}
