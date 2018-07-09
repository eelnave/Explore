package edu.byui.cit.kindness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.widget.CITFragment;


public final class HowToFragment extends ChildFragment {
	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.howTo);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.how_to_frag, container, false);

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
