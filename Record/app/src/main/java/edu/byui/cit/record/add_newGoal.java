package edu.byui.cit.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.SpinString;


public class add_newGoal extends CITFragment {
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.frag_add_newgoal, container, false);

		//we need to link everything on the screen here so that the objects can do stuff
		//TODO: We need to change all these objects to use widgets
		//Spinner spinner = (Spinner) findViewById(R.id.spinner);
//		new SpinString(view, R.id.spinner);
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
//				R.array.frequencyChoices, android.R.layout.simple_spinner_item);
//		// Specify the layout to use when the list of choices appears
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		// Apply the adapter to the spinner
//		view.
//		spinner.setAdapter(adapter);

		//let's just start over using the SpinString
		//the freaking wrapper won't accept the ID of the spinner as a string, it wants an int??
		//TODO: figure out how to specify the spinner using some kind of integer ID
		new SpinString(view, 1234, "R.array.frequencyChoices");

		//set up text boxes to be ready
//		txtGoalName = findViewById(R.id.goalName);
//		txtCompletiondate = findViewById(R.id.finishDate);
//
//		//set up button to be clicked
//		Button submitButton = findViewById(R.id.submitFormButton);
//		submitButton.setOnClickListener(new add_customgoal.NewGenericGoal());

		return view;


	}

	@Override
	protected String getTitle() {
		return "Add New Goal";
	}
}
