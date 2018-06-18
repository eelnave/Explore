package edu.byui.cit.record;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class add_customgoal extends AppCompatActivity {

    //global integer to hold the current radio button selection.
    private static int buttonSelection = 0;
    private static boolean checkedOngoing = false;

    private EditText txtGoalName, txtCompletiondate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customgoal);

        //populate the spinner with the frequency choices
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.frequencyChoices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //set up text boxes to be read
        txtGoalName = findViewById(R.id.goalName);
        txtCompletiondate = findViewById(R.id.finishDate);

        //set up button to be clicked
        Button submitButton = findViewById(R.id.submitFormButton);
        submitButton.setOnClickListener(new NewGenericGoal());
    }
    //this code is commented out because we got rid of the radio buttons.
//    public void onRadioButtonClicked(View view) {
//        boolean checked = ((RadioButton) view).isChecked();
        //check which button was clicked and assign the corresponding value to the buttonSelection variable
        //1=daily, 2=weekly, 3=morning and night
//        switch (view.getId()){
//            case R.id.daily:
//                if (checked){
//                    buttonSelection = 1;
//                }
//            case R.id.weekly:
//                if (checked){
//                    buttonSelection = 2;
//                }
//            case R.id.morningAndNight:
//                if (checked){
//                    buttonSelection = 3;
//                }
//        }
//    }

    public void onCheckBoxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch(view.getId()){
            case R.id.checkBoxOngoing:
                if (isChecked){
                    checkedOngoing = true;
                }
                else {
                    checkedOngoing = false;
                }
        }
    }

    private class NewGenericGoal implements View.OnClickListener {
        @Override
        public void onClick(View submitButton){
            //put what happens when the RECORD! button is clicked.
            //collect data from the form.
            String goalName = txtGoalName.toString();

            String parseDate = txtCompletiondate.toString();
            SimpleDateFormat finishDate = new SimpleDateFormat(parseDate);

            //--state of check boxes and radio buttons are stored in global variables already defined.--

            //make a new row in the goal database with the data.
            //TODO: once database objects are ready, insert all of this data into a new row.
        }
    }
}
