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
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinInteger;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.text.ControlWrapper;

public class MaxReps extends CalcFragment {

    // variables for user input & formatting
    private static final String KEY_REPS = "Reps";
    private EditDecimal decWieght;
    private TextWrapper maxWeight;
    private SpinInteger numReps;
    private final NumberFormat fmtrDec;


    public MaxReps() {
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
        View view = inflater.inflate(R.layout.maxreps, container, false);

        // Text input
        decWieght = new EditDecimal(view, R.id.decWeight, this);

        // result
        maxWeight = new TextWrapper(view, R.id.oneRep);

        // creates a spinner for number of reps completed
        Activity act = getActivity();
        numReps = new SpinInteger(act, view, R.id.intReps,
                R.array.numReps, KEY_REPS, this);

        // specifies to the super what the inputs and clear options are
        EditWrapper[] inputs = {decWieght};

        ControlWrapper[] toClear = {
                decWieght, maxWeight };

        initialize(view, inputs, R.id.btnClear, toClear);

        return view;
    }

    // activates computing when input is detected
    private final class callCondition implements ClickListener {
        @Override
        public void clicked(View button) {
            boolean selected = (decWieght.notEmpty() && numReps.notEmpty());
            if (selected) {
                callCompute();
            }
        }

    }

    // uses the Fitness module to compute 1 rep maximum
    // Displays result
    @Override
    protected void compute() {
        double weight = decWieght.getDec();
        int reps = numReps.getInt();
        double max = Fitness.repMax(reps, weight);
        maxWeight.setText(fmtrDec.format(max));
    }
}
