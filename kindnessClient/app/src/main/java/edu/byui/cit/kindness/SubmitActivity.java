package edu.byui.cit.kindness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.MalformedURLException;

public class SubmitActivity extends AppCompatActivity {
	EditText latEdit, longEdit;
	Button submit;


	private void addReports() {
		latEdit = findViewById(R.id.editLatitude);
		longEdit = findViewById(R.id.editLongitude);

		double latitude = Double.parseDouble(latEdit.getText().toString());
		double longitude = Double.parseDouble(longEdit.getText().toString());

		Report report = new Report(latitude, longitude);
		try {
			report.addReport();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit);

		submit = findViewById(R.id.submitButton);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addReports();
			}
		});
	}
}
