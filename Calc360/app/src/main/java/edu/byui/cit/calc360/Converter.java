package edu.byui.cit.calc360;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.text.NumberFormat;

import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextChangedHandler;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;


public abstract class Converter extends CalcFragment {
	private final String KEY_TOP, KEY_BOTTOM;
	private final Property property;
	private final int arrayID;
	protected final NumberFormat fmtrDec;
	private SpinUnit spinTop, spinBottom;
	private EditDec decTop, decBottom;

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

		Activity act = getActivity();
		decTop = new EditDec(view, R.id.decTop, new TextChangedHandler() {
			@Override
			public void afterTextChanged(Editable editable) {
				compute(spinBottom, decBottom, spinTop, decTop);
			}
		});

		decBottom = new EditDec(view, R.id.decBottom, new TextChangedHandler() {
			@Override
			public void afterTextChanged(Editable editable) {
				compute(spinTop, decTop, spinBottom, decBottom);
			}
		});

		new ButtonWrapper(view, R.id.btnSwap, new OnClickListener() {
			@Override
			public void onClick(View button) {
				int posTop = spinTop.getSelectedItemPosition();
				String txtTop = decTop.getText();
				spinTop.setSelection(spinBottom.getSelectedItemPosition());
				decTop.setText(decBottom.getText());
				spinBottom.setSelection(posTop);
				decBottom.setText(txtTop);
			}
		});

		spinTop = new SpinUnit(act, view, R.id.spinTop,
				property, arrayID, KEY_TOP, this);
		spinBottom = new SpinUnit(act, view, R.id.spinBottom,
				property, arrayID, KEY_BOTTOM, this);

		// Open the Android system preferences file for Calc360.
		SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);

		// Restore the units chosen by the
		// user when she last used this calculator.
		spinTop.restore(prefs, spinTop.getItemAtPosition(0).getID());
		spinBottom.restore(prefs, spinBottom.getItemAtPosition(1).getID());

		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	@Override
	public void itemSelected(
			AdapterView<?> parent, View view, int position, long id) {
		compute(spinBottom, decBottom, spinTop, decTop);
	}


	// Overload not override, so try and catch is necessary.
	private void compute(SpinUnit spinTo, EditDec decTo,
			SpinUnit spinFrom, EditDec decFrom) {
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


	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write into the preferences file
		// the currencies chosen by the user.
		spinTop.save(editor);
		spinBottom.save(editor);
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			decTop.clear();
			decBottom.clear();
			decTop.requestFocus();
		}
	}
}
