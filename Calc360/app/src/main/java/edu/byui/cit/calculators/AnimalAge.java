package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Set;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public class AnimalAge extends CalcFragment {
	private final HashMap<TextWrapper, Double> animals;
	private final NumberFormat fmtrDec;
	private EditInteger editHuman;
	private ControlWrapper[] toClear;

	public AnimalAge() {
		super();
		animals = new HashMap<>(25);
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMaximumFractionDigits(2);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.animal_age, container, false);

		editHuman = new EditInteger(view, R.id.humanYears, this);

		animals.clear();
		animals.put(new TextWrapper(view, R.id.bearYears), 2.0);
		animals.put(new TextWrapper(view, R.id.catYears), 3.2);
		animals.put(new TextWrapper(view, R.id.chickenYears), 5.33);
		animals.put(new TextWrapper(view, R.id.cowYears), 3.64);
		animals.put(new TextWrapper(view, R.id.deerYears), 2.29);
		animals.put(new TextWrapper(view, R.id.dogYears), 3.64);
		animals.put(new TextWrapper(view, R.id.donkeyYears), 1.78);
		animals.put(new TextWrapper(view, R.id.duckYears), 4.21);
		animals.put(new TextWrapper(view, R.id.elephantYears), 1.14);
		animals.put(new TextWrapper(view, R.id.foxYears), 5.71);
		animals.put(new TextWrapper(view, R.id.goatYears), 5.33);
		animals.put(new TextWrapper(view, R.id.groundhogYears), 5.71);
		animals.put(new TextWrapper(view, R.id.guineaYears), 10.0);
		animals.put(new TextWrapper(view, R.id.hamsterYears), 20.0);
		animals.put(new TextWrapper(view, R.id.hippoYears), 1.78);
		animals.put(new TextWrapper(view, R.id.horseYears), 2.0);
		animals.put(new TextWrapper(view, R.id.kangarooYears), 8.89);
		animals.put(new TextWrapper(view, R.id.lionYears), 2.29);
		animals.put(new TextWrapper(view, R.id.monkeyYears), 3.2);
		animals.put(new TextWrapper(view, R.id.mouseYears), 20.0);
		animals.put(new TextWrapper(view, R.id.paraYears), 4.44);
		animals.put(new TextWrapper(view, R.id.pigYears), 3.2);
		animals.put(new TextWrapper(view, R.id.pigeonYears), 7.27);
		animals.put(new TextWrapper(view, R.id.rabbitYears), 8.89);
		animals.put(new TextWrapper(view, R.id.ratYears), 26.67);
		animals.put(new TextWrapper(view, R.id.sheepYears), 5.33);
		animals.put(new TextWrapper(view, R.id.squiYears), 5.0);
		animals.put(new TextWrapper(view, R.id.wolfYears), 4.44);

		EditWrapper[] inputs = { editHuman };
		Set<TextWrapper> keys = animals.keySet();
		toClear = new ControlWrapper[inputs.length + keys.size()];
		System.arraycopy(inputs, 0, toClear, 0, inputs.length);
		int i = inputs.length;
		for (TextWrapper key : keys) {
			toClear[i++] = key;
		}
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		if (editHuman.notEmpty()) {
			int human = editHuman.getInt();
			for (TextWrapper key : animals.keySet()) {
				double ratio = animals.get(key);
				double result = human * ratio;
				result = Math.round(result * 4.0) / 4.0;
				key.setText(fmtrDec.format(result));
			}
		}
		else {
			clearAll(toClear);
		}
	}
}
