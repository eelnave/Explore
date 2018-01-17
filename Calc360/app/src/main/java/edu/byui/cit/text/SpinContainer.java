package edu.byui.cit.text;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.byui.cit.units.Container;
import edu.byui.cit.units.Named;


abstract class SpinContainer<P extends Named> extends SpinWrapper {
	SpinContainer(View parent, int spinID,
			ItemSelectedListener listener) {
		super(parent, spinID, null, listener);
		spinner.setOnItemSelectedListener(listener);
	}

	/** Initializes a spinner with all the objects in the given container. */
	SpinContainer(Context ctx, View parent, int spinID,
			Container<P> cont, String prefsKey,
			ItemSelectedListener listener) {
		super(parent, spinID, prefsKey, listener);

		// Set the spinner's adapter.
		spinner.setAdapter(makeAdapter(ctx, cont.getAll()));

		spinner.setOnItemSelectedListener(listener);
	}

	/** Initializes a spinner with the objects in the given
	 * container that are listed in the given array. */
	SpinContainer(Context ctx, View parent, int spinID,
			Container<P> cont, int arrayID, String prefsKey,
			ItemSelectedListener listener) {
		super(parent, spinID, prefsKey, listener);

		// Set the spinner's adapter.
		Resources res = ctx.getResources();
		String[] childNames = res.getStringArray(arrayID);
		spinner.setAdapter(makeAdapter(ctx, cont.getByName(childNames)));

		spinner.setOnItemSelectedListener(listener);
	}


	@Override
	public Number getValue() {
		return getSelectedItem().getID();
	}


	@Override
	public void save(SharedPreferences.Editor editor) {
		editor.putInt(prefsKey, getSelectedItem().getID());
	}

	public void restore(SharedPreferences prefs, int defltID) {
		int id = prefs.getInt(prefsKey, defltID);
		setSelectedID(id);
	}

	@Override
	public P getItemAtPosition(int pos) {
		return (P)super.getItemAtPosition(pos);
	}

	@Override
	public P getSelectedItem() {
		return (P)super.getSelectedItem();
	}

	public void setSelectedID(int id) {
		// Linear search to find the default unit.
		int index = -1;
		ArrayAdapter<P> adapter = (ArrayAdapter<P>)spinner.getAdapter();
		for (int i = 0, n = adapter.getCount();  i < n;  ++i) {
			P item = adapter.getItem(i);
			if (item.getID() == id) {
				index = i;
				break;
			}
		}

		// Set the spinner's selected item.
		setSelection(index);
	}


	/** Creates a spinner adapter from a container and the
	 * array ID of a list of the container's children.
	 */
	public ArrayAdapter<P> makeAdapter(
			Context ctx, Container<P> cont, int arrayID) {
		Resources res = ctx.getResources();
		String[] childNames = res.getStringArray(arrayID);
		return makeAdapter(ctx, cont.getByName(childNames));
	}

	/** Creates a spinner adapter from a container. */
	public ArrayAdapter<P> makeAdapter(Context ctx, Container<P> cont) {
		return makeAdapter(ctx, cont.getAll());
	}

	/** Creates a spinner adapter from a list of children. */
	private ArrayAdapter<P> makeAdapter(Context ctx, List<P> listChildren) {
		ArrayAdapter<P> adapter = new ArrayAdapter<>(ctx,
				android.R.layout.simple_spinner_item, listChildren);
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}
}
