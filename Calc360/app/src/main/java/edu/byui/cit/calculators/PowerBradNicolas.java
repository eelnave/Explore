package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class PowerBradNicolas extends CalcFragment {
    private final NumberFormat fmtrDec, fmtrCur;

    // Each of these variables is a reference to
    // one of the text fields in this calculator.
    private EditCur curPrice;
    private EditDec decTaxRate;
    private TextWrapper curTaxAmt, curTotal;


    public PowerBradNicolas() {
        // Call the constructor in the parent class.
        super();

        fmtrDec = NumberFormat.getInstance();
        fmtrCur = NumberFormat.getCurrencyInstance();
    }


    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this calculator.
        View view = inflater.inflate(R.layout.power_bradley_nicolas, container, false);

        // Get a reference to each of the text fields in this calculator.
        curPrice = new EditCur(view, R.id.curPrice, this);
        decTaxRate = new EditDec(view, R.id.decTaxRate,
                Calc360.KEY_SALES_TAX_RATE, this);
        curTaxAmt = new TextWrapper(view, R.id.curTaxAmt);
        curTotal = new TextWrapper(view, R.id.curTotal);

        EditWrapper[] inputs = { curPrice, decTaxRate };
        ControlWrapper[] toClear = { curPrice, curTaxAmt, curTotal };
        initialize(view, inputs, toClear, R.id.btnClear);
        return view;
    }


    @Override
    protected void restorePrefs(SharedPreferences prefs) {
        // Get the previous sales tax rate entered by the user if it exits.
        decTaxRate.restore(prefs, fmtrDec);
    }

    @Override
    protected void savePrefs(SharedPreferences.Editor editor) {
        // Write the tax rate entered by the user into the preferences file.
        decTaxRate.save(editor);
    }


    @Override
    protected void compute() {
        if (curPrice.notEmpty() && decTaxRate.notEmpty()) {
            double price = curPrice.getCur();
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
