package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.byui.cit.widget.CITFragment;


public class OilFrag extends CITFragment {

	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.oil_frag, container, false);
		return view;
	}

	@Override
	protected String getTitle() {
		return null;
	}
}