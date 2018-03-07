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

public class Subnet extends CalcFragment {
	private EditInteger ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8;
	private EditWrapper[] inputs;
	private TextWrapper availableHosts, numberOfSubnets, networkAddress, broadcastAddress;
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
		networkAddress = new TextWrapper(view, R.id.networkAddress);
		broadcastAddress = new TextWrapper(view, R.id.broadcast);

		inputs = new EditWrapper[]{ ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8 };
		ControlWrapper[] toClear = { ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8,
				availableHosts, numberOfSubnets, networkAddress, broadcastAddress };
		initialize(view, inputs, R.id.btnClear, toClear);

		return view;
	}

	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(inputs)) {

			String ip = ip1.getInt() + "." + ip2.getInt() + "." +
					ip3.getInt() + "." + ip4.getInt();

			String subnet = ip5.getInt() + "." + ip6.getInt() +
					"." + ip7.getInt() + "." + ip8.getInt();

			double hosts = Computing.Subnet.calculateHosts(subnet);
			double nets = Computing.Subnet.calculateNets(subnet);
			String netAddress = Computing.Subnet.getNetworkAddress(ip, subnet);
			String broadcast = Computing.Subnet.getBroadcast(ip, subnet);

			numberOfSubnets.setText(fmtrInt.format(nets));
			availableHosts.setText(fmtrInt.format(hosts));
			networkAddress.setText(netAddress);
			broadcastAddress.setText(broadcast);
		}
		else {
			availableHosts.clear();
			numberOfSubnets.clear();
		}
	}
}
