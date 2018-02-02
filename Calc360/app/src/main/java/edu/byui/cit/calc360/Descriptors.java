package edu.byui.cit.calc360;

import java.util.Arrays;

import edu.byui.cit.calculators.*;


final class Descriptors {
	/** The hierarchy of group and calculator descriptors. */
	private static final GroupDescriptor hierarchy =
		new GroupDescriptor(0, R.string.appName, R.mipmap.calc360, new Descriptor[]{
			new GroupDescriptor(307, R.string.shopping, R.drawable.folder_shopping, new CalcDescriptor[]{
				new CalcDescriptor(1017, R.string.salesTax, R.drawable.sales_tax, SalesTax.class),
				new CalcDescriptor(1014, R.string.discount, R.drawable.discount, Discount.class),
				new CalcDescriptor(1001, R.string.cmpPrices, R.drawable.balance, ComparePrices.class),
			}),
			new GroupDescriptor(308, R.string.sports, R.drawable.folder_sports, new CalcDescriptor[]{
				new CalcDescriptor(1011, R.string.basketball, R.drawable.basketball, Basketball.class),
				new CalcDescriptor(1012, R.string.bodyMassIndex, R.drawable.body_mass_index, BodyMassIndex.class),
				new CalcDescriptor(1020, R.string.caloriesBurned, R.drawable.flame, CaloriesBurned.class),
			}),
			new GroupDescriptor(302, R.string.travel, R.drawable.folder_travel, new CalcDescriptor[]{
				new CalcDescriptor(1003, R.string.FuelEffic, R.drawable.fuel_gauge, FuelEfficiency.class),
				new CalcDescriptor(1031, R.string.fuelCost, R.drawable.fuel_cost, FuelCost.class),
				new CalcDescriptor(1026, R.string.foreignFuel, R.drawable.fuel_pump, ForeignFuel.class),
				new CalcDescriptor(1036, R.string.currencyExch, R.drawable.currency_exchange, CurrencyExchange.class),
				new CalcDescriptor(1002, R.string.tip, R.drawable.tip, Tip.class),
				new CalcDescriptor(1060, R.string.fuelReimburse, R.mipmap.miles_driven,MilesDriven.class),
			}),
			new GroupDescriptor(303, R.string.finance, R.drawable.folder_finance, new CalcDescriptor[]{
				new CalcDescriptor(1013, R.string.compoundInterest, R.drawable.compound_interest, CompoundInterest.class),
				new CalcDescriptor(1004, R.string.invest, R.drawable.investment, Investment.class),
				new CalcDescriptor(1005, R.string.loan, R.drawable.loan, Loan.class),
				new CalcDescriptor(1022, R.string.laborCost, R.drawable.labor_cost, LaborCost.class),
			}),
			new GroupDescriptor(311,R.string.cooking, R.drawable.folder_cooking, new CalcDescriptor[]{
				new CalcDescriptor(1052, R.string.recipeMult, R.drawable.recipe_book, RecipeMultiplier.class),
				new CalcDescriptor(1048, R.string.tempConvert, R.drawable.temperature, TemperatureConvert.class),
				new CalcDescriptor(1049, R.string.massConvert, R.drawable.scale, MassConvert.class),
				new CalcDescriptor(1053, R.string.lengthConvert, R.drawable.ruler, LengthConvert.class),
				new CalcDescriptor(1054, R.string.areaConvert, R.drawable.baking_sheet, AreaConvert.class),
				new CalcDescriptor(1055, R.string.volumeConvert, R.drawable.volume, VolumeConvert.class),
				/*
				new CalcDescriptor(?, "Food Amount", R.drawable.food_amount, FoodAmount.class),
				 */
			}),
			new GroupDescriptor(305, R.string.events, R.drawable.folder_events, new CalcDescriptor[]{
				new CalcDescriptor(1033, R.string.dateArith, R.drawable.calendar, DateArith.class),
				new CalcDescriptor(1056, R.string.dateDiff, R.drawable.calendar, DateDiff.class),
				new CalcDescriptor(1007, R.string.counter, R.drawable.abacus, Counter.class),
				new CalcDescriptor(1008, R.string.queueTime, R.drawable.queue, QueueTime.class),
			}),
			new GroupDescriptor(309, R.string.mathematics, R.drawable.folder_math, new CalcDescriptor[]{
				new CalcDescriptor(1029, R.string.percent, R.drawable.percent, Percent.class),
				new CalcDescriptor(1030, R.string.ratio, R.drawable.golden_ratio, Ratio.class),
				new CalcDescriptor(1024, R.string.quadratic, R.drawable.quadratic, Quadratic.class),
				new CalcDescriptor(1028, R.string.modulo, R.drawable.division, Modulo.class),
				new CalcDescriptor(1023, R.string.rightTriangle, R.drawable.right_triangle, RightTriangle.class),
				new CalcDescriptor(1050, R.string.triangle, R.drawable.triangle, Triangle.class),
				new CalcDescriptor(1042, R.string.rectangle, R.drawable.rectangle, Rectangle.class),
				new CalcDescriptor(1037, R.string.sphere, R.drawable.sphere, Sphere.class),
				new CalcDescriptor(1057, R.string.torus, R.drawable.torus, Torus.class),
				new CalcDescriptor(1044, R.string.cylinder, R.drawable.cylinder, Cylinder.class),
				new CalcDescriptor(1046, R.string.cone, R.drawable.cone, Cone.class),
				new CalcDescriptor(1058, R.string.rectPrism, R.drawable.box, RectangularPrism.class),
				new CalcDescriptor(1034, R.string.pyramid, R.drawable.pyramid, Pyramid.class),
				/*
				new CalcDescriptor(?, "GCM and LCM", R.drawable.gcmlcm, GCMandLCM.class),
				 */
			}),
			/*
			new GroupDescriptor(312, "Geometry", R.drawable.geometryfolder, new CalcDescriptor[] {
				new CalcDescriptor(?, "Points", R.drawable.points, Points.class),
				new CalcDescriptor(?, "Circle", R.drawable.circle, Circle.class),
				new CalcDescriptor(?, "Ellipse", R.drawable.ellipse, Ellipse.class),
				new CalcDescriptor(?, "Square", R.drawable.square, Square.class),
				new CalcDescriptor(?, "Rhombus", R.drawable.rhombus, Rhombus.class),
				new CalcDescriptor(?, "Cube", R.drawable.cube, Cube.class),
				new CalcDescriptor(?, "Triangular Prism", R.drawable.triprism, TriPrism.class),
			}), */
			new GroupDescriptor(306, R.string.stats, R.drawable.folder_stats, new CalcDescriptor[]{
				new CalcDescriptor(1009, R.string.meanEtc, R.drawable.mean_etc, MeanEtc.class),
				new CalcDescriptor(1010, R.string.correl, R.drawable.correlation, Correlation.class),
			}),
			new GroupDescriptor(304, R.string.science, R.drawable.folder_science, new CalcDescriptor[]{
				new CalcDescriptor(1040, R.string.NewtonsSecond, R.drawable.motion, NewtonsSecond.class),
				new CalcDescriptor(1043, R.string.pendulum, R.drawable.pendulum, Pendulum.class),
				new CalcDescriptor(1039, R.string.harmonicMotion, R.drawable.spring, HarmonicMotion.class),
				new CalcDescriptor(1051, R.string.Torque, R.drawable.gear, Torque.class),
				new CalcDescriptor(1006, R.string.ohmsLaw, R.drawable.omega, OhmsLaw.class),
				new CalcDescriptor(1035, R.string.coulombsLaw, R.drawable.charges, CoulombsLaw.class),
				new CalcDescriptor(1027, R.string.idealGas, R.drawable.ideal_gas, IdealGas.class),
				new CalcDescriptor(1045, R.string.gasEnergy, R.drawable.gas_energy, GasEnergy.class),
				new CalcDescriptor(1038, R.string.gasVelocity, R.drawable.gas_velocity, GasVelocity.class),
				new CalcDescriptor(1049, R.string.relativity, R.drawable.einstein, Relativity.class),
			}),
			new GroupDescriptor(310, R.string.general, R.drawable.folder_general, new CalcDescriptor[]{
				new CalcDescriptor(1032, R.string.unitConvert, R.drawable.unit_convert, UnitConvert.class),
				new CalcDescriptor(1015, R.string.binaryConvert, R.drawable.binary, Binary.class),
				new CalcDescriptor(1047, R.string.subnet, R.drawable.binary2, Subnet.class),
				new CalcDescriptor(1021, R.string.fiveFunc, R.drawable.five_func, FiveFunction.class),
			}),
		});

