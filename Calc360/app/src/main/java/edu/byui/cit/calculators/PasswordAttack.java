package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Computing;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public class PasswordAttack extends CalcFragment {
	private EditInteger editDomain, editLength, editKeysSeconds, editMachines;
	private TextWrapper resKeySpace, resSec, resHour, resDay, resYear;
	private EditWrapper[] inputs;
	private final NumberFormat fmtrDec;

	public PasswordAttack() {
		super();
		fmtrDec = NumberFormat.getInstance();
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.password_attack, container,
				false);

		editDomain = new EditInteger(view, R.id.charNum, this);
		editLength = new EditInteger(view, R.id.numWords, this);
		editKeysSeconds = new EditInteger(view, R.id.keysTested, this);
		editMachines = new EditInteger(view, R.id.numMachines, this);

		resKeySpace = new TextWrapper(view, R.id.numKeySpace);
		resSec = new TextWrapper(view, R.id.timeSec);
		resHour = new TextWrapper(view, R.id.timeHrs);
		resDay = new TextWrapper(view, R.id.timeDays);
		resYear = new TextWrapper(view, R.id.timeYears);

		inputs = new EditWrapper[]{
				editDomain, editLength, editKeysSeconds, editMachines
		};
		ControlWrapper[] toClear = {
				editDomain, editLength, editKeysSeconds,
				editMachines, resKeySpace, resSec, resHour, resDay, resYear
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(inputs)) {
			int domain = editDomain.getInt();
			int length = editLength.getInt();
			int keysSecond = editKeysSeconds.getInt();
			int machines = editMachines.getInt();
			double keyspace = Computing.PasswordAttack.getKeySpace(
					domain, length);

			resKeySpace.setText(fmtrDec.format(keyspace));
			resSec.setText(fmtrDec.format(Computing.PasswordAttack.
					getTimeSeconds(keyspace, keysSecond, machines)));
			resHour.setText(fmtrDec.format(Computing.PasswordAttack.
					getTimeHours(keyspace, keysSecond, machines)));
			resDay.setText(fmtrDec.format(Computing.PasswordAttack.
					getTimeDays(keyspace, keysSecond, machines)));
			resYear.setText(fmtrDec.format(Computing.PasswordAttack.
					getTimeYears(keyspace, keysSecond, machines)));
		}
	}
}
