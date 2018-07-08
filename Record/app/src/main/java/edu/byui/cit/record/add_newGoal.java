package edu.byui.cit.record;

import java.util.Calendar;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.Goal;
import edu.byui.cit.model.GoalDAO;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.DateWrapper;
import edu.byui.cit.widget.EditString;
import edu.byui.cit.widget.SpinString;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.widget.WidgetWrapper;


public class add_newGoal extends CITFragment {
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.frag_add_newgoal, container, false);


		//THIS IS OLD CODE THAT APPLIED THE ADAPTER TO THE SPINNER
		//Spinner spinner = (Spinner) findViewById(R.id.spinner);
//		new SpinString(view, R.id.spinner);
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
//				R.array.frequencyChoices, android.R.layout.simple_spinner_item);
//		// Specify the layout to use when the list of choices appears
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner.setAdapter(adapter);

		//TODO: This spinner is not being populated with the frequency choices.
		new SpinString(view, R.id.spinner, "R.array.frequencyChoices");

		//set up text boxes to be ready
		Calendar calendar = new Calendar() {
			@Override
			protected void computeTime() {

			}

			@Override
			protected void computeFields() {

			}

			@Override
			public void add(int field, int amount) {

			}

			@Override
			public void roll(int field, boolean up) {

			}

			@Override
			public int getMinimum(int field) {
				return 0;
			}

			@Override
			public int getMaximum(int field) {
				return 0;
			}

			@Override
			public int getGreatestMinimum(int field) {
				return 0;
			}

			@Override
			public int getLeastMaximum(int field) {
				return 0;
			}
		};
//		//set the date to two months from today
		calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) + 2));
		// TODO: This line of code is causing an issue with linking the calendar
//		new DateWrapper(view, R.id.finishDate, calendar);
		new EditString(view, R.id.goalName);

		//set up submit button to be clicked
		new ButtonWrapper(view, R.id.submitFormButton, new submitNewGoalListener());

		return view;


	}

	@Override
	protected String getTitle() {
		return "Add New Goal";
	}

	private class submitNewGoalListener implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			//TODO: here we need to save the goal before moving back to the home screen

			Random rand = new Random();
			int n =rand.nextInt(200) + 1;
            edu.byui.cit.model.Goal test1 = new Goal();
			test1.setGoalID(n);
			Context ctx = getActivity().getApplicationContext();
			GoalDAO dao = AppDatabase.getInstance(ctx).getGoalDAO();
			dao.insert(test1);

			//once the goal is saved, go back to the main lander fragment
			((MainActivity) getActivity()).switchFragment(MainActivity.homeFrag);
		}
	}
}
