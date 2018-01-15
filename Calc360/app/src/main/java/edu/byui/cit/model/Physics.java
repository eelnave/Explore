package edu.byui.cit.model;

public final class Physics {
	private Physics() {
	}


	public static final class NewtonsSecond {
		private NewtonsSecond() {
		}

		public static double force(double mass, double accel) {
			// Solve for F = ma
			return mass * accel;
		}

		public static double mass(double force, double accel) {
			// Solve for m = F / a
			return force / accel;
		}

		public static double accel(double force, double mass) {
			// Solve for a = F / m
			return force / mass;
		}
	}


	public static final class Pendulum {
		private Pendulum() {
		}

		public static double time(double length, double gravity) {
			return 2.0 * Math.PI * Math.sqrt(length / gravity);
		}

		public static double length(double time, double gravity) {
			double temp = time / (2.0 * Math.PI);
			return temp * temp * gravity;
		}

		public static double gravity(double time, double length) {
			double temp = 2.0 * Math.PI / time;
			return temp * temp * length;
		}
	}


	public static final class HarmonicMotion {
		private HarmonicMotion() {
		}

		public static double time(double mass, double spring) {
			return 2.0 * Math.PI * Math.sqrt(mass / spring);
		}

		public static double mass(double time, double spring) {
			double temp = time / (2.0 * Math.PI);
			return spring * temp * temp;
		}

		public static double spring(double time, double mass) {
			double temp = 2.0 * Math.PI / time;
			return mass * temp * temp;
		}
	}


	public static final class Torque {
		private Torque() {
		}

		public static double torque(double force, double radius, double theta) {
			return force * radius * Math.sin(theta);
		}

		public static double force(double torque, double radius, double theta) {
			return torque / (radius * Math.sin(theta));
		}

		public static double radius(double torque, double force, double theta) {
			return torque / (force * Math.sin(theta));
		}

		public static double theta(double torque, double force, double radius) {
			return Math.asin(torque / (force * radius));
		}
	}


	public static final class OhmsLaw {
		private OhmsLaw() {
		}

		public static double voltage(double cur, double res) {
			return cur * res;
		}

		public static double current(double vol, double res) {
			return vol / res;
		}

		public static double resistance(double vol, double cur) {
			return vol / cur;
		}
	}


	public static final class CoulombsLaw {
		private CoulombsLaw() {
		}

		private static final double ke = 8.9875517873681764e9;

		public static double force(double q1, double q2, double r) {
			// Force formula:  F = ke [(q1 q2) / r^2]
			return (ke * q1 * q2) / (r * r);
		}

		public static double charge(double F, double q, double r) {
			// q formula: q1 = (F * r^2) / (ke * q2)
			return (F * r * r) / (ke * q);
		}

		public static double distance(double F, double q1, double q2) {
			// r formula: r = sqrt((ke * q1 * q2) / f)
			return Math.sqrt((ke * q1 * q2) / F);
		}
	}


	public static final class MassEnergy {
		private MassEnergy() {
		}

		private static final double c2 = Math.pow(299792458, 2);

		public static double energy(double m) {
			// Energy formula:  E = m c^2
			return m * c2;
		}

		public static double mass(double e) {
			// m = E / c^2
			return e / c2;
		}
	}
}
