package edu.byui.cit.explore;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


enum Category {
	None(0),
	Gifts(1);
//	Service(R.drawable.service_icon),
//	Time(R.drawable.time_icon),
//	Touch(R.drawable.touch_icon),
//	Words(R.drawable.words_icon)

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
