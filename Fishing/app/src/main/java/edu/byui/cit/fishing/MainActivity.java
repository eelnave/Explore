package edu.byui.cit.fishing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Fishing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

                		Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner1);

                		ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                				android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.water));
        		myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		mySpinner1.setAdapter(myAdapter1);

                		Spinner mySpinner2 = (Spinner) findViewById(R.id.spinner2);

                		ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                				android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.species));
        	myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		mySpinner2.setAdapter(myAdapter2);

                		Spinner mySpinner3 = (Spinner) findViewById(R.id.spinner3);

                		ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(MainActivity.this,
                				android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.season));
        		myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		mySpinner3.setAdapter(myAdapter3);
    }



}
