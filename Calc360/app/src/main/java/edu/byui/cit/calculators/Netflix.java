package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.w3c.dom.Text;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class Netflix extends CalcFragment{
	private final NumberFormat fmtNum;
	private EditDecimal ssp, hours1, hours2, hours3, minutes1, minutes2;
	private TextWrapper ttw, spentPer;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.netflix, container, false);

//		this layout of stuff : decimal1 = new EditDecimal(view, R.id.decimal1, this);
		ssp = new EditDecimal(view, R.id.ssp, this);
		hours1 = new EditDecimal(view, R.id.hours1, this);
		hours2 = new EditDecimal(view, R.id.hours2, this);
		hours3 = new EditDecimal(view, R.id.hours3, this);
		minutes1 = new EditDecimal(view, R.id.minutes1, this);
		minutes2 = new EditDecimal(view, R.id.minutes2, this);

//		result
		spentPer = new TextWrapper(view, R.id.spentPer);

//		initialize the calculator
		EditWrapper[] inputs = { ssp, hours1, hours2, hours3, minutes1, minutes2 };

//		clears the button
		ControlWrapper[] toClear = { ssp, hours1, hours2, hours3, minutes1, minutes2, ttw, spentPer };
		initialize(view, inputs, R.id.btnClear, toClear);

		return view;
	}

	public Netflix(){
		super();
//		get instance
		fmtNum = NumberFormat.getInstance();
	}

	@Override
	protected void compute(){
		if (ssp.isEmpty() ||  hours1.isEmpty() || hours2.isEmpty() || hours3.isEmpty() || minutes1.isEmpty() || minutes2.isEmpty()){
			int ssp, hours1, hours2, hours3, minutes1, minutes2 = 0;

		}else if (ssp.notEmpty() || hours1.notEmpty() || hours3.notEmpty() || minutes1.isEmpty() || minutes2.isEmpty()){
			double price = ssp.getDec();
			double firstHour = hours1.getDec();
			double secondHour = hours2.getDec();
			double thirdHour = hours3.getDec();
			double firstMin = minutes1.getDec();
			double secondMin = minutes2.getDec();

//			get the total
			double sum = (firstHour + secondHour + thirdHour + firstMin + secondMin) / price;
//			result
			spentPer.setText(fmtNum.format(sum));
		}else{
			spentPer.clear();
		}
	}
}
