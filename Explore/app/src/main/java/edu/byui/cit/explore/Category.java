package edu.byui.cit.explore;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


enum Category {
	None(0),
	Bathroom(R.drawable.icon_bathroom),
	Firewood(R.drawable.icon_firewood),
	Lookout(R.drawable.icon_lookout),
	MountainBike(R.drawable.icon_mountainbike),
	RockClimb(R.drawable.icon_rockclimb),
	SleddingHill(R.drawable.icon_sleddinghill),
	ChristmasTree(R.drawable.icon_christmastree),
	Wildfire(R.drawable.icon_wildfire);

	private final int iconID;
	private BitmapDescriptor icon;

	Category(int iconID) {
		this.iconID = iconID;
	}

	private void loadIcon() {
		if (icon == null && iconID != 0) {
			icon = BitmapDescriptorFactory.fromResource(iconID);
		}
	}

	BitmapDescriptor getIcon() {
		return icon;
	}


	static Category get(int ordinal) {
		return values()[ordinal];
	}

	// Loads the icons that are placed on the map.
	static void loadIcons() {
		for (Category cat : values()) {
			cat.loadIcon();
		}
	}
}
