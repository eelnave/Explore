package edu.byui.cit.calc360;

import org.junit.Test;

import static edu.byui.cit.model.Chemistry.*;
import static org.junit.Assert.assertEquals;


public final class ChemistryTest {
	@Test
	public void testIdealGas() {
		double p = 1.8, v = 4.5, n = 2.7, r = 3.6, t = (p * v) / (n * r);
		double delta = 1e-6;
		assertEquals(p, IdealGas.pressure(v, n , r, t), delta);
		assertEquals(v, IdealGas.volume(p, n, r, t), delta);
		assertEquals(n, IdealGas.moles(p, v, r, t), delta);
		assertEquals(r, IdealGas.gasConst(p, v, n, t), delta);
		assertEquals(t, IdealGas.temperature(p, v, n, r), delta);
	}

	@Test
	public void testGasEnergy() {
		double n = 1.8, r = 320.7, t = 3.6, e = 3.0 * n * r * t / 2.0;
		double delta = 1e-6;
		assertEquals(e, GasEnergy.energy(n, r, t), delta);
		assertEquals(n, GasEnergy.moles(r, t, e), delta);
		assertEquals(r, GasEnergy.gasConst(n, t, e), delta);
		assertEquals(t, GasEnergy.temperature(n, r, e), delta);
	}

	@Test
	public void testGasVelocity() {
		double r = 1.8, t = 320.7, m = 12.5, v = Math.sqrt(3.0 * r * t / m);
		double delta = 1e-6;
		assertEquals(v, GasVelocity.velocity(r, t, m), delta);
		assertEquals(r, GasVelocity.gasConst(t, m, v), delta);
		assertEquals(t, GasVelocity.temperature(r, m, v), delta);
		assertEquals(m, GasVelocity.molarMass(r, t, v), delta);
	}
}
