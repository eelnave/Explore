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
		numberTwo = new EditInteger(view, R.id.numberTwo, this);
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

		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.set( year, month, day);
		long currentTime = System.currentTimeMillis();
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(currentTime);

		//Difference between years
		year = now.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		int actualMonth = now.get(Calendar.MONTH) + 1;
		int birthMonth = dateOfBirth.get(Calendar.MONTH) + 1;

		//Difference between months
		month = actualMonth - birthMonth;

		//If month difference is negative then reduce years by one
		if (month < 0) {
			year = year -1;
			month = 12 - birthMonth + actualMonth;
			if (now.get(Calendar.DATE) < dateOfBirth.get(Calendar.DATE))
				month = month - 1;
		}
		else if (month == 0 && now.get(Calendar.DATE) < dateOfBirth.get(Calendar.DATE)) {
			year = year - 1;
			month = 11;
		}

		//Calculate days
		if (now.get(Calendar.DATE) > dateOfBirth.get(Calendar.DATE))
			day = now.get(Calendar.DATE) - dateOfBirth.get(Calendar.DATE);
		else if (now.get(Calendar.DATE) < dateOfBirth.get(Calendar.DATE)) {
			int today = now.get(Calendar.DAY_OF_MONTH);
			now.add(Calendar.MONTH, -1);
			day = now.getActualMaximum(Calendar.DAY_OF_MONTH) - dateOfBirth.get(Calendar.DAY_OF_MONTH) + today;
		}
		else {
			day = 0;
			if (month == 12) {
				year = year + 1;
				month = 0;
			}
		}
			String age = Integer.toString(year) + " years " + Integer.toString(month) + " months " + Integer.toString(day) + " days old!";

			exactAge.setText(fmtrDec.format(year));
		}
	}
