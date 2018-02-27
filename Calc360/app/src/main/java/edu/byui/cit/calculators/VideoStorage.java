package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class VideoStorage extends CalcFragment {
	private final NumberFormat fmtrDec;

	private EditDecimal space;
	private TextWrapper fourKSixty;
	private TextWrapper fourKThirty;
	private TextWrapper tenEighty;
	private TextWrapper sevenTwenty;


	public VideoStorage() {
		super();
		fmtrDec = NumberFormat.getInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.video_storage, container, false);

		space = new EditDecimal(view, R.id.space, this);
		fourKSixty = new TextWrapper(view, R.id.fourKSixty);
		fourKThirty = new TextWrapper(view, R.id.fourKThirty);
		tenEighty = new TextWrapper(view, R.id.tenEighty);
		sevenTwenty = new TextWrapper(view, R.id.sevenTwenty);

		EditWrapper[] inputs = { space };
		ControlWrapper[] toClear = { space, fourKSixty, fourKThirty, tenEighty, sevenTwenty };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		space.restore(prefs, fmtrDec);
	}


	@Override
	protected void compute() {
		if (space.notEmpty()) {
			double reSpace = space.getDec();
			double asMb = reSpace * 1024;
			int reFourKSixty = (int) (asMb / 7.5) / 60;
			int reFourKThirty = (int) (asMb / 5.9) / 60;
			int reTenEighty = (int) (asMb / 3.33) / 60;
			int reSevenTwenty = (int) (asMb / 1.5) / 60;

			String sFourKSixty = reFourKSixty + " mins";
			String sFourKThirty = reFourKThirty + " mins";
			String sTenEighty = reTenEighty + " mins";
			String sSevenTwenty = reSevenTwenty + " mins";

			fourKSixty.setText(sFourKSixty);
			fourKThirty.setText(sFourKThirty);
			tenEighty.setText(sTenEighty);
			sevenTwenty.setText(sSevenTwenty);
		}
		else {
			space.clear();
			fourKSixty.clear();
			fourKThirty.clear();
			tenEighty.clear();
			sevenTwenty.clear();
		}
	}
}
