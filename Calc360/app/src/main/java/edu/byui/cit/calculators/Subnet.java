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

import java.util.regex.*;


public class Subnet extends CalcFragment {
	private EditInteger ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8;
	private EditWrapper[] inputs;
	private TextWrapper availableHosts, numberOfSubnets;
	private final NumberFormat fmtrInt;

	public Subnet() {
		// Call the constructor in the parent class.
		super();

		fmtrInt = NumberFormat.getInstance();
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.subnet, container, false);

		ip1 = new EditInteger(view, R.id.ipValue1, this);
		ip2 = new EditInteger(view, R.id.ipValue2, this);
		ip3 = new EditInteger(view, R.id.ipValue3, this);
		ip4 = new EditInteger(view, R.id.ipValue4, this);
		ip5 = new EditInteger(view, R.id.ipValue5, this);
		ip6 = new EditInteger(view, R.id.ipValue6, this);
		ip7 = new EditInteger(view, R.id.ipValue7, this);
		ip8 = new EditInteger(view, R.id.ipValue8, this);

		numberOfSubnets = new TextWrapper(view, R.id.numberOfSubnets);
		availableHosts = new TextWrapper(view, R.id.availableHosts);

		inputs = new EditWrapper[]{ ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8 };
		ControlWrapper[] toClear = { ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8,
				availableHosts, numberOfSubnets };
		initialize(view, inputs, R.id.btnClear, toClear);

		return view;
	}

	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(inputs)) {
			//	int input1 = ip1.getInt();  Planning on doing more later
			//	int input2 = ip2.getInt();  Planning on doing more later
			//	int input3 = ip3.getInt();  Planning on doing more later
			//	int input4 = ip4.getInt();  Planning on doing more later

			String sub1 = Long.toBinaryString(ip5.getInt());
			String sub2 = Long.toBinaryString(ip6.getInt());
			String sub3 = Long.toBinaryString(ip7.getInt());
			String sub4 = Long.toBinaryString(ip8.getInt());

			String subnet = sub1 + "." + sub2 + "." + sub3 + "." + sub4;

			double hosts = Computing.Subnet.calculateHosts(subnet);
			double nets = Computing.Subnet.calculateNets(subnet);

			numberOfSubnets.setText(fmtrInt.format(nets));
			availableHosts.setText(fmtrInt.format(hosts));
		}
		else {
			availableHosts.clear();
			numberOfSubnets.clear();
		}
	}
}
