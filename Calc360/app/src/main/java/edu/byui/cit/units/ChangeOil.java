package edu.byui.cit.units;

public final class ChangeOil extends Property {
	public static final int
			fullSynOil = 3001,
			regularOil = 3002;

	private static ChangeOil singleton;

	public static synchronized ChangeOil getInstance() {
		if (singleton == null) {
			singleton = new ChangeOil();
		}
		return singleton;
	}

	private ChangeOil() {
		super(World.len, "ChangeOil", new Unit[]{
				new Unit(fullSynOil, "fs", "Full Synthetic", 1),
				new Unit(regularOil, "reg", "Regular",1)
		});
	}
}


