package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.TextWrapper;

public final class PowerBradNicolas extends CalcFragment {

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
        new ButtonWrapper(view, R.id.btnClear, new ClearHandler());

        return view;
    }


    @Override
    protected void compute() {
        if (num1.notEmpty() && decTaxRate.notEmpty()) {
            double price = num1.getCur();
            double taxRate = decTaxRate.getDec() / 100.0;
            double taxAmt = Consumer.Ratio.amount(taxRate, price);
            double total = Consumer.Ratio.total(taxRate, price);
            curTaxAmt.setText(fmtrCur.format(taxAmt));
            curTotal.setText(fmtrCur.format(total));
        }
        else {
            curTaxAmt.clear();
            curTotal.clear();
        }
    }
}
