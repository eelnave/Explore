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


		//populate the main screen area with current goals
		//test with filler data
		final ArrayList<String> myGoals = new ArrayList<>();
		myGoals.add("Read Scriptures");
		myGoals.add("Teach My Kid to Read");
		myGoals.add("Work on Android Apps");
		myGoals.add("Help Old Man Jenkins with His Lawn");
//
//		//this object represents the listView on the screen
//		ListView goalListView = (ListView) view.findViewById(R.id.goalList);
//		ArrayAdapter<String> goalListAdapter = new ArrayAdapter<>(getActivity(), R.layout.simplerow, myGoals);
//		goalListView.setAdapter(goalListAdapter);

//		myGoals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//				Bundle bundle = new Bundle();
//				String goalTitle = myGoals.get(i);
//				bundle.putString("goalTitle", goalTitle);
//				((MainActivity) getActivity()).switchFragment(new ViewGoal(), bundle);
//			}
//		});



//		FloatingActionButton fab = findViewById(R.id.newGoalFAB);
		new ButtonWrapper(view, R.id.newGoalButton, new newGoalClickHandler());
		new ButtonWrapper(view, R.id.deleteGoal, new showGoalsList());
		//		fab.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				//make the jump to the new goal screen
//				Intent intent = new Intent(MainActivity.this,
//						add_customgoal.class);
//				startActivity(intent);
//			}
//		});

		//Context ctx = getActivity().getApplicationContext();
		//GoalDAO dao = AppDatabase.getInstance(ctx).getGoalDAO();
		//List<Goal> myGoals = dao.getAll();
		//int size = myGoals.size();
		//for (int i = 0; i < size;i++){
			//int currentGoalID = myGoals.get(i).getGoalID();
		//}
		//int dataTestID = myGoals.get(0).getGoalID();
		//TextWrapper testWrapper = new TextWrapper(view, R.id.testText);
		//testWrapper.setText(dataTestID);


		//this object represents the listView on the screen
//		ListView mainListView = findViewById(R.id.toDoList);
//		ArrayAdapter<String> theAdapter = new ArrayAdapter<>(
//				this, R.layout.simplerow, theGoals);
//		// TODO: THIS IS THE THING THAT IS CAUSING THE CRASH
//		mainListView.setAdapter(theAdapter);
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
				Context ctx = getActivity().getApplicationContext();
				GoalDAO dao = AppDatabase.getInstance(ctx).getGoalDAO();
				List<Goal> myGoals = dao.getAll();

				ArrayList<String> stringGoals = new ArrayList<>();
				stringGoals.add(myGoals.get(0).toString());
				stringGoals.add(myGoals.get(0).toString());

				//this object represents the listView on the screen
				ListView mainListView = getActivity().findViewById(R.id.toDoList);
				ArrayAdapter<String> theAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.simplerow, stringGoals);
				// TODO: THIS IS THE THING THAT IS CAUSING THE CRASH
				mainListView.setAdapter(theAdapter);

			}
			catch (Exception ex) {
				Log.e("Record", ex.toString());
			}
		}
	}
}
