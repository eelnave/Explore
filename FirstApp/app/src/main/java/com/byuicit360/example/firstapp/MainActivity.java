package com.byuicit360.example.firstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText txtOne, txtTwo;
    private TextView txtFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtOne = findViewById(R.id.editText);
        txtTwo = findViewById(R.id.editText2);
        txtFour = findViewById(R.id.textView4);

        Button btnClear = findViewById(R.id.button2);
        btnClear.setOnClickListener(new MowTheGrass());

        txtTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NumberFormat formatter = NumberFormat.getInstance();
                String input = txtOne.getText().toString();
                String input2 = txtTwo.getText().toString();
                try {
                    Number number = formatter.parse(input);
                    Number number2 = formatter.parse(input2);
                    double one = number.doubleValue();
                    double two = number2.doubleValue();
                    double result = Math.pow(one, two);
                    txtFour.setText(formatter.format(result));
                }
                catch (Exception ex) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private final class MowTheGrass implements View.OnClickListener {
        @Override

        public void onClick(View button2) {
            txtOne.getText().clear();
            txtTwo.getText().clear();
            txtOne.requestFocus();
        }
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
