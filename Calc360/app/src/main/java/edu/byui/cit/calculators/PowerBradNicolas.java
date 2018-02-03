package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;

public final class PowerBradNicolas extends CalcFragment {
    private final NumberFormat fmtrDec;

    public PowerBradNicolas() {
        // Call the constructor in the parent class.
        super();

        fmtrDec = NumberFormat.getInstance();
    }

    private EditDec num1;
    private EditDec num2;
    private TextWrapper output;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this calculator.
        View view = inflater.inflate(R.layout.power_bradley_nicolas, container, false);

        // Get a reference to each of the text fields in this calculator.
        num1 = new EditDec(view, R.id.num1, this);
        num2 = new EditDec(view, R.id.num2, this);

        // Get a reference to the text view
        // where the results will be displayed.
        output = new TextWrapper(view, R.id.output);

        // Set this calculator as the click listener for the clear button.
        EditWrapper[] inputs = { num1, num2 };
        ControlWrapper[] toClear = { num1, num2, output };
        initialize(view, inputs, toClear, R.id.btnClear);
        return view;
    }


    @Override
    protected void compute() {
        if (num1.notEmpty() && num2.notEmpty()) {
            double result = num1.getDec() + num2.getDec();
            output.setText(fmtrDec.format(result));
        }
        else {
            output.clear();
        }
    }
}
