package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Finance;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.RadioWrapper;
import edu.byui.cit.text.TextWrapper;


public class HouseAffordability extends CalcFragment {
	private EditInteger editSal, editMortPerc;
	private EditDecimal editLoanRate;
	private RadioWrapper radioFifteen, radioThirty;
	private TextWrapper resCost;
	private EditWrapper[] inputs;
	private final NumberFormat fmtrCur = NumberFormat.getCurrencyInstance();


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.house_affordability, container,
				false);

		editSal = new EditInteger(view, R.id.houseSalary, this);
		editMortPerc = new EditInteger(view, R.id.housePercentage, this);
		editLoanRate = new EditDecimal(view, R.id.houseInterest, this);

		radioFifteen = new RadioWrapper(view, R.id.houseRadFif, this);
		radioThirty = new RadioWrapper(view, R.id.houseRadThir, this);

		resCost = new TextWrapper(view, R.id.houseCost);

		inputs = new EditWrapper[]{ editSal, editMortPerc, editLoanRate };
		ControlWrapper[] toClear = {
				editSal, editLoanRate, editMortPerc, resCost
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(inputs) &&
				(radioFifteen.isChecked() || radioThirty.isChecked())) {

			double annualRate = editLoanRate.getDec() / 100.0;

			int years = 0;
			if (radioFifteen.isChecked()) {
				years = 15;
			}
			else if (radioThirty.isChecked()) {
				years = 30;
			}

			double payment = ((double)editSal.getInt() / 12.0) *
					((double)editMortPerc.getInt() / 100.0);

			double amount = Finance.loanAmount(annualRate, years, 12, payment);
			resCost.setText(fmtrCur.format(amount));
		}
	}
}
