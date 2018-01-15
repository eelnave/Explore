package edu.byui.cit.units;

public final class Area extends Property {
	public static final int
		sqIn = 2001,
		sqFt = 2002,
		sqYd = 2003,
		acre = 2004,
		sqMi = 2005,
		sqCm = 2010,
		sqM  = 2011,
		hect = 2012,
		sqKm = 2013;

	private static final double sqkm = 2.58999;
	private static Area singleton;

	public static synchronized Area getInstance() {
		if (singleton == null) {
			singleton = new Area();
		}
		return singleton;
	}

	private Area() {
		super(World.area, "area", new Unit[]{
			new Unit(sqIn, "sq in", "squareInch", 640.0 * 4840 * 3 * 3 * 12 * 12),
			new Unit(sqFt, "sq ft", "squareFoot", 640.0 * 4840 * 3 * 3),
			new Unit(sqYd, "sq yd", "squareYard", 640.0 * 4840),
			new Unit(acre, "acre", "acre", 640),
			new Unit(sqMi, "sq mi", "squareMile", 1),
			new Unit(sqCm, "sq cm", "squareCentimeter", sqkm * 100 * 10000 * 10000),
			new Unit(sqM, "sq m", "squareMeter", sqkm * 100 * 10000),
			new Unit(hect, "ha", "hectare", sqkm * 100),
			new Unit(sqKm, "sq km", "squareKilometer", sqkm)
		});
	}
}
