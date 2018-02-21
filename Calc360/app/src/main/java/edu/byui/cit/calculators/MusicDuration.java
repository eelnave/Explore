package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class MusicDuration extends CalcFragment {
	private EditInteger totalMeasures;
	private EditInteger beatsPerMinute;
	private EditInteger timeSignature;
	private EditWrapper[] inputs;
	private TextWrapper songTime;


	public MusicDuration() {
		super();
	}

	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.music_duration, container, false);

		totalMeasures = new EditInteger(view, R.id.totalMeasures, this);
		beatsPerMinute = new EditInteger(view, R.id.tempo, this);
		timeSignature = new EditInteger(view, R.id.timeSignature, this);
		songTime = new TextWrapper(view, R.id.songDuration);

		inputs = new EditWrapper[]{ totalMeasures, beatsPerMinute };
		ControlWrapper[] toClear = {
				totalMeasures, beatsPerMinute, timeSignature, songTime
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(inputs)) {
			double measures = totalMeasures.getInt();
			double bpm = beatsPerMinute.getInt();
			double timeSig = timeSignature.getInt();

			int duration = (int)Math.round(timeSig * (measures / bpm) * 60);  // in seconds
			int hour = duration / 3600;
			duration %= 3600;
			int min = duration / 60;
			duration %= 60;
			int sec = duration;

			String songDurText = pad(hour) + ":" + pad(min) + ":" + pad(sec);
			songTime.setText(songDurText);
		}
		else {
			songTime.clear();
		}
	}

	private static String pad(int x) {
		String s = Integer.toString(x);
		return "00".substring(0, 2 - s.length()) + s;
	}
}
