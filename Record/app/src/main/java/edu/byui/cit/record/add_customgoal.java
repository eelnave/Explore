package edu.byui.cit.record;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_customgoal extends AppCompatActivity {

    private EditText txtGoalName, txtCompletiondate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customgoal);

        //set up text boxes to be read
        txtGoalName = findViewById(R.id.goalName);

        //set up button to be clicked
        Button submitButton = findViewById(R.id.submitFormButton);
        submitButton.setOnClickListener(new NewGenericGoal());
    }

    private class NewGenericGoal implements View.OnClickListener {
        @Override
        public void onClick(View submitButton){
            //put what happens when the RECORD! button is clicked.
            //collect data from the form.

            //make a new row in the goal database with the data.

        }
    }
}
