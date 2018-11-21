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
	Wildfire(R.drawable.icon_wildfire),
	Antelope(R.drawable.icon_antelope),
	Bear(R.drawable.icon_bear),
	Berry(R.drawable.icon_berry),
	BodyOfWater(R.drawable.icon_bodyofwater),
	Camping(R.drawable.icon_camping),
	Deer(R.drawable.icon_deer),
	Elk(R.drawable.icon_elk),
	Fish(R.drawable.icon_fish),
	Hiking(R.drawable.icon_hiking),
	Horse(R.drawable.icon_horse),
	Moose(R.drawable.icon_moose),
	Person(R.drawable.icon_person),
	Waterfall(R.drawable.icon_waterfall2),
	Wolf(R.drawable.icon_wolf);


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
