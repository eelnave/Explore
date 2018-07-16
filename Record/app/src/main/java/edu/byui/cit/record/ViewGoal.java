package edu.byui.cit.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.byui.cit.widget.CITFragment;




public class ViewGoal extends CITFragment {
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstState) {
		View view = inflater.inflate(R.layout.frag_view_goal, container, false);
		String myGoal = this.getArguments().getString("goalTitle");

		TextView goalTitle = view.findViewById(R.id.goalTitle);
		goalTitle.setText(myGoal);




		return view;
	}

	@Override
	protected String getTitle() {
		return "My Goals";
	}

}
