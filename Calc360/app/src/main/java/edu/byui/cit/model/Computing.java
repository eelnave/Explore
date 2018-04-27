package edu.byui.cit.model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class Computing {

	private Computing() {

	}

	public static final class PasswordAttack {
		private PasswordAttack() {

		}

		public static double getKeySpace(int domain, int length) {
			return Math.pow(domain, length);
		}

		public static double getTimeSeconds(double keyspace, int keysSeconds, int machines) {
			return keyspace / (keysSeconds * machines);
		}

		public static double getTimeHours(double keyspace, int keysSeconds, int machines) {
			return getTimeSeconds(keyspace, keysSeconds, machines) / 3600;
		}

		public static double getTimeDays(double keyspace, int keysSeconds, int machines) {
			return getTimeHours(keyspace, keysSeconds, machines) / 24;
		}

		public static double getTimeYears(double keyspace, int keysSeconds, int machines) {
			return getTimeDays(keyspace, keysSeconds, machines) / 365;
		}
	}

	public static final class Subnet {
		private Subnet() {

		}

		public static String getBroadcast(String ip, String subnet) {
			String broadcast = "";
			StringBuilder ipBinary;
			String subnetBinary;

			int count = 0;

			if (isValidSubnet(subnet)) {

				subnetBinary = ipToBinary(subnet);
				ipBinary = new StringBuilder(ipToBinary(ip));

				for (int i = 0; i < subnetBinary.length(); i++) {
					if (subnetBinary.charAt(i) == '1') {
						count++;
					}
				}
				ipBinary = new StringBuilder(ipBinary.substring(0, count));
				int ones = 32 - ipBinary.length();

				for (int i = 0; i < ones; i++) {
					ipBinary.append("1");
				}

				broadcast = binaryToIp(ipBinary.toString());
			}

			return broadcast;
		}

		public static String getNetworkAddress(String ip, String subnet) {
			String networkAddress = "";
			StringBuilder ipBinary;
			String subnetBinary;

			int count = 0;

			if (isValidSubnet(subnet)) {

				subnetBinary = ipToBinary(subnet);
				ipBinary = new StringBuilder(ipToBinary(ip));

				for (int i = 0; i < subnetBinary.length(); i++) {
					if (subnetBinary.charAt(i) == '1') {
						count++;
					}
				}
				ipBinary = new StringBuilder(ipBinary.substring(0, count));
				int zeroes = 32 - ipBinary.length();

				for (int i = 0; i < zeroes; i++) {
					ipBinary.append("0");
				}

				networkAddress = binaryToIp(ipBinary.toString());
			}

			return networkAddress;
		}

		public static String binaryToIp(String binary) {
			String ip;

			if (binary.length() == 32) {
				int oct1 = Integer.parseInt(binary.substring(0,8), 2);
				int oct2 = Integer.parseInt(binary.substring(8,16), 2);
				int oct3 = Integer.parseInt(binary.substring(16,24), 2);
				int oct4 = Integer.parseInt(binary.substring(24), 2);

				ip = oct1 + "." + oct2 + "." + oct3 + "." + oct4;
			}
			else {
				ip = "invalid";
			}

			return ip;
		}

		/* will convert subnet to binary */
		/* like 255.255.255.0 */
		public static String ipToBinary(String ip) {

			StringBuilder binary = new StringBuilder();

			String[] octets = ip.split("\\.");

			for (int i = 0; i < octets.length; i++) {

				octets[i] = Long.toBinaryString(Integer.parseInt(octets[i]));

				if (octets[i].length() != 8) {
					int zeroes = 8 - octets[i].length();
					String tmp = octets[i];
					octets[i] = "";
					for (int j = 0; j < zeroes; j++) {
						octets[i] += "0";
					}
					octets[i] += tmp;
				}
			}

			for (String octet : octets) {
				binary.append(octet);
			}

			return binary.toString();
		}

		/* Checks to see if the subnet is valid by passing it a binary string */
		public static boolean isValidSubnet(String subnet) {
			String binary = ipToBinary(subnet);

			String pattern = "\\A[1]+[0]+\\z";

			Pattern r = Pattern.compile(pattern);

			Matcher m = r.matcher(binary);
			return m.find() && binary.length() == 32;
		}

		/* Will calculate amount of nets from a binary string */
		/* returns -1 if your it's an invalid subnet binary string */
		public static int calculateNets(String subnet) {
			String binary = ipToBinary(subnet);
			int count = 0;
			double nets = 0;

			if (isValidSubnet(subnet)) {
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
		public static int calculateHosts(String subnet) {

			String binary = ipToBinary(subnet);

			int count = 0;
			double hosts = 0;

			if (isValidSubnet(subnet)) {
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
