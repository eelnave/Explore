package edu.byui.cit.units;

public final class Temperature extends Property {
	public static final int
		kel  = 1501,
		cels = 1502,
		fahr = 1503;

	private static Temperature singleton;

	public static synchronized Temperature getInstance() {
		if (singleton == null) {
			singleton = new Temperature();
		}
		return singleton;
	}

	private Temperature() {
		super(World.temp, "temperature", new TempUnit[]{
			new TempUnit(kel, "Kelvin", "K") {
				@Override
				double convert(double quant, TempUnit from) {
					double k = Double.NaN;
					switch (from.getID()) {
						case kel:
							k = quant;
							break;
						case cels:
							k = quant + 273.15;
							break;
						case fahr:
							k = (quant - 32) * 5 / 9 + 273.15;
							break;
					}
					return k;
				}
			},

			new TempUnit(cels, "Celsius", "C") {
				@Override
				double convert(double quant, TempUnit from) {
					double c = Double.NaN;
					switch (from.getID()) {
						case kel:
							c = quant - 273.15;
							break;
						case cels:
							c = quant;
							break;
						case fahr:
							c = (quant - 32) * 5 / 9;
							break;
					}
					return c;
				}
			},

			new TempUnit(fahr, "Fahrenheit", "F") {
				@Override
				double convert(double quant, TempUnit from) {
					double f = Double.NaN;
					switch (from.getID()) {
						case kel:
							f = (quant - 273.15) * 9 / 5 + 32;
							break;
						case cels:
							f = quant * 9 / 5 + 32;
							break;
						case fahr:
							f = quant;
							break;
					}
					return f;
				}
			}
		});
	}

	private static abstract class TempUnit extends Unit {
		TempUnit(int id, String name, String abbrev) {
			super(id, abbrev, name, 0);
		}

		abstract double convert(double quant, TempUnit from);
	}


	@Override
	public double convert(Unit to, double quant, Unit from) {
		return ((TempUnit)to).convert(quant, (TempUnit)from);
	}
}
