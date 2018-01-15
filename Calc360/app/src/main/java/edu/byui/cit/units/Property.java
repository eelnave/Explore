package edu.byui.cit.units;

import android.content.res.Resources;
import android.util.Log;

import java.util.List;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;


public class Property extends Container<Unit> {
	public Property(int id, String name, Unit[] units) {
		super(id, null, name, units);
	}


	void initialize(Resources res) {
		Property prop = this;
		try {
			String name = prop.getName();
			String localName = res.getString(Calc360.getID(R.string.class, name));
			prop.setLocalName(localName);

			// Get the list of desired units for this physical quantity.
			String[] unitsNames = res.getStringArray(
					Calc360.getID(R.array.class, name));
			List<Unit> units = prop.getByName(unitsNames);
			for (Unit unit : units) {
				try {
					name = unit.getName();
					localName = res.getQuantityString(
							Calc360.getID(R.plurals.class, name), Integer.MAX_VALUE);
					unit.setLocalName(localName);
				}
				catch (Exception ex) {
					Log.e(Calc360.TAG, "exception", ex);
				}
			}
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	public final double convert(int to, double quant, int from) {
		return convert(get(to), quant, get(from));
	}

	public final double convert(int to, double quant, Unit from) {
		return convert(get(to), quant, from);
	}

	public final double convert(Unit to, double quant, int from) {
		return convert(to, quant, get(from));
	}

	public double convert(Unit to, double quant, Unit from) {
		if (to != from) {
			double facFrom = from.getFactor();
			double facTo = to.getFactor();
			quant *= (facTo / facFrom);
		}
		return quant;
	}
}
