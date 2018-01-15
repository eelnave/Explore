package edu.byui.cit.model;

import java.util.Arrays;


public final class IndexArray {
	private final byte[] array;
//	private int next, size;
	private int size;

	public IndexArray(int cap) {
		array = new byte[cap];
//		next = 0;
		size = 0;
	}

	public void clear() {
//		next = 0;
		size = 0;
	}

	public int size() {
		return size;
	}

	public void add(int b) {
		if (b < 0 || 63 < b) {
			throw new IllegalArgumentException(Integer.toString(b));
		}
		if (! contains(b)) {
			if (size == array.length) {
				--size;
				for (int i = 0;  i < size;  ++i) {
					array[i] = array[i+1];
				}
			}
			array[size++] = (byte)b;
//			array[next] = (byte)b;
//			if (++next == array.length) {
//				next = 0;
//			}
//			if (size < array.length) {
//				++size;
//			}
		}
	}

	public void remove(int b) {
		int index = indexOf(b);
		if (index != -1) {
			--size;
			for (int i = index;  i < size;  ++i) {
				array[i] = array[i+1];
			}
//			next = size;
		}
	}

	public boolean contains(int key) {
		return indexOf(key) != -1;
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

	public long bitset() {
		long bits = 0;
		for (int i = 0;  i < size;  ++i) {
			bits |= bitFromIndex(array[i]);
		}
		return bits;
	}

	// I believe there is a faster way to do this written in
	public static long bitFromIndex(int b) {
		return 1 << b;
	}

	@Override
	public String toString() {
		return size + /*" " + next +*/ " " + Arrays.toString(array) + " " + bitset();
	}
}