	/* Save and restore user selected units in any
	 * two calculators that don't already do that:
	 * PriceCompare, BMI, FuelCost */

	/** An array that holds all group and calculator descriptors. */
	private static Descriptor[] descriptors;

	/** Copies the hierarchy of descriptors into an array so that
	 * we can easily and quickly find a descriptor by its ID. */
	static void initialize() {
		int count = countDescrips(hierarchy) + 1;
		descriptors = new Descriptor[count];
		storeDescrips(descriptors, 0, hierarchy);
		descriptors[count - 1] = new CalcDescriptor(1, R.string.about, R.mipmap.calc360, About.class);
		Arrays.sort(descriptors, Descriptor.compareID);
	}

	/** Counts the number of descriptors in the
	 * hierarchy that includes and is below descrip. */
	private static int countDescrips(Descriptor descrip) {
		int count = 1;
		if (descrip instanceof GroupDescriptor) {
			for (Descriptor child : ((GroupDescriptor)descrip).getChildren()) {
				count += countDescrips(child);
			}
		}
		return count;
	}

	/** Stores in array, the descriptors in the
	 * hierarchy that includes and is below descrip. */
	private static int storeDescrips(Descriptor[] array, int index, Descriptor descrip) {
		// Check for duplicated ID's.
//		for (int i = 0;  i < index;  ++i) {
//			if (array[i].getID() == descrip.getID()) {
//				throw new IllegalArgumentException("duplicated ID: " + descrip.getID());
//			}
//		}

		array[index++] = descrip;
		if (descrip instanceof GroupDescriptor) {
			for (Descriptor child : ((GroupDescriptor)descrip).getChildren()) {
				index = storeDescrips(array, index, child);
			}
		}
		return index;
	}

