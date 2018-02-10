package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;

//Song calculation
//get tempo, time signature, no of measures
//multiply time signature (top) * no of measures / tempo (beatsPerMinute)
//display in seconds if less than a minute, minutes if less than an hour, hour otherwise
//do not allow negative inputs
//possibly allow full time or just beats per measure
//cap if needed



public final class SongDuration extends CalcFragment {
	
	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	
	private EditInt totalMeasures;
	private EditInt beatsPerMinute;
	private EditInt timeSignature;
	private TextWrapper songTime;


	public SongDuration() {
		// Call the constructor in the parent class.
		super();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.song_duration, container, false);

		// Get a reference to each of the text fields in this calculator.
		totalMeasures = new EditInt(view, R.id.totalMeasures, this);
		beatsPerMinute = new EditInt(view, R.id.tempo, this);
		timeSignature = new EditInt(view, R.id.timeSignature, this);
		songTime = new TextWrapper(view, R.id.songDuration);

		EditWrapper[] inputs = { totalMeasures, beatsPerMinute };
		ControlWrapper[] toClear = { totalMeasures, beatsPerMinute, timeSignature, songTime};
		initialize(view, inputs, toClear, R.id.btnClear);
		return view;
	}
	
	
	@Override
	protected void compute() {
		if (totalMeasures.notEmpty() && beatsPerMinute.notEmpty()) {
			double measures = totalMeasures.getInt();
			double bpm = beatsPerMinute.getInt();
			double timeSig = timeSignature.getInt();
			//returns song duration in hours, minutes, seconds
			double songDur = timeSig * (measures / bpm);

			int time = (int)(songDur * 60);

			int hour = time/3600;
			time %= 3600;
			int min = time/60;
			time %= 60;
			int sec = time;
			String songDurText = hour + ":" + min + ":" + sec;

			songTime.setText(songDurText);
		}
		else {
			songTime.clear();
		}
	}
}
