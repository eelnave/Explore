package cit360.calc_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class main_activity extends AppCompatActivity {

    EditText value1, value2;
    TextView valueResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        value1 = findViewById(R.id.textNumber1);
        value2 = findViewById(R.id.textNumber2);
        valueResult = findViewById(R.id.textResult);
    }

    public double powerFunction (double a, double b){
        return Math.pow(a, b);
    }

    @Override
    public boolean boolIfDouble (String a){
        String doublePattern = "([0-9]*)\\.([0-9]*)";
        boolean ifMatch = Pattern.matches(doublePattern, a);
        return ifMatch;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
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
