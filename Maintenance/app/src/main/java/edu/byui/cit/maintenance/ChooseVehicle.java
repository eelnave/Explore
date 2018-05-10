package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ChooseVehicle extends CITFragment {
	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {
		View view = inflater.inflate(R.layout.choose_vehicle, container,
				false);

		Button btnAdd = view.findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new AddHandler());
		return view;
	}

	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.chooseVehicle);
	}


	private final class AddHandler implements View.OnClickListener {
		private CITFragment fragment;

		@Override
		public void onClick(View v) {
			if (fragment == null || fragment.isDetached()) {
				fragment = new AddVehicle();
			}
			((MainActivity)getActivity()).switchFragment(fragment);
		}
	}
}
