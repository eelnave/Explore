package edu.byui.cit.calc360;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.text.NumberFormat;

import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.ItemSelectedHandler;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextChangeHandler;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;


public abstract class Converter extends CalcFragment {
	private final String KEY_TOP, KEY_BOTTOM;
	private final Property property;
	private final int arrayID;
	protected final NumberFormat fmtrDec;
	private SpinUnit spinTop, spinBottom;
	private EditDecimal decTop, decBottom;

	protected Converter(String keyPrefix, Property property, int arrayID) {
		this.KEY_TOP = keyPrefix + ".unitTop";
		this.KEY_BOTTOM = keyPrefix + ".unitBottom";
		this.property = property;
		this.arrayID = arrayID;
		this.fmtrDec = NumberFormat.getInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.converter, container, false);

		decTop = new EditDecimal(view, R.id.decTop, new TextChangeHandler() {
			@Override
			public void textChanged(CharSequence s) {
				compute(decBottom, spinBottom, decTop, spinTop);
			}
		});

		decBottom = new EditDecimal(view, R.id.decBottom, new TextChangeHandler() {
			@Override
			public void textChanged(CharSequence s) {
				compute(decTop, spinTop, decBottom, spinBottom);
			}
		});

		new ButtonWrapper(view, R.id.btnSwap, new ClickListener() {
			@Override
			public void clicked(View button) {
				int posTop = spinTop.getSelectedItemPosition();
				String txtTop = decTop.getText();
				spinTop.setSelection(spinBottom.getSelectedItemPosition());
				decTop.setText(decBottom.getText());
				spinBottom.setSelection(posTop);
				decBottom.setText(txtTop);
			}
		});

		Activity act = getActivity();
		ItemSelectedHandler handler = new ItemSelectedHandler() {
			@Override
			public void itemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				compute(decBottom, spinBottom, decTop, spinTop);
			}
		};
		spinTop = new SpinUnit(act, view, R.id.spinTop,
				property, arrayID, KEY_TOP, handler);
		spinBottom = new SpinUnit(act, view, R.id.spinBottom,
				property, arrayID, KEY_BOTTOM, handler);

		EditWrapper[] inputs = { decTop, decBottom };
		initialize(view, inputs, R.id.btnClear, inputs);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinTop.restore(prefs, spinTop.getItemAtPosition(0).getID());
		spinBottom.restore(prefs, spinBottom.getItemAtPosition(1).getID());
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write into the preferences file
		// the currencies chosen by the user.
		spinTop.save(editor);
		spinBottom.save(editor);
	}


	// Overload not override, so try and catch is necessary.
	private void compute(EditDecimal decTo, SpinUnit spinTo,
			EditDecimal decFrom, SpinUnit spinFrom) {
		try {
			if (decFrom.notEmpty()) {
				Unit unitFrom = spinFrom.getSelectedItem();
				Unit unitTo = spinTo.getSelectedItem();
				double from = decFrom.getDec();
				double to = property.convert(unitTo, from, unitFrom);
				decTo.setText(fmtrDec.format(to));
			}
			else {
				decTo.clear();
			}
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}
}
