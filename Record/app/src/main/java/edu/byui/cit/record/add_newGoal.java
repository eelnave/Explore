package edu.byui.cit.record;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.Goal;
import edu.byui.cit.model.GoalDAO;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.DateChangeListener;
import edu.byui.cit.widget.DateWrapper;
import edu.byui.cit.widget.EditString;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.SpinString;
import edu.byui.cit.widget.TextChangeListener;
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

		//set the time spinner to 6:00 PM by default
		TimePicker pickerTime = (TimePicker)getActivity().findViewById(R.id.timePicker);
		Calendar now = Calendar.getInstance();

		//these two setters are causing the app to not work
		//pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
		//pickerTime.setCurrentMinute(0);


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
		new EditString(view, R.id.goalName, new onDateTextChangeListener());

		//set up submit button to be clicked
		new ButtonWrapper(view, R.id.submitFormButton, new submitNewGoalListener());
		new DateWrapper(view, R.id.completionDate, calendar, new selectCompletionDateListener());

		return view;
	}

	@Override
	protected String getTitle() {
		return "Add New Goal";
	}

	private class submitNewGoalListener implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			//TODO: extract data from the form
			String goalName = getActivity().findViewById(R.id.goalName).toString();
			String tempDateHolder = getActivity().findViewById(R.id.finishDate).toString();
			String[] dateSplitter = tempDateHolder.split("/");
			//how do we convert this string to a date object?
//			Date finishDate = new Date(dateSplitter[])



			//TODO: here we need to save the goal before moving back to the home screen

			Random rand = new Random();
			int n =rand.nextInt(200) + 1;
            edu.byui.cit.model.Goal test1 = new Goal();
			test1.setGoalID(n);
			Context ctx = getActivity().getApplicationContext();
			GoalDAO dao = AppDatabase.getInstance(ctx).getGoalDAO();
			dao.insert(test1);

			//once the goal is saved, go back to the main lander fragment
			//we need to pop the fragment off the fragment stack, similar to pressing the back button.
			getActivity().getFragmentManager().popBackStack();
			//((MainActivity) getActivity()).switchFragment(MainActivity.homeFrag);

			//output message to user that the goal was saved
			Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Goal Recorded!", Toast.LENGTH_LONG);
			toast.show();
		}
	}

	private class selectCompletionDateListener implements DateChangeListener {
		@Override
		public void afterChanged(DateWrapper source, int year, int month, int dayOfMonth) {
			//TODO: Insert updated date into the text box here

		}
	}

	private class onDateTextChangeListener implements TextChangeListener {
		@Override
		public void textChanged(EditWrapper source) {
			//Change DatePicker from invisible to visible on click.
//			DatePicker picker = new DatePicker(R.id.completionDate);
//			picker.setVisibility(View.VISIBLE);

		}
	}
}