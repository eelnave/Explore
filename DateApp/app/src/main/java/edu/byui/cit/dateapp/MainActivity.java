package edu.byui.cit.dateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gavin_main_layout);

    }

    public void findOnClick (View v) {

        //Displays a floating text - Created to test button and XML interaction
        if (v.getId() == R.id.buttonRandom) {
            MessageBox("Hello World");
        }
    }

    public void MessageBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void customOnClick (View v) {

    }

    //TODO make text view
}