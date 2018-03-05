package edu.byui.cit.model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class Computing {

	private Computing() {

	}

	public static final class Subnet {
		private Subnet() {

		}
		/* Checks to see if the subnet is valid by passing it a binary string */
		public static boolean isValidSubnet(String binary) {
			String pattern = "\\A[1]+[0]+\\z";

			Pattern r = Pattern.compile(pattern);

			Matcher m = r.matcher(binary);
			if (m.find()) {
				return true;
			}
			return false;
		}

		/* Will calculate amount of nets from a binary string */
		/* returns -1 if your it's an invalid subnet binary string */
		public static int calculateNets(String binary) {
			int count = 0;
			double nets = 0;

			if (isValidSubnet(binary)) {
				for (int i = 0; i < binary.length(); i++) {
					if (binary.charAt(i) == '1') {
						count++;
					}
					int rem = count % 8;
					nets = Math.pow(2, rem);
				}
				return (int) nets;
			}
			else {
				return -1;
			}
		}

		/* Will calculate amount of hosts from a binary string */
		/* returns -1 if your it's an invalid subnet binary string */
		public static int calculateHosts(String binary) {
			int count = 0;
			double hosts = 0;

			if (isValidSubnet(binary)) {
				for (int i = 0; i < binary.length(); i++) {
					if (binary.charAt(i) == '1') {
						count++;
					}
					hosts = Math.pow(2, (32-count)) - 2;
				}
				return (int) hosts;
			}
			else {
				return -1;
			}
		}

	}


}
