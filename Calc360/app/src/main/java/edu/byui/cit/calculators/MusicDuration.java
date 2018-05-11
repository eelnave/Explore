package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Art;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditInteger;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;


public final class MusicDuration extends CalcFragment {
	private EditInteger totalMeasures;
	private EditInteger beatsPerMinute;
	private EditInteger timeSignature;
	private EditWrapper[] inputs;
	private TextWrapper musicDuration;


	public MusicDuration() {
		super();
	}

	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.music_duration, container, false);

		totalMeasures = new EditInteger(view, R.id.totalMeasures, this);
		beatsPerMinute = new EditInteger(view, R.id.tempo, this);
		timeSignature = new EditInteger(view, R.id.timeSignature, this);
		musicDuration = new TextWrapper(view, R.id.musicDuration);

		inputs = new EditWrapper[]{ totalMeasures, beatsPerMinute };
		WidgetWrapper[] toClear = {
				totalMeasures, beatsPerMinute, timeSignature, musicDuration
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		if (EditWrapper.allNotEmpty(inputs)) {
			double measures = totalMeasures.getInt();
			double bpm = beatsPerMinute.getInt();
			double timeSig = timeSignature.getInt();

			int duration = Art.MusicDuration.calculateSongDuration(measures, bpm, timeSig);
			int hour = Art.MusicDuration.calculateHours(duration);
			int min = Art.MusicDuration.calculateMinutes(duration);
			int sec = Art.MusicDuration.calculateSeconds(duration);

			String durationText = pad(hour) + ":" + pad(min) + ":" + pad(sec);
			musicDuration.setText(durationText);
		}
		else {
			musicDuration.clear();
		}
	}

	private static String pad(int x) {
		String s = Integer.toString(x);
		return "00".substring(0, 2 - s.length()) + s;
	}
}
