package edu.byui.cit.units;

public final class DataSize extends Property {
	public static final int
		bit   = 8001,
		nib   = 8002,
		byt   = 8003,
		hword = 8004,
		word  = 8005,
		dword = 8006,

		kbit = 8011,
		mbit = 8012,
		gbit = 8013,
		tbit = 8014,
		pbit = 8015,
		xbit = 8016,

		kByte = 8021,
		mByte = 8022,
		gByte = 8023,
		tByte = 8024,
		pByte = 8025,
		xByte = 8026;

	private static DataSize singleton;

	public static synchronized DataSize getInstance() {
		if (singleton == null) {
			singleton = new DataSize();
		}
		return singleton;
	}

	private DataSize() {
		super(World.data, "dataSize", new Unit[]{
			new Unit(bit, "bit", "bit", 0),
			new Unit(nib, "nib", "nibble", 2),
			new Unit(byt, "byte", "byte", 3),
			new Unit(hword, "hw", "hword", 4),
			new Unit(word, "w", "word", 5),
			new Unit(dword, "dw", "dword", 6),

			new Unit(kbit, "kb", "kilobit", 10),
			new Unit(mbit, "mb", "megabit", 20),
			new Unit(gbit, "gb", "gigabit", 30),
			new Unit(tbit, "tb", "terabit", 40),
			new Unit(pbit, "pb", "petabit", 50),
			new Unit(xbit, "xb", "exabit", 60),

			new Unit(kByte, "KB", "kilobyte", 13),
			new Unit(mByte, "MB", "megabyte", 23),
			new Unit(gByte, "GB", "gigabyte", 33),
			new Unit(tByte, "TB", "terabyte", 43),
			new Unit(pByte, "PB", "petabyte", 53),
			new Unit(xByte, "XB", "exabyte", 63)
		});
	}


	@Override
	public double convert(Unit to, double quant, Unit from) {
		int bitsFrom = (int)from.getFactor();
		int bitsTo   = (int)to.getFactor();
		int diff = bitsFrom - bitsTo;
		double factor, conv;
		if (diff < 0) {
			factor = 1L << -diff;
			conv = quant / factor;
		}
		else {
			factor = 1L << diff;
			conv = quant * factor;
		}
		return conv;
	}
}
