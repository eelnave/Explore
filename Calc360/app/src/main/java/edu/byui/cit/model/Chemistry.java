package edu.byui.cit.model;

public final class Chemistry {
	private Chemistry() {
	}


	public static final class IdealGas {
		private IdealGas() {
		}

		public static double pressure(double v, double n, double r, double t) {
			return n * r * t / v;
		}

		public static double volume(double p, double n, double r, double t) {
			return n * r * t / p;
		}

		public static double moles(double p, double v, double r, double t) {
			return (p * v) / (r * t);
		}

		public static double gasConst(double p, double v, double n, double t) {
			return (p * v) / (n * t);
		}

		public static double temperature(double p, double v, double n, double r) {
			return (p * v) / (n * r);
		}
	}


	public static final class GasEnergy {
		private GasEnergy() {
		}

		public static double energy(double moles, double gasConst, double temp) {
			// Formula for energy:  E = 3nRT / 2
			return 3.0 * moles * gasConst * temp / 2.0;
		}

		public static double moles(double gasConst, double temp, double energy) {
			// Formula for moles:  m = 2E / 3RT
			return (2.0 * energy) / (3.0 * gasConst * temp);
		}

		public static double gasConst(double moles, double temp, double energy) {
			// Formula for gas constant:  R = 2E / 3nT
			return (2.0 * energy) / (3.0 * moles * temp);
		}

		public static double temperature(double moles, double gasConst, double energy) {
			// Formula for gas temperature:  T = 2E / 3nR
			return (2.0 * energy) / (3.0 * moles * gasConst);
		}
	}


	public static final class GasVelocity {
		private GasVelocity() {
		}

		public static double velocity(double gasConst, double temp, double moles) {
			// Formula for velocity:  v = sqrt(3RT / m)
			return Math.sqrt(3.0 * gasConst * temp / moles);
		}

		public static double gasConst(double temp, double moles, double veloc) {
			// Formula for gas constant:  R = m v^2 / 3T
			return (moles * veloc * veloc) / (3.0 * temp);
		}

		public static double temperature(double gasConst, double moles, double veloc) {
			// Formula for gas temperature:  T = m v^2 / 3R
			return (moles * veloc * veloc) / (3.0 * gasConst);
		}

		public static double molarMass(double gasConst, double temp, double veloc) {
			// Formula for moles:  m = 3RT / v^2
			return (3.0 * gasConst * temp) / (veloc * veloc);
		}
	}
}
