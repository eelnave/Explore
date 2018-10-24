package edu.byui.cit.record;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.annotation.Nullable;


public class RepeatingNotification extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);

		//just testing text insertion...will actually put goal name here.
		TextView goalName = findViewById(R.id.notification_goalName);
		goalName.setText("Mow the grass");

		TextView goalNameInsertion = findViewById(R.id.notification_text2);
		String when = "day"; // = goal.getFrequencyChoice(); //Will need to actually find out the frequency
		goalNameInsertion.setText("Did you \"" + goalName.getText() + "\" during the last " + when + "?");
	}
}