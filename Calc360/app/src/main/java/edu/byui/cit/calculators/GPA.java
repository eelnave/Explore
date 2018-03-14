package edu.byui.cit.calculators;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinString;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Unit;


public final class GPA extends CalcFragment {

	private static final String
		KEY_GRADE_UNITS = "GPA.gradeUnits";

	private EditDecimal decCurrGpa, decCurrCred;
	private TextWrapper yourGrades, semesterGPA, overallGPA;
	private Button inputButton, removeButton;
	private SpinUnit spinGrades;

	private EditWrapper[] inputs;

	private final NumberFormat fmtrDec = NumberFormat.getInstance();

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gpa_calc, container,
				false);

		decCurrGpa = new EditDecimal(view, R.id.curGPA, this);
		decCurrCred = new EditDecimal(view, R.id.curCredits, this);

		yourGrades = new TextWrapper(view, R.id.yourGrades);
		semesterGPA = new TextWrapper(view, R.id.semesterGrade);
		overallGPA = new TextWrapper(view, R.id.yourCurGPA);



		Activity act = getActivity();

		spinGrades = new SpinUnit(act, view, R.id.gradeSpin,
				Length.getInstance(), R.array.gradeSpin,
				KEY_GRADE_UNITS, this);

		EditWrapper[] inputs = { };

		return view;
	}

	@Override
	protected void compute() {
		Unit gradeUnits = spinGrades.getSelectedItem();
	}
}
