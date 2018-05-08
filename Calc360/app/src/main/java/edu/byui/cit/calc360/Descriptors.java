package edu.byui.cit.calc360;

import java.util.Arrays;

import edu.byui.cit.calculators.*;


final class Descriptors {
	/**
	 * The hierarchy of group and calculator descriptors. Each groupID and
	 * calcID must be unique. groupID's begin at 300 and calcID's begin at
	 * 1000.
	 */
	private static final GroupDescriptor hierarchy =
		new GroupDescriptor("Calc360", R.string.appName, R.mipmap.calc360, new Descriptor[]{
			new GroupDescriptor("Shopping", R.string.shopping, R.drawable.folder_shopping, new CalcDescriptor[]{
				new CalcDescriptor(R.string.salesTax, R.drawable.sales_tax, SalesTax.class),
				new CalcDescriptor(R.string.discount, R.drawable.discount, Discount.class),
				new CalcDescriptor(R.string.cmpPrices, R.drawable.balance, ComparePrices.class),
			}),
			new GroupDescriptor("Cooking", R.string.cooking, R.drawable.folder_cooking, new CalcDescriptor[]{
				new CalcDescriptor(R.string.recipeMult, R.drawable.recipe_book, RecipeMult.class),
				new CalcDescriptor(R.string.tempConvert, R.drawable.temperature, TemperatureConvert.class),
				new CalcDescriptor(R.string.massConvert, R.drawable.scale, MassConvert.class),
				new CalcDescriptor(R.string.lengthConvert, R.drawable.ruler, LengthConvert.class),
				new CalcDescriptor(R.string.areaConvert, R.drawable.baking_sheet, AreaConvert.class),
				new CalcDescriptor(R.string.volumeConvert, R.drawable.volume, VolumeConvert.class),
				/*
				new CalcDescriptor("FoodAmount", R.string.foodAmount, R.drawable.food_amount, FoodAmount.class),
				 */
			}),
			new GroupDescriptor("Events", R.string.events, R.drawable.folder_events, new CalcDescriptor[]{
				new CalcDescriptor(R.string.age, R.drawable.birthday_cake, Age.class),
				new CalcDescriptor(R.string.animalAge, R.drawable.dog, AnimalAge.class),
				new CalcDescriptor(R.string.tableCount, R.drawable.table_with_chairs, TableCount.class),
				new CalcDescriptor(R.string.dateArith, R.drawable.calendar, DateArith.class),
				new CalcDescriptor(R.string.dateDiff, R.drawable.calendar, DateDiff.class),
				new CalcDescriptor(R.string.romanNumConvert, R.drawable.roman_num, RomanNumerals.class),
				new CalcDescriptor(R.string.counter, R.drawable.abacus, Counter.class),
				new CalcDescriptor(R.string.queueTime, R.drawable.queue, QueueTime.class),
			}),
			new GroupDescriptor("Fitness", R.string.fitness, R.drawable.folder_sports, new CalcDescriptor[]{
				new CalcDescriptor(R.string.bodyMassIndex, R.drawable.body_mass_index, BodyMassIndex.class),
				new CalcDescriptor(R.string.basalMetabolicRate, R.drawable.lightning, BasalMetabolicRate.class),
				new CalcDescriptor(R.string.caloriesBurned, R.drawable.flame, CaloriesBurned.class),
				new CalcDescriptor(R.string.basketball, R.drawable.basketball, Basketball.class),
				new CalcDescriptor(R.string.pace, R.drawable.lightning, Pace.class),
				new CalcDescriptor(R.string.oneRepMax, R.drawable.barbell, OneRepMax.class),
			}),
			new GroupDescriptor("Travel", R.string.travel, R.drawable.folder_travel, new CalcDescriptor[]{
				new CalcDescriptor(R.string.FuelEcon, R.drawable.fuel_gauge, FuelEconomy.class),
				new CalcDescriptor(R.string.fuelCost, R.drawable.fuel_cost, FuelCost.class),
				new CalcDescriptor(R.string.foreignFuel, R.drawable.fuel_pump, ForeignFuel.class),
				new CalcDescriptor(R.string.travelTime, R.drawable.travel_time, TravelTime.class),
				new CalcDescriptor(R.string.OilChange, R.drawable.oil_change, OilChange.class),
				new CalcDescriptor(R.string.currencyExch, R.drawable.currency_exchange, CurrencyExchange.class),
				new CalcDescriptor(R.string.gratuity, R.drawable.gratuity, Gratuity.class),
			}),
			new GroupDescriptor("Finance", R.string.finance, R.drawable.folder_finance, new CalcDescriptor[]{
				new CalcDescriptor(R.string.compoundInterest, R.drawable.compound_interest, CompoundInterest.class),
				new CalcDescriptor(R.string.invest, R.drawable.investment, Investment.class),
				new CalcDescriptor(R.string.roi, R.drawable.roi, ReturnOnInvest.class),
				new CalcDescriptor(R.string.loan, R.drawable.loan, Loan.class),
				new CalcDescriptor(R.string.houseAff, R.drawable.house, HouseAffordability.class),
				new CalcDescriptor(R.string.laborCost, R.drawable.labor_cost, LaborCost.class),
				new CalcDescriptor(R.string.habitCost, R.drawable.habit_cost, HabitCost.class),
				new CalcDescriptor(R.string.stream, R.drawable.netflix, StreamingCost.class),
				new CalcDescriptor(R.string.tithing, R.drawable.coins, Tithing.class),
			}),
			new GroupDescriptor("Academic", R.string.academic, R.drawable.folder_academic, new CalcDescriptor[]{
				new CalcDescriptor(R.string.gpaCalc, R.drawable.gpa, GPA.class),
			}),
			new GroupDescriptor("Science", R.string.science, R.drawable.folder_science, new CalcDescriptor[]{
				new CalcDescriptor(R.string.NewtonsSecond, R.drawable.motion, NewtonsSecond.class),
				new CalcDescriptor(R.string.pendulum, R.drawable.pendulum, Pendulum.class),
				new CalcDescriptor(R.string.harmonicMotion, R.drawable.spring, HarmonicMotion.class),
				new CalcDescriptor(R.string.Torque, R.drawable.gear, Torque.class),
				new CalcDescriptor(R.string.ohmsLaw, R.drawable.omega, OhmsLaw.class),
				new CalcDescriptor(R.string.electricPower, R.drawable.electric, ElectricPower.class),
				new CalcDescriptor(R.string.electricEnergy, R.drawable.electric, ElectricEnergy.class),
				new CalcDescriptor(R.string.coulombsLaw, R.drawable.charges, CoulombsLaw.class),
				new CalcDescriptor(R.string.idealGas, R.drawable.ideal_gas, IdealGas.class),
				new CalcDescriptor(R.string.gasEnergy, R.drawable.gas_energy, GasEnergy.class),
				new CalcDescriptor(R.string.gasVelocity, R.drawable.gas_velocity, GasVelocity.class),
				new CalcDescriptor(R.string.relativity, R.drawable.einstein, Relativity.class),
			}),
			new GroupDescriptor("Art", R.string.art, R.drawable.folder_art, new CalcDescriptor[]{
				new CalcDescriptor(R.string.videoStorage, R.drawable.film, VideoStorage.class),
				new CalcDescriptor(R.string.starExposure, R.drawable.star_exposure, StarExposure.class),
				new CalcDescriptor(R.string.musicDuration, R.drawable.music_notes, MusicDuration.class),
			}),
			new GroupDescriptor("Math", R.string.mathematics, R.drawable.folder_math, new CalcDescriptor[]{
				new CalcDescriptor(R.string.percent, R.drawable.percent, Percent.class),
				new CalcDescriptor(R.string.fractions, R.drawable.fraction, Fractions.class),
				new CalcDescriptor(R.string.ratio, R.drawable.golden_ratio, Ratio.class),
				new CalcDescriptor(R.string.quadratic, R.drawable.quadratic, Quadratic.class),
				new CalcDescriptor(R.string.modulo, R.drawable.division, Modulo.class),
			}),
			new GroupDescriptor("Geometry", R.string.geometry, R.drawable.folder_geometry, new CalcDescriptor[]{
				new CalcDescriptor(R.string.points, R.drawable.points, Points.class),
				new CalcDescriptor(R.string.rightTriangle, R.drawable.right_triangle, RightTriangle.class),
				new CalcDescriptor(R.string.triangle, R.drawable.triangle, Triangle.class),
				new CalcDescriptor(R.string.rectangle, R.drawable.rectangle, Rectangle.class),
				new CalcDescriptor(R.string.sphere, R.drawable.sphere, Sphere.class),
				new CalcDescriptor(R.string.torus, R.drawable.torus, Torus.class),
				new CalcDescriptor(R.string.cylinder, R.drawable.cylinder, Cylinder.class),
				new CalcDescriptor(R.string.cone, R.drawable.cone, Cone.class),
				new CalcDescriptor(R.string.rectPrism, R.drawable.box, RectPrism.class),
				new CalcDescriptor(R.string.pyramid, R.drawable.pyramid, Pyramid.class),
				/*
				new CalcDescriptor("Points", R.string.points, R.drawable.points, Points.class),
				new CalcDescriptor("Circle", R.string.circle, R.drawable.circle, Circle.class),
				new CalcDescriptor("Ellipse", R.string.ellipse, R.drawable.ellipse, Ellipse.class),
				new CalcDescriptor("Square", R.string.square, R.drawable.square, Square.class),
				new CalcDescriptor("Rhombus", R.string.rhombus, R.drawable.rhombus, Rhombus.class),
				new CalcDescriptor("Cube", R.string.cube, R.drawable.cube, Cube.class),
				new CalcDescriptor("TriPrism", R.string.tri_prism, R.drawable.tri_prism, TriPrism.class),
				*/
			}),
			new GroupDescriptor("Statistics", R.string.stats, R.drawable.folder_stats, new CalcDescriptor[]{
				new CalcDescriptor(R.string.meanEtc, R.drawable.mean_etc, MeanEtc.class),
				new CalcDescriptor(R.string.correl, R.drawable.correlation, Correlation.class),
				new CalcDescriptor(R.string.binDis, R.drawable.normal_distrib, BinDistProb.class),
			}),
			new GroupDescriptor("Computing", R.string.computing, R.drawable.folder_computing, new CalcDescriptor[]{
				new CalcDescriptor(R.string.binaryConvert, R.drawable.binary, Binary.class),
				new CalcDescriptor(R.string.subnet, R.drawable.network, Subnet.class),
				new CalcDescriptor(R.string.passAttack, R.drawable.padlock, PasswordAttack.class),
			}),
			new GroupDescriptor("General", R.string.general, R.drawable.folder_general, new CalcDescriptor[]{
				new CalcDescriptor(R.string.unitConvert, R.drawable.unit_convert, UnitConvert.class),
				new CalcDescriptor(R.string.fiveFunc, R.drawable.five_func, FiveFunction.class),
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
		int count = countDescrips(hierarchy) + 2;
		descriptors = new Descriptor[count];
		storeDescrips(descriptors, 0, hierarchy);
		descriptors[count - 2] = new CalcDescriptor(R.string.about, R.mipmap.calc360, About.class);
		descriptors[count - 1] = new CalcDescriptor(R.string.feedback, R.mipmap.calc360, Feedback.class);
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
		array[index++] = descrip;
		if (descrip instanceof GroupDescriptor) {
			for (Descriptor child : ((GroupDescriptor)descrip).getChildren()) {
				index = storeDescrips(array, index, child);
			}
		}
		return index;
	}

	/** Finds a descriptor by its ID and returns the descriptor. */
	static Descriptor getDescrip(String descrID) {  //int descrID) {
		Descriptor key = new CalcDescriptor(descrID);
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
