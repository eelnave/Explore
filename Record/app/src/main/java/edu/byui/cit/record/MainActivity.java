package edu.byui.cit.record;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
	public static final String TAG = "Record";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		FloatingActionButton fab = findViewById(R.id.newGoalFAB);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//make the jump to the new goal screen
				Intent intent = new Intent(MainActivity.this, add_customgoal.class);
				startActivity(intent);
			}
		});
	}
}
