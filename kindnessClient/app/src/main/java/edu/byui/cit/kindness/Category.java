package edu.byui.cit.kindness;

public enum Category {
	None,
	Gifts,
	Service,
	Time,
	Touch,
	Words;

	public static Category get(int ordinal) {
		return Category.values()[ordinal];
	}
}
