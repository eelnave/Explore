package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.units.DataSize;


public final class VideoStorage extends CalcFragment {
	private static final String KEY_STORAGE_SIZE = "VideoStorage.size";
	private static final double secsPerMin = 60.0;
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
		WidgetWrapper[] toClear = {
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
	protected void compute(WidgetWrapper source) {
		if (space.notEmpty()) {
			double reSpace = space.getDec();
			double asMb = DataSize.getInstance()
					.convert(DataSize.mByte, reSpace, DataSize.gByte);

			// The conversion constants below come from experiments recording
			// video at the various resolutions on both an Apple iPhone and
			// an Android phone.
			int reSevenTwenty = (int)Math.floor(asMb / 1.5 / secsPerMin);
			int reTenEighty = (int)Math.floor(asMb / 3.33 / secsPerMin);
			int reFourKThirty = (int)Math.floor(asMb / 5.9 / secsPerMin);
			int reFourKSixty = (int)Math.floor(asMb / 7.5 / secsPerMin);

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
