package edu.byui.cit.calc360;

import org.junit.Test;

import edu.byui.cit.model.Physics.*;

import static org.junit.Assert.assertEquals;


public final class PhysicsTest {
	private static final double delta = 1e-6;

	@Test
	public void testNewtonsSecond() {
		double m = 3, a = 2.5, f = m * a;
		assertEquals(f, NewtonsSecond.force(m, a), delta);
		assertEquals(m, NewtonsSecond.mass(f, a), delta);
		assertEquals(a, NewtonsSecond.accel(f, m), delta);
	}

	@Test
	public void testPendulum() {
		double l = 4.5, g = 9.8, t = 2 * Math.PI * Math.sqrt(l / g);
		assertEquals(t, Pendulum.time(l, g), delta);
		assertEquals(l, Pendulum.length(t, g), delta);
		assertEquals(g, Pendulum.gravity(t, l), delta);
	}

	@Test
	public void testHarmonicMotion() {
		double k = 10, m = 40, t = (Math.PI * 2) * Math.sqrt(m / k);
		assertEquals(t, HarmonicMotion.time(m, k), delta);
		assertEquals(m, HarmonicMotion.mass(t, k), delta);
		assertEquals(k, HarmonicMotion.spring(t, m), delta);
	}

	@Test
	public void testTorque() {
		double f = 3, r = 5, th = Math.PI / 2.0, t = f * r * Math.sin(th);
		assertEquals(t, Torque.torque(f, r, th), delta);
		assertEquals(f, Torque.force(t, r, th), delta);
		assertEquals(r, Torque.radius(t, f, th), delta);
		assertEquals(th, Torque.theta(t, f, r), delta);
	}


	@Test
	public void testOhmsLaw() {
		double v = 12, r = 1100, i = v / r;
		assertEquals(v, OhmsLaw.voltage(i, r), delta);
		assertEquals(i, OhmsLaw.current(v, r), delta);
		assertEquals(r, OhmsLaw.resistance(v, i), delta);
	}

	@Test
	public void testCoulombsLaw() {
		double f = 2751291363.4801, q1 = 5, q2 = 3, r = 7;
		double percent = 1e-12;
		assertEquals(f, CoulombsLaw.force(q1, q2, r), f * percent);
		assertEquals(q1, CoulombsLaw.charge(f, q2, r), q1 * percent);
		assertEquals(q2, CoulombsLaw.charge(f, q1, r), q2 * percent);
		assertEquals(r, CoulombsLaw.distance(f, q1, q2), r * percent);
	}
}
