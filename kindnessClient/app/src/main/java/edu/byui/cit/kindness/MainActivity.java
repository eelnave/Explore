package edu.byui.cit.kindness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public final class MainActivity extends AppCompatActivity {
	public static final String TAG = "Kindness";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
}
