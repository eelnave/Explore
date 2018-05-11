package edu.byui.cit.calc360;

import android.content.res.Resources;

import java.util.Comparator;


/**
 * A Descriptor is either a CalcDescriptor or a GroupDescriptor and stores
 * information about a calculator or a group (folder) of calculators.
 */
class Descriptor {
	private final int id;
	private final int titleID, iconID, explainID;
	private final Class<? extends CITFragment> calcClass;
	private String prefix, title, explain;

	Descriptor(int id) {
		this(id, 0, 0, null, 0);
	}

	/**
	 * Params:
	 * @param id        - ID of this node. Must be unique and stable
	 *                  across all releases of the app
	 * @param titleID   - an ID from R.string
	 * @param iconID    - an ID from R.drawable
	 * @param calcClass - the Java class for the calculator that
*                  should be opened when the user clicks
	 * @param explainID - an ID from R.string
	 */
	Descriptor(int id, int titleID, int iconID,
			Class<? extends CITFragment> calcClass, int explainID) {
		this.id = id;
		this.titleID = titleID;
		this.iconID = iconID;
		this.calcClass = calcClass;
		this.explainID = explainID;
	}

	final int getID() {
		return id;
	}

	final int getIconID() {
		return iconID;
	}

	final Class<? extends CITFragment> getCalcClass() {
		return calcClass;
	}

	final String getPrefsPrefix(Resources res) {
		if (prefix == null) {
			prefix = res.getResourceEntryName(id);
		}
		return prefix;
	}

	final String getTitle(Resources res) {
		if (title == null) {
			title = res.getString(titleID);
		}
		return title;
	}

	final String getExplanation(Resources res) {
		if (explainID != 0 && explain == null) {
			explain = res.getString(explainID);
		}
		return explain;
	}

	@Override
	public String toString() {
		return calcClass.getSimpleName();
	}

	static final Comparator<Descriptor> compareID
			= new Comparator<Descriptor>() {
		@Override
		public int compare(Descriptor one, Descriptor two) {
			return one.id - two.id;
		}
	};
}
