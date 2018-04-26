package edu.byui.cit.model;

public final class Search {
	private Search() {
	}

	public static boolean contains(String[] array, String key) {
		return linearSearch(array, key) != -1;
	}

	public static int linearSearch(String[] array, String key) {
		int index = -1;
		for (int i = 0;  i < array.length;  ++i) {
			if (array[i].equals(key)) {
				index = i;
				break;
			}
		}
		return index;
	}
}
