// kristina and josh new subnet calc
package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public class Subnet extends CalcFragment {
	private EditInteger ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8;
	private TextWrapper availableHosts, numberOfSubnets;


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

		EditWrapper[] inputs = { ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8 };
		ControlWrapper[] toClear = { ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8 };
		initialize(view, inputs, R.id.btnClear, toClear);

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


	/*@Override
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
		}*/

	@Override
	protected void compute() {
		if (ip1.notEmpty() && ip2.notEmpty() && ip3.notEmpty() && ip4.notEmpty() && ip5.notEmpty()
				&& ip6.notEmpty() && ip7.notEmpty() && ip8.notEmpty()) {
			int input1 = ip1.getInt();
			int input2 = ip2.getInt();
			int input3 = ip3.getInt();
			int input4 = ip4.getInt();
			int input5 = ip5.getInt();
			int input6 = ip6.getInt();
			int input7 = ip7.getInt();
			int input8 = ip8.getInt();
			int sub = 0;
			int zero = 0;

			if (input5 >= 255) {
				if (input6 >= 255) {
					if (input7 >= 255) {
						if (input8 >= 255) {

						}
						else {
							if (input8 == 0) {
								zero = 254;
								sub = 1;
							}
							else if (input8 == 128) {
								zero = 126;
								sub = 2;
							}
							else if (input8 == 192) {
								zero = 62;
								sub = 4;
							}
							else if (input8 == 224) {
								zero = 30;
								sub = 8;
							}
							else if (input8 == 240) {
								zero = 14;
								sub = 16;
							}
							else if (input8 == 248) {
								zero = 6;
								sub = 32;
							}
							else if (input8 == 252) {
								zero = 2;
								sub = 64;
							}

						}
					}
					else {
						if (input7 == 0) {
							zero = 65534;
							sub = 1;
						}
						else if (input7 == 128) {
							zero = 32766;
							sub = 2;
						}
						else if (input7 == 192) {
							zero = 16382;
							sub = 4;
						}
						else if (input7 == 224) {
							zero = 8190;
							sub = 8;
						}
						else if (input7 == 240) {
							zero = 4094;
							sub = 16;
						}
						else if (input7 == 248) {
							zero = 2046;
							sub = 32;
						}
						else if (input7 == 252) {
							zero = 1022;
							sub = 64;
						}
						else if (input7 == 254) {
							zero = 510;
							sub = 128;
						}

					}
				}
				else {
					if (input6 == 0) {
						zero = 16777214;
						sub = 1;
					}
					else if (input6 == 128) {
						zero = 8388606;
						sub = 2;
					}
					else if (input6 == 192) {
						zero = 4194302;
						sub = 4;
					}
					else if (input6 == 224) {
						zero = 2097150;
						sub = 8;
					}
					else if (input6 == 240) {
						zero = 1048574;
						sub = 16;
					}
					else if (input6 == 248) {
						zero = 524286;
						sub = 32;
					}
					else if (input6 == 252) {
						zero = 262142;
						sub = 64;
					}
					else if (input6 == 254) {
						zero = 131070;
						sub = 128;
					}
				}
			}
			numberOfSubnets.setText(sub);
			availableHosts.setText(String.valueOf(zero));
		}
		else {
			ip1.clear();
			ip2.clear();
			ip3.clear();
			ip4.clear();
			ip5.clear();
			ip6.clear();
			ip7.clear();
			ip8.clear();
		}
	}
}
