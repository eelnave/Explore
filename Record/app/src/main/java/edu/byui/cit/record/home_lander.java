package edu.byui.cit.record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
//		FloatingActionButton fab = findViewById(R.id.newGoalFAB);
		new ButtonWrapper(view, R.id.newGoalButton, new newGoalClickHandler());
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
			((MainActivity) getActivity()).switchFragment(new add_newGoal());
		}
	}
}
