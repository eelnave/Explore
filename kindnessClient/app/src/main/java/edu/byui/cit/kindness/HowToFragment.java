package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public final class HowToFragment extends ChildFragment {
	@Override
	protected String getTitle() {
		return getString(R.string.howTo);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.how_to_frag, container, false);
	}
}