	/** Finds a descriptor by its ID and returns the descriptor. */
	static Descriptor getDescrip(int descrID) {
		Descriptor key = new CalcDescriptor(descrID, 0, 0, null);
		int index = Arrays.binarySearch(descriptors, key, Descriptor.compareID);
		return index >= 0 ? descriptors[index] : null;
	}

//	static Descriptor getLeftSibling(Descriptor descr) {
//		Descriptor sibling = null;
//		GroupDescriptor parent = getParentR(hierarchy, descr);
//		if (parent != null) {
//			Descriptor[] children = parent.getChildren();
//			int index = indexOf(children, descr);
//			if (--index < 0) {
//				index = children.length - 1;
//			}
//			sibling = children[index];
//		}
//		return sibling;
//	}
//
//	static Descriptor getRightSibling(Descriptor descr) {
//		Descriptor sibling = null;
//		GroupDescriptor parent = getParentR(hierarchy, descr);
//		if (parent != null) {
//			Descriptor[] children = parent.getChildren();
//			int index = indexOf(children, descr);
//			if (--index < 0) {
//				index = children.length - 1;
//			}
//			sibling = children[index];
//		}
//		return sibling;
//	}
//
//	private static GroupDescriptor getParentR(GroupDescriptor hier, Descriptor key) {
//		Descriptor[] children = hier.getChildren();
//		int index = indexOf(children, key);
//		if (index != -1) {
//			return hier;
//		}
//		else {
//			for (Descriptor child : children) {
//				if (child instanceof GroupDescriptor) {
//					return getParentR((GroupDescriptor)child, key);
//				}
//			}
//		}
//		return null;
//	}
//
//	private static int indexOf(Descriptor[] descrips, Descriptor key) {
//		int index = -1;
//		for (int i = 0;  i < descrips.length;  ++i) {
//			if (descrips[i] == key) {
//				index = i;
//				break;
//			}
//		}
//		return index;
//	}
}
