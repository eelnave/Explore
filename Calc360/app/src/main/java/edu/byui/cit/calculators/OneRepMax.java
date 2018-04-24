package edu.byui.cit.calculators;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.model.Fitness;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinInteger;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.text.ControlWrapper;


public class OneRepMax extends CalcFragment {
    // variables for user input & formatting
    private static final String KEY_REPS = "Reps";
    private EditDecimal decWeight;
    private TextWrapper maxWeight;
    private SpinInteger numReps;
    private final NumberFormat fmtrDec;


    public OneRepMax() {
        // Will call the constructor in the parent class.
        super();

        // formatting
        fmtrDec = NumberFormat.getNumberInstance();
    }

    // Creates view and interface relationships
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

        // Inflate the layout for this calculator.
        View view = inflater.inflate(R.layout.one_rep_max, container, false);

        // Text input
        decWeight = new EditDecimal(view, R.id.decWeight, this);

        // result
        maxWeight = new TextWrapper(view, R.id.oneRep);

        // creates a spinner for number of reps completed
        Activity act = getActivity();
        numReps = new SpinInteger(act, view, R.id.intReps,
                R.array.numReps, KEY_REPS, this);

        // specifies to the super what the inputs and clear options are
        EditWrapper[] inputs = {decWeight};
        ControlWrapper[] toClear = {decWeight, maxWeight};
        initialize(view, inputs, R.id.btnClear, toClear);
        return view;
    }

    @Override
    protected void compute() {
        if (decWeight.isEmpty()) {
            maxWeight.setText(fmtrDec.format(0));
        } else {
            double weight = decWeight.getDec();
            int reps = numReps.getInt();
            double max = Fitness.repMax(reps, weight);
            maxWeight.setText(fmtrDec.format(max));
        }
    }
}
