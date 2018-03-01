package edu.byui.cit.units;


public final class ChangeOil extends Property {
	public static final int
			fullSynOil = 9001,
			regularOil = 9002,
			cityDrive  = 9003,
			highDrive  = 9004;

	private static ChangeOil singleton;

	public static synchronized ChangeOil getInstance() {
		if (singleton == null) {
			singleton = new ChangeOil();
		}
		return singleton;
	}

	private ChangeOil() {
		super(World.oil, "changeOil", new Unit[]{
				new Unit(fullSynOil, "fs", "Full-Synthetic", 1),
				new Unit(regularOil, "reg", "Regular",1),
				new Unit(cityDrive, "city", "City Driving",1),
				new Unit(highDrive,"high","Highway Driving",1)
		});
	}
}


