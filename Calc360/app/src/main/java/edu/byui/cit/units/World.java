package edu.byui.cit.units;

import android.app.Activity;
import android.content.res.Resources;

import edu.byui.cit.calc360.R;


/** World is a singleton that contains units for all physical properties.
 * Several requirements contributed to the design of this units code:
 * 1. It only makes sense to convert among units that measure the same
 *    physical property.
 * 2. It must be easy to generate a list of physical properties and a
 *    list of units for a user to select from.
 * 3. Units must remain organized by physical property.
 * 4. Within a physical property, it must be possible to convert among
 *    units of the same system and different systems.
 */
public final class World extends Container<Property> {
	public static final int
		len   =  1,
		mass  =  2,
		time  =  3,
//		cur   =  4,  // current (amperes)
//		char  =  5,	 // charge (coulombs)
		temp  =  6,
//		lum   =  7,
		area  = 10,
		vol   = 11,
		freq  = 20,
		angle = 21,
//		volt  = 30,
//		resis = 31,
		pres  = 40,
//		ener  = 50,
//		power = 51,
fuelEcon = 52,
		speed = 60,
//		accel = 61,
		force = 62,
		data  = 70,
		money = 71,
		pace = 50;

	private static World singleton;

	public static synchronized World getInstance() {
		if (singleton == null) {
			singleton = new World();
		}
		return singleton;
	}

	private boolean inited = false;

	private World() {
		super(1, "all", "all physical properties", new Property[]{
//			new Property(empty, "empty", new Unit[]{}),
			Length.getInstance(),
			Mass.getInstance(),
			Time.getInstance(),
			Temperature.getInstance(),
			Area.getInstance(),
			Volume.getInstance(),
			Frequency.getInstance(),
			Angle.getInstance(),
			Pressure.getInstance(),
			FuelEcon.getInstance(),
			Speed.getInstance(),
			Force.getInstance(),
			DataSize.getInstance(),
			Money.getInstance()
		});
	}

	public synchronized void initialize(Activity act) {
		if (! inited) {
			// Initialize the localized name for all supported physical
			// properties and corresponding units, so that the localized
			// name can appear in the units drop down lists.
			Resources res = act.getResources();
			String[] supported = res.getStringArray(R.array.supportedProperties);
			for (Property prop : getByName(supported)) {
				prop.initialize(res);
			}

//			((Money)get(money)).getRates(act);
			inited = true;
		}
	}
}
