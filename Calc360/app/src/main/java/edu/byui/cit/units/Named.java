package edu.byui.cit.units;

import java.util.Comparator;


public class Named {
	private final int id;
	private final String abbrev, name;
	private String localName;

	Named(int id, String abbrev, String name) {
		this.id = id;
		this.abbrev = abbrev;
		this.name = name;
		this.localName = name;
	}

	public int getID() {
		return id;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public String getName() {
		return name;
	}

	void setLocalName(String name) {
		this.localName = name;
	}

	public String getLocalName() {
		return localName;
	}

	@Override
	public String toString() {
		return localName;
	}

	static final Comparator<Named> byID = new Comparator<Named>() {
		@Override
		public int compare(Named obj1, Named obj2) {
			return obj1.id - obj2.id;
		}
	};

	static final Comparator<Named> byAbbrev = new Comparator<Named>() {
		@Override
		public int compare(Named obj1, Named obj2) {
			return obj1.abbrev.compareTo(obj2.abbrev);
		}
	};

	static final Comparator<Named> byName = new Comparator<Named>() {
		@Override
		public int compare(Named obj1, Named obj2) {
			return obj1.name.compareTo(obj2.name);
		}
	};
}
