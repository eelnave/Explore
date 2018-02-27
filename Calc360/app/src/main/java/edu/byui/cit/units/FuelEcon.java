package edu.byui.cit.units;

public final class FuelEcon extends Property {
	public final static int
		mpg = 6201,
		kpl = 6202,
		litPer100km = 6203;
	private static final double factor = 2.352144725;

	private static FuelEcon singleton;

	public static synchronized FuelEcon getInstance() {
		if (singleton == null) {
			singleton = new FuelEcon();
		}
		return singleton;
	}

	private FuelEcon() {
		super(World.fuelEcon, "fuelEcon", new Unit[]{
			new EconUnit(mpg, "mpg", "milePerGallon") {
				double convert(double quant, EconUnit from) {
					double f = Double.NaN;
					switch (from.getID()) {
						case litPer100km:
							f = (factor * 100.0) / quant;
							break;
						case kpl:
							f = quant * factor;
							break;
						case mpg:
							f = quant;
							break;
					}
					return f;
				}
			},

			new EconUnit(kpl, "kpl", "kilometerPerLiter") {
				double convert(double quant, EconUnit from) {
					double f = Double.NaN;
					switch (from.getID()) {
						case litPer100km:
							f = 100.0 / quant;
							break;
						case kpl:
							f = quant;
							break;
						case mpg:
							f = quant / factor;
							break;
					}
					return f;
				}
			},

			new EconUnit(litPer100km, "lit/100 km", "literPer100kilometer") {
				double convert(double quant, EconUnit from) {
					double f = Double.NaN;
					switch (from.getID()) {
						case litPer100km:
							f = quant;
							break;
						case kpl:
							f = 100.0 / quant;
							break;
						case mpg:
							f = (factor * 100.0) / quant;
							break;
					}
					return f;
				}
			}
		});
	}

	private static abstract class EconUnit extends Unit {
		EconUnit(int id, String abbrev, String name) {
			super(id, abbrev, name, 0);
		}

		abstract double convert(double quant, EconUnit from);
	}

	@Override
	public double convert(Unit to, double quant, Unit from) {
		return ((EconUnit)to).convert(quant, (EconUnit)from);
	}
}
