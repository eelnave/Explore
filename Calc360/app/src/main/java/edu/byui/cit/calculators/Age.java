package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.NumberFormat;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;
import java.time.*;
import java.time.temporal.ChronoUnit;

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
		exactAge= new TextWrapper(view, R.id.exactAge);

		EditWrapper[] inputs = { numberOne, numberTwo, numberThree };
		ControlWrapper[] toClear = { numberOne, numberTwo, numberThree, exactAge };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	LocalDate start = LocalDate.of(numberThree, numberTwo, numberOne);
	LocalDate end = LocalDate.now(); // use for age-calculation: LocalDate.now()
	long years = ChronoUnit.YEARS.between(start, end);

				@Override
				protected void compute() {

				}
			}
		}

//public class AgeCalculator {

//public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
//if ((birthDate != null) && (currentDate != null)) {
//	return Period.between(birthDate, currentDate).getYears();
//} else {
//	return 0;
//}
//}