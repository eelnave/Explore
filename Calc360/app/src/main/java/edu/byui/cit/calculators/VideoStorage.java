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
import edu.byui.cit.units.DataSize;


public final class VideoStorage extends CalcFragment {
	private static final String KEY_STORAGE_SIZE = "VideoStorage.size";
	private final NumberFormat fmtrInt, fmtrDec;

	private EditDecimal space;
	private TextWrapper fourKSixty;
	private TextWrapper fourKThirty;
	private TextWrapper tenEighty;
	private TextWrapper sevenTwenty;


	public VideoStorage() {
		super();
		fmtrInt = NumberFormat.getIntegerInstance();
		fmtrDec = NumberFormat.getInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.video_storage, container, false);

		space = new EditDecimal(view, R.id.space, KEY_STORAGE_SIZE, this);
		fourKSixty = new TextWrapper(view, R.id.fourKSixty);
		fourKThirty = new TextWrapper(view, R.id.fourKThirty);
		tenEighty = new TextWrapper(view, R.id.tenEighty);
		sevenTwenty = new TextWrapper(view, R.id.sevenTwenty);

		EditWrapper[] inputs = { space };
		ControlWrapper[] toClear = {
				space, fourKSixty, fourKThirty, tenEighty, sevenTwenty
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		space.restore(prefs, fmtrDec);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		space.save(editor);
	}


	@Override
	protected void compute() {
		if (space.notEmpty()) {
			double reSpace = space.getDec();
			double asMb = DataSize.getInstance()
					.convert(DataSize.mByte, reSpace, DataSize.gByte);
			int reSevenTwenty = (int)Math.floor(asMb / 1.5 / 60.0);
			int reTenEighty = (int)Math.floor(asMb / 3.33 / 60.0);
			int reFourKThirty = (int)Math.floor(asMb / 5.9 / 30.0);
			int reFourKSixty = (int)Math.floor(asMb / 7.5 / 60.0);

			sevenTwenty.setText(fmtrInt.format(reSevenTwenty));
			tenEighty.setText(fmtrInt.format(reTenEighty));
			fourKThirty.setText(fmtrInt.format(reFourKThirty));
			fourKSixty.setText(fmtrInt.format(reFourKSixty));
		}
		else {
			fourKSixty.clear();
			fourKThirty.clear();
			tenEighty.clear();
			sevenTwenty.clear();
		}
	}
}
