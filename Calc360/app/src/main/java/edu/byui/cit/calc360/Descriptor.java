package edu.byui.cit.calc360;

import android.content.res.Resources;

import java.util.Comparator;


/**
 * A Descriptor is either a CalcDescriptor or a GroupDescriptor and stores
 * information about a calculator or a group (folder) of calculators.
 */
abstract class Descriptor {
	private final String id;
	private final int titleID, iconID;
	private final Class<? extends InfoFragment> calcClass;
	private String title;

	/**
	 * Params:
	 * @param id        - ID of this node. Must be unique and stable
	 *                  across all releases of the app
	 * @param titleID   - an ID from R.string
	 * @param iconID    -
	 * @param calcClass - the Java class for the calculator that
	 *                  should be opened when the user clicks
	 *                  this descriptor
	 */
	Descriptor(String id, int titleID, int iconID,
			   Class<? extends InfoFragment> calcClass) {
		this.id = id;
		this.titleID = titleID;
		this.iconID = iconID;
		this.calcClass = calcClass;
	}

	String getID() {
		return id;
	}

	int getIconID() {
		return iconID;
	}

	Class<? extends InfoFragment> getCalcClass() {
		return calcClass;
	}

	String getTitle(Resources res) {
		if (title == null) {
			title = res.getString(titleID);
		}
		return title;
	}

	@Override
	public String toString() {
		return calcClass.getSimpleName();
	}

//	static final Comparator<Descriptor> compareID = new Comparator<Descriptor>() {
//		@Override
//		public int compare(Descriptor ch1, Descriptor ch2) {
//			return ch1.id - ch2.id;
//		}
//	};
	static final Comparator<Descriptor> compareID = new Comparator<Descriptor>() {
		@Override
		public int compare(Descriptor one, Descriptor two) {
			return one.id.compareTo(two.id);
		}
	};
}
