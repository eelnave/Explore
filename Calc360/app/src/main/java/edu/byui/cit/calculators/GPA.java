package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Academic;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.RadioWrapper;
import edu.byui.cit.text.TextWrapper;


public final class GPA extends CalcFragment {

	private EditDecimal editCurrGPA, editCurrCred;
	private TextWrapper yourGrades, semGPA, overallGPA;

	private RadioWrapper radioA, radioB, radioC, radioD, radioF;
	private RadioWrapper radioMinus, radioPlus, radioNone;
	private RadioWrapper radioCred05, radioCred1, radioCred2, radioCred3, radioCred4;

	private String yourGradesString = "";

	private Double semesterGPA;
	private Double semesterCredits;

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

		new ButtonWrapper(view, R.id.butGradeInput, new InputHandler());
		new ButtonWrapper(view, R.id.gpaClear, new ClearHandler());
		new ButtonWrapper(view, R.id.butGradeRemove, new RemoveHandler());

		radioA = new RadioWrapper(view, R.id.letterA, this);
		radioB = new RadioWrapper(view, R.id.letterB, this);
		radioC = new RadioWrapper(view, R.id.letterC, this);
		radioD = new RadioWrapper(view, R.id.letterD, this);
		radioF = new RadioWrapper(view, R.id.letterF, this);

		radioMinus = new RadioWrapper(view, R.id.gradeMinus, this);
		radioPlus = new RadioWrapper(view, R.id.gradePlus, this);
		radioNone = new RadioWrapper(view, R.id.gradeNone, this);

		radioCred05 = new RadioWrapper(view, R.id.credit05, this);
		radioCred1 = new RadioWrapper(view, R.id.credit1, this);
		radioCred2 = new RadioWrapper(view, R.id.credit2, this);
		radioCred3 = new RadioWrapper(view, R.id.credit3, this);
		radioCred4 = new RadioWrapper(view, R.id.credit4, this);

		return view;
	}

	@Override
	protected void compute() {
		calculateSemGPA();
		calculateOverallGPA();
	}

	private void calculateOverallGPA() {
		if (!editCurrGPA.isEmpty() && !editCurrCred.isEmpty()) {
			double lastGPA = Double.parseDouble(editCurrGPA.getText());
			double lastCreds = Double.parseDouble(editCurrCred.getText());

			double currCreds = lastCreds + semesterCredits;

			Double finalGPA = Math.floor(((semesterGPA * semesterCredits +
					lastGPA * lastCreds) / currCreds) * 100) / 100;

			overallGPA.setText(finalGPA.toString());
		}
	}

	private void calculateSemGPA() {
		String[] gradeCreds = yourGradesString.split(",");

		Double pointsCount = 0.0;
		semesterCredits = 0.0;

		for (String gradeCred : gradeCreds) {
			String[] result = gradeCred.split(":");
			String letter = result[0];
			Double credits = Double.parseDouble(result[1].trim());
			semesterCredits += credits;
			Double points = Academic.GPA.calculateGPA(letter.trim()) * credits;
			pointsCount += points;
		}

		semesterGPA = Math.floor((pointsCount / semesterCredits) * 100) / 100;

		semGPA.setText(semesterGPA.toString());
	}

	private boolean isValid() {

		boolean credits = radioCred05.isChecked() || radioCred1.isChecked() ||
				radioCred2.isChecked() || radioCred3.isChecked() ||
				radioCred4.isChecked();

		boolean letters = radioA.isChecked() || radioB.isChecked() ||
				radioC.isChecked() || radioD.isChecked() || radioF.isChecked();

		boolean symbol = radioMinus.isChecked() || radioPlus.isChecked() ||
				radioNone.isChecked();

		return credits && letters && symbol;
	}

	private double getCredits() {
		if (radioCred05.isChecked())
			return 0.5;
		else if (radioCred1.isChecked())
			return 1.0;
		else if (radioCred2.isChecked())
			return 2.0;
		else if (radioCred3.isChecked())
			return 3.0;
		else if (radioCred4.isChecked())
			return 4.0;
		else
			return 0;
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

	private final class RemoveHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			StringBuilder newGradeString = new StringBuilder();
			if(!yourGradesString.equals("")) {
				String[] array = yourGradesString.split(",");
				for (int i = 0; i < array.length - 1; i++) {
					if (i == 0)
						newGradeString = new StringBuilder(array[i]);
					else
						newGradeString.append(", ").append(array[i]);
				}
			}

			yourGradesString = newGradeString.toString();

			yourGrades.setText(yourGradesString);

			calculateSemGPA();
			calculateOverallGPA();
		}
	}

	private final class InputHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			if (isValid()) {
				String prior = yourGradesString;
				String[] grades = yourGradesString.split(",");
				String current;
				String letter = getLetter();
				String symbol = getSymbol();
				Double credits = getCredits();

				if (grades[0].equals(""))
					current = letter + symbol + " : " + credits;
				else
					current = prior + ", " + letter + symbol + " : " + credits;

				yourGradesString = current;

				yourGrades.setText(yourGradesString);

				calculateSemGPA();
				calculateOverallGPA();
			}
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
