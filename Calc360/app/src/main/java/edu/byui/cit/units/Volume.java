package edu.byui.cit.units;

public final class Volume extends Property {
	public static final int
		tsp   = 2101,
		tbsp  = 2102,
		flOz  = 2103,
		cup   = 2104,
		pint  = 2105,
		quart = 2106,
		gallon = 2107,
		cuIn  = 2110,
		cuFt  = 2111,
		ml    = 2120,
		liter = 2121,
		cuM   = 2130;

	private static final double
		cf = 35.3147,
		g = 7.48052;
	private static Volume singleton;

	public static synchronized Volume getInstance() {
		if (singleton == null) {
			singleton = new Volume();
		}
		return singleton;
	}

	private Volume() {
		super(World.vol, "volume", new Unit[]{
			new Unit(tsp, "tsp", "teaspoon", cf * g * 4 * 2 * 2 * 8 * 2 * 3),
			new Unit(tbsp, "tbsp", "tablespoon", cf * g * 4 * 2 * 2 * 8 * 2),
			new Unit(flOz, "fl oz", "fluidOunce", cf * g * 4 * 2 * 2 * 8),
			new Unit(cup, "cup", "cup", cf * g * 4 * 2 * 2),
			new Unit(pint, "pt", "pint", cf * g * 4 * 2),
			new Unit(quart, "qt", "quart", cf * g * 4),
			new Unit(gallon, "gallon", "gallon", cf * g),
			new Unit(cuIn, "cu in", "cubicInch", cf * 1728),
			new Unit(cuFt, "cu ft", "cubicFoot", cf),
			new Unit(ml, "ml", "milliliter", 1000.0 * 1000),
			new Unit(liter, "L", "liter", 1000),
			new Unit(cuM, "cu m", "cubicMeter", 1)
		});
	}
}
