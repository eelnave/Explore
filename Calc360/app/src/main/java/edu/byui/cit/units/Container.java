package edu.byui.cit.units;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public abstract class Container<P extends Named> extends Named {
	private final P[] byID;
	private P[] byAbbrev, byName;

	Container(int id, String abbrev, String name, P[] things) {
		super(id, abbrev, name);
		this.byID = Arrays.copyOf(things, things.length);
		Arrays.sort(byID, Named.byID);
	}


	public P get(int id) {
		int i = Arrays.binarySearch(byID, new Named(id, null, null), Named.byID);
		return i >= 0 ? byID[i] : null;
	}

	public P getByAbbrev(String abbrev) {
		if (byAbbrev == null) {
			byAbbrev = Arrays.copyOf(byID, byID.length);
			Arrays.sort(byAbbrev, Named.byAbbrev);
		}
		int i = Arrays.binarySearch(byAbbrev, new Named(-1, abbrev, null), Named.byAbbrev);
		return i >= 0 ? byAbbrev[i] : null;
	}

	public P getByName(String name) {
		if (byName == null) {
			byName = Arrays.copyOf(byID, byID.length);
			Arrays.sort(byName, Named.byName);
		}
		int i = Arrays.binarySearch(byName, new Named(-1, null, name), Named.byName);
		return i >= 0 ? byName[i] : null;
	}

	public List<P> get(int[] idents) {
		List<P> list = new ArrayList<>(idents.length);
		for (int id : idents) {
			P p = get(id);
			if (p != null) {
				list.add(p);
			}
		}
		return list;
	}

	public List<P> getByName(String[] names) {
		List<P> list = new ArrayList<>(names.length);
		for (String name : names) {
			P p = getByName(name);
			if (p != null) {
				list.add(p);
			}
		}
		return list;
	}

	public List<P> getExcept(int[] idents) {
		List<P> list = new ArrayList<>(byID.length - idents.length);
		Arrays.sort(idents);
		for (P p : byID) {
			if (Arrays.binarySearch(idents, p.getID()) < 0) {
				list.add(p);
			}
		}
		return list;
	}

	public List<P> getAll() {
		ArrayList<P> copy = new ArrayList<>(byID.length);
		Collections.addAll(copy, byID);
		return copy;
	}

	public int[] getAllIDs() {
		int[] ids = new int[byID.length];
		for (int i = 0;  i < ids.length;  ++i) {
			ids[i] = byID[i].getID();
		}
		return ids;
	}
}
