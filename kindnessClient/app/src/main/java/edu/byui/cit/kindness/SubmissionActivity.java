package edu.byui.cit.kindness;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class SubmissionActivity extends AppCompatActivity {

	private ServerConnect dbConnection = new ServerConnect();
	private EditText longitude, latitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submission);

		ActionBar actBar = getSupportActionBar();
		assert actBar != null;
		actBar.setDisplayHomeAsUpEnabled(true);

		Spinner spinner = findViewById(R.id.submission_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.submission_spinner_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		final Button button = findViewById(R.id.submitButton);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				insertReport();
			}
		});

		longitude = findViewById(R.id.editText);
		latitude = findViewById(R.id.editText2);
	}

	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);

		Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
		startActivityForResult(myIntent, 0);
		return true;

	}
	protected void insertReport() {
		String latCoordinate = latitude.getText().toString();
		String longCoordinate = longitude.getText().toString();

		dbConnection.insertReport(
				Double.parseDouble(latCoordinate),
				Double.parseDouble(longCoordinate)
		);
	}
}
