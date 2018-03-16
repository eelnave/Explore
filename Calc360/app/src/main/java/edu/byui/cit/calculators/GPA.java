package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.RadioWrapper;
import edu.byui.cit.text.TextWrapper;


public final class GPA extends CalcFragment {

	private EditDecimal editCurrGPA, editCurrCred;
	private TextWrapper yourGrades, semGPA, overallGPA;

	ButtonWrapper inputGrade;

	private RadioWrapper radioA, radioB, radioC, radioD, radioF;
	private RadioWrapper radioMinus, radioPlus;
	List<RadioWrapper> letters = null;
	List<RadioWrapper> symbols = null;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gpa_calc, container,
				false);

		editCurrCred = new EditDecimal(view, R.id.curCredits, this);
		editCurrGPA = new EditDecimal(view, R.id.curGPA, this);

		yourGrades = new TextWrapper(view, R.id.yourGrades);
		semGPA = new TextWrapper(view, R.id.semGPA);
		overallGPA = new TextWrapper(view, R.id.overallGPA);

		inputGrade = new ButtonWrapper(view, R.id.butGradeInput, this);

		radioA = new RadioWrapper(view, R.id.letterA, this);
		radioB = new RadioWrapper(view, R.id.letterB, this);
		radioC = new RadioWrapper(view, R.id.letterC, this);
		radioD = new RadioWrapper(view, R.id.letterD, this);
		radioF = new RadioWrapper(view, R.id.letterF, this);

		radioMinus = new RadioWrapper(view, R.id.gradeMinus, this);
		radioPlus = new RadioWrapper(view, R.id.gradePlus, this);

		letters.add(radioA);
		letters.add(radioB);
		letters.add(radioC);
		letters.add(radioD);
		letters.add(radioF);

		symbols.add(radioMinus);
		symbols.add(radioPlus);

		EditWrapper[] inputs = { editCurrGPA, editCurrCred };
		ControlWrapper[] toClear = {
				editCurrCred, editCurrGPA, yourGrades, semGPA, overallGPA
		};

		initialize(view, inputs, R.id.gpaClear, toClear);

		return view;
	}

	private	void addGrade() {
		String letterString = "";
		String symbolString = "";

		for (RadioWrapper symbol: symbols) {
			if(symbol.isChecked()) {
				symbolString = symbol.toString();
			}
		}
		for (RadioWrapper letter: letters) {
			if (letter.isChecked()) {
				letterString = letter.toString();
			}
		}
		yourGrades.setText(letterString + symbolString);
	}
}
