package edu.byui.cit.kindness;

public enum Category {
	None,
	Service,
	Gifts,
	Time,
	Touch,
	Words;

	public static Category get(int ordinal) {
		return Category.values()[ordinal];
	}
}
