package edu.byui.cit.calculators;

		import android.os.Bundle;
		import android.view.LayoutInflater;
		import android.view.View;
		import android.view.ViewGroup;

		import java.text.NumberFormat;
		import java.util.Calendar;
		import edu.byui.cit.calc360.CalcFragment;

		import edu.byui.cit.calc360.R;
		import edu.byui.cit.text.ControlWrapper;
		import edu.byui.cit.text.EditInteger;
		import edu.byui.cit.text.EditWrapper;
		import edu.byui.cit.text.TextWrapper;

public final class Age extends CalcFragment {
	private final NumberFormat fmtrDec;

	private EditInteger numberOne;
	private EditInteger numberTwo;
	private EditInteger numberThree;
	private TextWrapper exactAge;


	public Age() {
		// Call constructor
		super();

		fmtrDec = NumberFormat.getInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.age, container, false);

		// Reference each of the text fields
		numberOne = new EditInteger(view, R.id.numberOne, this);
		numberTwo = new EditInteger(view, R.id.numberThree, this);
		numberThree = new EditInteger(view, R.id.numberThree, this);
		exactAge = new TextWrapper(view, R.id.exactAge);

		EditWrapper[] inputs = { numberOne, numberTwo, numberThree };
		ControlWrapper[] toClear = { numberOne, numberTwo, numberThree, exactAge };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {

			Integer day = numberOne.getInt();
			Integer month = numberTwo.getInt();
			Integer year = numberThree.getInt();

			Calendar now = Calendar.getInstance();
			Calendar dateOfBirth = Calendar.getInstance();
			int age;
			int monthage;
			int dayage;
			String actualAge;
			if (dateOfBirth.after(now)) {
				throw new IllegalArgumentException(
						"Can't be born in the future");
			}
			else {
				int year1 = now.get(Calendar.YEAR);
				int year2 = year;
				age = year1 - year2;
				int month1 = now.get(Calendar.MONTH);
				int month2 = month;
				if (month2 > month1) {
					monthage = month1 - month2;
					int day1 = now.get(Calendar.DAY_OF_MONTH);
					int day2 = day;
					if (day1 > day2) {
						dayage = day2 - day1;
					}
				}
				else if (month1 == month2) {
					monthage = 0;
					int day1 = now.get(Calendar.DAY_OF_MONTH);
					int day2 = day;
					if (day2 > day1) {
						dayage = day2 - day1;
					}
				}
			}

			exactAge.setText(fmtrDec.format(age));
		}
	}
