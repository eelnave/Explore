package edu.byui.cit.calculators;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.ArrayList;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Academic.Course;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinDecimal;
import edu.byui.cit.text.SpinString;
import edu.byui.cit.text.TextWrapper;


public final class GPA extends CalcFragment {
	private EditDecimal decCurrGPA, decCurrCred;
	private SpinString spinGrades;
	private SpinDecimal spinCredits;
	private TextWrapper yourGrades, semGPA, cumGPA;

	private ArrayList<Course> courses = new ArrayList<>();
	private final NumberFormat fmtrGPA;

	public GPA() {
		super();
		fmtrGPA = NumberFormat.getInstance();
		fmtrGPA.setMinimumFractionDigits(2);
		fmtrGPA.setMaximumFractionDigits(2);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gpa, container, false);

		decCurrGPA = new EditDecimal(view, R.id.curGPA, this);
		decCurrCred = new EditDecimal(view, R.id.curCredits, this);

		Activity act = getActivity();
		spinGrades = new SpinString(view, R.id.spinGrade, null, this);
		spinCredits = new SpinDecimal(act, view, R.id.spinCredits,
				R.array.gpaCredits, null, this);
		new ButtonWrapper(view, R.id.btnAdd, new AddHandler());
		new ButtonWrapper(view, R.id.btnRemove, new RemoveHandler());

		yourGrades = new TextWrapper(view, R.id.yourGrades);
		semGPA = new TextWrapper(view, R.id.semGPA);
		cumGPA = new TextWrapper(view, R.id.cumGPA);

		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	private final class AddHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			String grade = spinGrades.getSelectedItem();
			float credits = spinCredits.getDec();
			Course course = new Course(grade, credits);
			courses.add(course);
			yourGrades.setText(Course.toGradeString(courses));
			callCompute();
		}
	}

	private final class RemoveHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			courses.remove(courses.size() - 1);
			yourGrades.setText(Course.toGradeString(courses));
			callCompute();
		}
	}


	@Override
	protected void compute() {
		computeSemester();
		computeCumulative();
	}

	private void computeSemester() {
		if (courses.size() > 0) {
			double gpa = Course.computeGPA(0, 0, courses);
			semGPA.setText(fmtrGPA.format(gpa));
		}
		else {
			semGPA.clear();
		}
	}

	private void computeCumulative() {
		if (EditWrapper.allNotEmpty(decCurrGPA, decCurrCred) || courses.size() > 0) {
			double gpa = 0;
			double credits = 0;

			if (EditWrapper.allNotEmpty(decCurrGPA, decCurrCred)) {
				gpa = decCurrGPA.getDec();
				credits = decCurrCred.getDec();
			}

			gpa = Course.computeGPA(gpa, credits, courses);
			cumGPA.setText(fmtrGPA.format(gpa));
		}
		else {
			cumGPA.clear();
		}
	}


	private final class ClearHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			ControlWrapper[] toClear = {
					decCurrCred, decCurrGPA, yourGrades, semGPA, cumGPA
			};
			for (ControlWrapper ctrl : toClear) {
				ctrl.clear();
			}
			courses.clear();
		}
	}
}
