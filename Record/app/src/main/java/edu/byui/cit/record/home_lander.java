package edu.byui.cit.record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.Goal;
import edu.byui.cit.model.GoalDAO;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.widget.WidgetWrapper;


public class home_lander extends CITFragment {
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstState){
		View view = inflater.inflate(R.layout.home_lander, container, false);
        // these wrappers, not tupac or biggie, link xml resources to listeners in java code
		new ButtonWrapper(view, R.id.newGoalButton, new newGoalClickHandler());
		new ButtonWrapper(view, R.id.deleteGoal, new showGoalsList());
		return view;
	}

	@Override
	protected String getTitle() {
		return "Record Home";
	}

	private final class newGoalClickHandler implements ClickListener{
		@Override
		public void clicked(WidgetWrapper source) {
			//calling parent method switchFragment
			((MainActivity) getActivity()).switchFragment(new add_newGoal(), new Bundle());
		}
	}

	private final class showGoalsList implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {

			try {
				//VERY IMPORTANT!!! First 2 lines of code needed to access dao functions 3rd line takes function and saves
				// local copy
				Context ctx = getActivity().getApplicationContext();
				GoalDAO dao = AppDatabase.getInstance(ctx).getGoalDAO();
				//this gets a list of goals from the database
				List<Goal> myGoals = dao.getAll();

				// adding hardcoded goals to app for testing purposes
				ArrayList<String> stringGoals = new ArrayList<>();
				stringGoals.add(myGoals.get(0).toString());
				stringGoals.add(myGoals.get(0).toString());

				//this object represents the listView on the screen
				ListView mainListView = getActivity().findViewById(R.id.goalList);
				ArrayAdapter<String> theAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.simplerow, stringGoals);
				mainListView.setAdapter(theAdapter);

			}
			catch (Exception ex) {
				Log.e("Record", ex.toString());
			}
		}
	}
}
