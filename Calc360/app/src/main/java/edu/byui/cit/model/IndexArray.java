package edu.byui.cit.model;

public class IndexArray {
	private long array;
	private final int max;

	public IndexArray(int max) {
		checkIndex(max, 64);
		this.array = 0L;
		this.max = max;
	}

	public void clear() {
		array = 0L;
	}

	public int size() {
		return Long.bitCount(array);
	}

	public void add(int b) {
		checkIndex(b, max);
		array |= (1L << b);
	}

	public void remove(int b) {
		checkIndex(b, max);
		array &= ~(1L << b);
	}

	public boolean contains(int b) {
		checkIndex(b, max);
		return (array & (1L << b)) != 0;
	}

	private static void checkIndex(int index, int limit) {
		if (index < 0 || limit <= index) {
			throw new IllegalArgumentException(Integer.toString(index));
		}
	}

	public long bitset() {
		return array;
	}

	@Override
	public String toString() {
		return Long.bitCount(array) + " " + Long.toBinaryString(array);
	}
}
