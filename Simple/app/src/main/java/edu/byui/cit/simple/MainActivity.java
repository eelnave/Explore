package edu.byui.cit.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

	private EditText num1;
	private EditText num2;
	private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);


		num1 =findViewById(R.id.etNum1);
		num2 =findViewById(R.id.etNum2);
		final Button btnClear =findViewById(R.id.btnClear);
		result = findViewById(R.id.tvClear);

		btnClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				num1.getText().clear();
				num2.getText().clear();
				result.setText("");


			}
		});

		num1.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				    try {
						double number1 = Double.parseDouble(num1.getText().toString());
						double number2 = Double.parseDouble(num2.getText().toString());
						double sum = Math.pow(number1,number2);
						result.setText(String.valueOf(sum));
					}
					catch(Exception ex) {
			        	//Do nothing
					}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    double number1 = Double.parseDouble(num1.getText().toString());
                    double number2 = Double.parseDouble(num2.getText().toString());
                    double sum = Math.pow(number1,number2);
                    result.setText(String.valueOf(sum));
                }
                catch(Exception ex) {
                    //Do nothing
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
	}

}