package edu.byui.cit.model;

public final class Engineering {
	private Engineering() {

	}

	public static final class ElectricalEnergy {
		private ElectricalEnergy() {
		}

		public static double milliampHour(double volt, double wattHour) {
			// Solve for milliampHour
			return (wattHour * 1000) / volt;
		}

		public static double volt(double milliampHour, double wattHour) {
			// Solve for volt
			return (wattHour * 1000) / milliampHour;
		}

		public static double wattHour(double volt, double milliampHour) {
			// Solve for wattHour
			return (milliampHour * volt) / 1000;
		}
	}
}
