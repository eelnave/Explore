package edu.byui.cit.model;

import java.util.Arrays;


public final class OrderedIndexArray {
	private final byte[] array;
	private final int max;
	private int size;

	public OrderedIndexArray(int cap, int max) {
		checkIndex(max, 64);
		checkIndex(cap, 64);
		this.array = new byte[cap];
		this.max = max;
		this.size = 0;
	}

	public void clear() {
		size = 0;
	}

	public int size() {
		return size;
	}

	public void add(int b) {
		checkIndex(b, max);
		if (! contains(b)) {
			if (size == array.length) {
				--size;
				for (int i = 0;  i < size;  ++i) {
					array[i] = array[i+1];
				}
			}
			array[size++] = (byte)b;
		}
	}

	public void remove(int b) {
		checkIndex(b, max);
		int index = indexOf(b);
		if (index != -1) {
			--size;
			for (int i = index;  i < size;  ++i) {
				array[i] = array[i+1];
			}
		}
	}

	public boolean contains(int b) {
		checkIndex(b, max);
		return indexOf(b) != -1;
	}

	private int indexOf(int key) {
		int index = -1;
		for (int i = 0;  i < size;  ++i) {
			if (array[i] == key) {
				index = i;
				break;
			}
		}
		return index;
	}

	private static void checkIndex(int index, int limit) {
		if (index < 0 || limit <= index) {
			throw new IllegalArgumentException(Integer.toString(index));
		}
	}

	public long bitset() {
		long bits = 0;
		for (int i = 0;  i < size;  ++i) {
			bits |= (1L << array[i]);
		}
		return bits;
	}

	@Override
	public String toString() {
		return size + " " + Arrays.toString(array) + " " + bitset();
	}
}
