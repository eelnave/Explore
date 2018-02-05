// kristina and josh new subnet calc
package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.TextWrapper;


public class Subnet extends CalcFragment {
	private EditInteger ip1;
	private TextWrapper availableHosts, maskText;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.subnet, container, false);

		ip1 = new EditInteger(view, R.id.ipValue1, this);

		maskText = new TextWrapper(view, R.id.maskText);
		availableHosts = new TextWrapper(view, R.id.availableHosts);

		return view;
	}

	//To be implemented later
	//String networkAddr = "";
	// String[] ipAddrParts = getIP.split("\\.");
	// String[] maskParts = mask.split("\\.");
	//for (int i = 0; i < 4; i++) {
	//   int x = Integer.parseInt(ipAddrParts[i]);
	//   int y = Integer.parseInt(maskParts[i]);
	//  int z = x & y;
	//   networkAddr += z + ".";
	// }


	@Override
	protected void compute() {
		int cc = ip1.getInt();
		String mask = null;
		int ipHosts = 0;
		if (cc > 0 && cc < 224) {
			if (cc < 128) {
				mask = "255.0.0.0";
				ipHosts = 16777214;
			}
			if (cc > 127 && cc < 192) {
				mask = "255.255.0.0";
				ipHosts = 65524;
			}
			if (cc > 191) {
				mask = "255.255.255.0";
				ipHosts = 254;
			}
		}
		maskText.setText(mask);
		availableHosts.setText(String.valueOf(ipHosts));
	}
}
