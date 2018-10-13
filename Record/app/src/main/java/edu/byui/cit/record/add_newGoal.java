package edu.byui.cit.record;

import java.time.Clock;
import java.time.Instant;
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
	private SpinString spinner;
	private EditString goalNameBox;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.frag_add_newgoal, container, false);

		goalNameBox = new EditString(view, R.id.goalName);
		spinner = new SpinString(view, R.id.spinner);

		//set the time spinner to 6:00 PM by default
		TimePicker pickerTime = (TimePicker)getActivity().findViewById(R.id.timePicker);
		Calendar now = Calendar.getInstance();

		//these two setters are causing the app to not work
//		pickerTime.setCurrentHour(6);
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
		new EditString(view, R.id.goalName);
//		new ButtonWrapper(view, R.id.finishDate, new onDateClickListener());
		//set up submit button to be clicked
		new ButtonWrapper(view, R.id.submitFormButton, new submitNewGoalListener());
		//this line is causing a crash
		new ButtonWrapper(view, R.id.dateButton, new onDateClickListener());

		return view;
	}

	@Override
	protected String getTitle() {
		return "Add New Goal";
	}

	private class submitNewGoalListener implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			String goalName = goalNameBox.getText();
			if (!goalName.isEmpty()) {
				//TODO: extract data from the form
				// goalName holds the name of the goal as a string


				// frequencyChoice stores the user's choice as an integer based on position
				int frequencyChoice = spinner.getSelectedItemPosition();

				//TODO: to extract a date, maybe use the DateWrapper instead?
				//			String tempDateHolder = getActivity().findViewById(R.id.finishDate).toString();
				//			String[] dateSplitter = tempDateHolder.split("/");
				//how do we convert this string to a date object?
				//			Date finishDate = new Date(dateSplitter[])

				//extracting time from the form
				TimePicker pickerTime = (TimePicker) getActivity().findViewById(R.id.timePicker);
				int hour = pickerTime.getHour();
				int minute = pickerTime.getMinute();


				//TODO: here we need to save the goal before moving back to the home screen

				// creating a test object for the database. VERY IMPORTANT!!! Inserting incomplete rows will break the database call
			Random rand = new Random();
			int n =rand.nextInt(200) + 1;
            edu.byui.cit.model.Goal test1 = new Goal();
			test1.setGoalID(n);
			test1.setTitle("milk the cow");
			test1.setDescription("literally go milk the cow");
			test1.setType(Goal.Type.text);
			test1.setFrequency(Goal.Frequency.daily);
			Date startDate = new Date();
			test1.setStart(startDate);
			test1.setEnd(startDate);
			// database call to clear table and then insert proper data. If table not cleared during testing, it won't work
			// remove clear table function when ready for production
			Context ctx = getActivity().getApplicationContext();
			GoalDAO dao = AppDatabase.getInstance(ctx).getGoalDAO();
			dao.clearTable();
			dao.insert(test1);

				//once the goal is saved, go back to the main lander fragment
				//we need to pop the fragment off the fragment stack, taking us back to the main screen
				// -- similar to pressing the back button.
				getActivity().getFragmentManager().popBackStack();

				//output message to user that the goal was saved
				Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Recorded Goal: \"" + goalName + "\", with fequency choice of "
						+ frequencyChoice + ". Remind at: " + hour + ":" + minute + "!", Toast.LENGTH_LONG);
				toast.show();
			}
			else{
				Toast toast = Toast.makeText(getActivity().getApplicationContext(), "STOP: You must specify a goal name!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

	private class selectCompletionDateListener implements DateChangeListener {
		@Override
		public void afterChanged(DateWrapper source, int year, int month, int dayOfMonth) {
			//TODO: Insert updated date into the text box here
//			DateWrapper thePicker = source;
//
//			Calendar calendar = Calendar.getInstance();
//			int junk = calendar.MONTH;

//			Toast toast = Toast.makeText(getActivity().getApplicationContext(), "working with " + junk, Toast.LENGTH_LONG);
		}
	}


	private class onDateClickListener implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			((MainActivity) getActivity()).switchFragment(new frag_calendarChoose(), new Bundle());
		}
	}
}