package edu.byui.cit.calc360;

/** A GroupDescriptor contains Descriptors. */
final class GroupDescriptor extends Descriptor {
	private final Descriptor[] children;

	/**
	 * Params:
	 * @param children - an array of descriptors that are in this group
	 */
	GroupDescriptor(int groupID, int titleID, int iconID, Descriptor[] children) {
		super(groupID, titleID, iconID, GroupFragment.class);
		this.children = children;
	}

	Descriptor[] getChildren() {
		return children;
	}
}
