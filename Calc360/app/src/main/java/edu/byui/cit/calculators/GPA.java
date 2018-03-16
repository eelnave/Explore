package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
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
	RadioWrapper[] letters, symbols;

	private String yourGradesString = "";

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

		new ButtonWrapper(view, R.id.butGradeInput, new GradeHandler());
		new ButtonWrapper(view, R.id.gpaClear, new ClearHandler());

		radioA = new RadioWrapper(view, R.id.letterA, this);
		radioB = new RadioWrapper(view, R.id.letterB, this);
		radioC = new RadioWrapper(view, R.id.letterC, this);
		radioD = new RadioWrapper(view, R.id.letterD, this);
		radioF = new RadioWrapper(view, R.id.letterF, this);

		radioMinus = new RadioWrapper(view, R.id.gradeMinus, this);
		radioPlus = new RadioWrapper(view, R.id.gradePlus, this);

		letters = new RadioWrapper[]{ radioA, radioB, radioC, radioD, radioF };
		symbols = new RadioWrapper[]{ radioMinus, radioPlus };

		return view;
	}

	@Override
	protected void compute() {

	}

	private String getLetter() {
		if (radioA.isChecked())
			return "A";
		else if (radioB.isChecked())
			return "B";
		else if (radioC.isChecked())
			return "C";
		else if (radioD.isChecked())
			return "D";
		else if (radioF.isChecked())
			return "F";
		else
			return "";
	}

	private String getSymbol() {
		if (radioMinus.isChecked())
			return "-";
		else if (radioPlus.isChecked())
			return "+";
		else
			return  "";
	}

	private final class GradeHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			String prior = yourGradesString;
			String[] grades = yourGradesString.split(",");
			String current;
			String letter = getLetter();
			String symbol = getSymbol();

			if (grades[0] == "")
				current = letter + symbol;
			else
				current = prior + "," + letter + symbol;

			yourGradesString = current;

			yourGrades.setText(yourGradesString);
		}
	}
	private final class ClearHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			ControlWrapper[] toClear = {
					editCurrCred, editCurrGPA, yourGrades, semGPA, overallGPA
			};
			for (ControlWrapper clear : toClear) {
				clear.clear();
			}
			yourGradesString = "";
		}
	}
}
