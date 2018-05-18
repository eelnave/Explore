package edu.byui.cit.firstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public void power(View v)
    {

        //get the edit text
        EditText t1=(EditText)findViewById(R.id.editText);
        EditText t2=(EditText)findViewById(R.id.editText2);

        if (t1.getText().toString().isEmpty() == false &&
                t2.getText().toString().isEmpty() == false) {
            //convert value into int
            double x = Double.parseDouble(t1.getText().toString());
            double y = Double.parseDouble(t2.getText().toString());

            //sum these two numbers
            double z = (Math.pow(x, y));

            //display this text to TextView
//            TextView tv_data = (TextView) findViewById(R.id.tv_result);
//            tv_data.setText("The answer is " + z);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
