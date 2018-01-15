package edu.byui.cit.calc360;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import edu.byui.cit.units.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public final class TestUnits {
	private static List<Integer> getIDs(Collection<Unit> collect) {
		ArrayList<Integer> list = new ArrayList<>(collect.size());
		for (Unit unit : collect) {
			list.add(unit.getID());
		}
		return list;
	}


	@Test
	public void testGetMethods() {
		Property prop = World.getInstance().get(World.vol);

		Unit flOz = prop.get(Volume.flOz);
		assertEquals("fluidOunce", flOz.getName());


		int[] desired = {Volume.liter, Volume.cuM, Volume.ml};
		List<Unit> actual = prop.get(desired);
		assertEquals(desired.length, actual.size());
		for (int i = 0, len = actual.size(); i < len; ++i) {
			assertEquals(desired[i], actual.get(i).getID());
		}

		List<Unit> all = prop.getAll();
		List<Integer> listExcept = Arrays.asList(Volume.cup, Volume.pint);
		int[] except = {Volume.cup, Volume.pint};
		actual = prop.getExcept(except);
		assertEquals(all.size() - except.length, actual.size());
		List<Integer> allIDs = getIDs(all);
		allIDs.removeAll(listExcept);
		List<Integer> actualIDs = getIDs(actual);
		assertEquals(allIDs, actualIDs);

		// removing a value from the collection returned from getAll
		// doesn't remove the corresponding mapping from the property.
		all.remove(flOz);
		Unit flOz2 = prop.get(Volume.flOz);
		assertNotNull(flOz2);
		assertEquals(flOz.getName(), flOz2.getName());
	}


	@Test
	public void testLength() {
		final double delta = 1e-6;
		Property prop = World.getInstance().get(World.len);
		assertEquals(24, prop.convert(Length.inch, 2, Length.foot), delta);
		assertEquals(72, prop.convert(Length.inch, 2, Length.yard), delta);
		assertEquals(6, prop.convert(Length.foot, 2, Length.yard), delta);
		assertEquals(5280, prop.convert(Length.foot, 1, Length.mile), delta);
		assertEquals(640, prop.convert(Length.rod, 2, Length.mile), delta);

		assertEquals(2, prop.convert(Length.foot, 24, Length.inch), delta);
		assertEquals(2, prop.convert(Length.yard, 72, Length.inch), delta);
		assertEquals(3, prop.convert(Length.yard, 9, Length.foot), delta);
		assertEquals(1, prop.convert(Length.mile, 5280, Length.foot), delta);
		assertEquals(2, prop.convert(Length.mile, 640, Length.rod), delta);

		assertEquals(3.106856, prop.convert(Length.mile, 5, Length.km), delta);
		assertEquals(9.977933, prop.convert(Length.km, 6.2, Length.mile), delta);

		assertEquals(6000, prop.convert(Length.mm, 6, Length.m), delta);
		assertEquals(600, prop.convert(Length.cm, 6, Length.m), delta);
		assertEquals(6, prop.convert(Length.m, 6, Length.m), delta);
		assertEquals(6000, prop.convert(Length.m, 6, Length.km), delta);

		assertEquals(0.006, prop.convert(Length.m, 6, Length.mm), delta);
		assertEquals(0.06, prop.convert(Length.m, 6, Length.cm), delta);
		assertEquals(0.006, prop.convert(Length.km, 6, Length.m), delta);
	}


	@Test
	public void testMass() {
		final double delta = 1e-6;
		Property prop = World.getInstance().get(World.mass);
		assertEquals(437.5, prop.convert(Mass.grain, 1, Mass.ounce), delta);
		assertEquals(32, prop.convert(Mass.ounce, 2, Mass.pound), delta);
		assertEquals(2000, prop.convert(Mass.pound, 1, Mass.shortTon), delta);
		assertEquals(2240, prop.convert(Mass.pound, 1, Mass.longTon), delta);

		assertEquals(0.997903, prop.convert(Mass.kilogram, 2.2, Mass.pound), delta);
		assertEquals(2000, prop.convert(Mass.gram, 2, Mass.kilogram), delta);
		assertEquals(3000, prop.convert(Mass.kilogram, 3, Mass.metricTon), delta);
	}


	@Test
	public void testTime() {
		double delta = 1e-6;
		Property prop = World.getInstance().get(World.time);
		assertEquals(120, prop.convert(Time.sec, 2, Time.min), delta);
		assertEquals(3600, prop.convert(Time.sec, 1, Time.hour), delta);
		assertEquals(24 * 60, prop.convert(Time.min, 1, Time.day), delta);
		assertEquals(21, prop.convert(Time.day, 3, Time.week), delta);

		assertEquals(2, prop.convert(Time.min, 120, Time.sec), delta);
		assertEquals(1, prop.convert(Time.hour, 3600, Time.sec), delta);
		assertEquals(2, prop.convert(Time.day, 2 * 24 * 60, Time.min), delta);
		assertEquals(2, prop.convert(Time.week, 14, Time.day), delta);
	}


	@Test
	public void testTemperature() {
		final double delta = 1e-4;
		Property prop = World.getInstance().get(World.temp);
		assertEquals(10, prop.convert(Temperature.kel, 10, Temperature.kel), delta);
		assertEquals(-273.15, prop.convert(Temperature.cels, 0, Temperature.kel), delta);
		assertEquals(-459.67, prop.convert(Temperature.fahr, 0, Temperature.kel), delta);

		assertEquals(10, prop.convert(Temperature.cels, 10, Temperature.cels), delta);
		assertEquals(273.15, prop.convert(Temperature.kel, 0, Temperature.cels), delta);
		assertEquals(32, prop.convert(Temperature.fahr, 0, Temperature.cels), delta);

		assertEquals(10, prop.convert(Temperature.fahr, 10, Temperature.fahr), delta);
		assertEquals(255.3722, prop.convert(Temperature.kel, 0, Temperature.fahr), delta);
		assertEquals(-17.7778, prop.convert(Temperature.cels, 0, Temperature.fahr), delta);
	}


	@Test
	public void testArea() {
		double delta = 1e-6;
		Property prop = World.getInstance().get(World.area);
		assertEquals(288, prop.convert(Area.sqIn, 2, Area.sqFt), delta);
		assertEquals(18, prop.convert(Area.sqFt, 2, Area.sqYd), delta);
		assertEquals(43560, prop.convert(Area.sqFt, 1, Area.acre), delta);
		assertEquals(640, prop.convert(Area.acre, 1, Area.sqMi), delta);

		assertEquals(20000, prop.convert(Area.sqCm, 2, Area.sqM), delta);
		assertEquals(20000, prop.convert(Area.sqM, 2, Area.hect), delta);
		assertEquals(200, prop.convert(Area.hect, 2, Area.sqKm), delta);
	}


	@Test
	public void testVolume() {
		double delta = 1e-6;
		Property prop = World.getInstance().get(World.vol);
		assertEquals(6, prop.convert(Volume.tsp, 2, Volume.tbsp), delta);
		assertEquals(4, prop.convert(Volume.tbsp, 2, Volume.flOz), delta);
		assertEquals(16, prop.convert(Volume.flOz, 2, Volume.cup), delta);
		assertEquals(4, prop.convert(Volume.cup, 2, Volume.pint), delta);
		assertEquals(4, prop.convert(Volume.pint, 2, Volume.quart), delta);
		assertEquals(8, prop.convert(Volume.quart, 2, Volume.gallon), delta);

		assertEquals(3456, prop.convert(Volume.cuIn, 2, Volume.cuFt), delta);

		assertEquals(2000, prop.convert(Volume.ml, 2, Volume.liter), delta);
		assertEquals(2000, prop.convert(Volume.liter, 2, Volume.cuM), delta);
	}


	@Test
	public void testAngle() {
		double delta = 1e-4;
		Property prop = World.getInstance().get(World.angle);
		assertEquals(57.2958, prop.convert(Angle.deg, 1, Angle.rad), delta);
		assertEquals(63.662, prop.convert(Angle.grad, 1, Angle.rad), delta);
		assertEquals(3437.7468, prop.convert(Angle.min, 1, Angle.rad), delta);

		assertEquals(Math.PI / 2.0, prop.convert(Angle.rad, 90, Angle.deg), delta);
		assertEquals(100, prop.convert(Angle.grad, 90, Angle.deg), delta);
		assertEquals(5400, prop.convert(Angle.min, 90, Angle.deg), delta);

		assertEquals(Math.PI / 4.0, prop.convert(Angle.rad, 50, Angle.grad), delta);
		assertEquals(45, prop.convert(Angle.deg, 50, Angle.grad), delta);
		assertEquals(2700, prop.convert(Angle.min, 50, Angle.grad), delta);

		assertEquals(3.0 * Math.PI / 2.0, prop.convert(Angle.rad, 16200, Angle.min), delta);
		assertEquals(300, prop.convert(Angle.grad, 16200, Angle.min), delta);
		assertEquals(3, prop.convert(Angle.deg, 180, Angle.min), delta);
	}


	@Test
	public void testDataSize() {
		Property prop = World.getInstance().get(World.data);
		final double delta = 1e-6;
		assertEquals(6, prop.convert(DataSize.nib, 24, DataSize.bit), delta);
		assertEquals(5, prop.convert(DataSize.byt, 40, DataSize.bit), delta);
		assertEquals(4, prop.convert(DataSize.hword, 64, DataSize.bit), delta);
		assertEquals(3, prop.convert(DataSize.word, 96, DataSize.bit), delta);
		assertEquals(2, prop.convert(DataSize.dword, 128, DataSize.bit), delta);

		assertEquals(8, prop.convert(DataSize.bit, 2, DataSize.nib), delta);
		assertEquals(24, prop.convert(DataSize.bit, 3, DataSize.byt), delta);
		assertEquals(64, prop.convert(DataSize.bit, 4, DataSize.hword), delta);
		assertEquals(160, prop.convert(DataSize.bit, 5, DataSize.word), delta);
		assertEquals(384, prop.convert(DataSize.bit, 6, DataSize.dword), delta);

		assertEquals(1, prop.convert(DataSize.kByte, 1024, DataSize.byt), delta);
		assertEquals(1, prop.convert(DataSize.mByte, 1024 * 1024, DataSize.byt), delta);
		assertEquals(1, prop.convert(DataSize.gByte, 1024 * 1024 * 1024, DataSize.byt), delta);
		assertEquals(1, prop.convert(DataSize.tByte, 1024L * 1024 * 1024 * 1024, DataSize.byt), delta);
		assertEquals(1, prop.convert(DataSize.pByte, 1024L * 1024 * 1024 * 1024 * 1024, DataSize.byt), delta);

		assertEquals(1, prop.convert(DataSize.kbit, 1024, DataSize.bit), delta);
		assertEquals(1, prop.convert(DataSize.mbit, 1024 * 1024, DataSize.bit), delta);
		assertEquals(1, prop.convert(DataSize.gbit, 1024 * 1024 * 1024, DataSize.bit), delta);
		assertEquals(1, prop.convert(DataSize.tbit, 1024L * 1024 * 1024 * 1024, DataSize.bit), delta);
		assertEquals(1, prop.convert(DataSize.pbit, 1024L * 1024 * 1024 * 1024 * 1024, DataSize.bit), delta);
	}
}
