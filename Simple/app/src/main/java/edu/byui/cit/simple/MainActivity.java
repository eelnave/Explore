package edu.byui.cit.simple;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
	private EditText txtOne, txtTwo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		txtOne = findViewById(R.id.editText);
		txtTwo = findViewById(R.id.editText2);

		Button btnCompute = findViewById(R.id.button);
		Button btnClear = findViewById(R.id.button2);
		btnCompute.setOnClickListener(new ComputeHandler());
		btnClear.setOnClickListener(new ClearHandler());

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action",
						Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items
		// to the action bar if it is present.
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


	private final class ComputeHandler implements View.OnClickListener {
		@Override
		public void onClick(View button) {
			NumberFormat formatter = NumberFormat.getInstance();
			String input = txtOne.getText().toString();
			try {
				Number number = formatter.parse(input);
				double one = number.doubleValue();
				double result = one;
				txtTwo.setText(formatter.format(result));
			}
			catch (Exception ex) {
				// Do nothing.
			}
		}
	}

	private final class ClearHandler implements View.OnClickListener {
		@Override
		public void onClick(View button) {
			txtOne.getText().clear();
			txtTwo.getText().clear();
			txtOne.requestFocus();
		}
	}
}
