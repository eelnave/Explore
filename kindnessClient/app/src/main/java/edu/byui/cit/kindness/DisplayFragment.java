package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.widget.ItemSelectedListener;
import edu.byui.cit.widget.SpinString;
import edu.byui.cit.widget.SpinWrapper;


public final class DisplayFragment extends CITFragment{
   private SpinString timeSpinner;

    @Override
    protected String getTitle() {
        return "Display";
    }

    @Override

    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_frag, container, false);
        timeSpinner = new SpinString(view, R.id.timeSpinner, new spinnerChange());

        return view;
    }
    private class spinnerChange implements ItemSelectedListener {

        @Override
        public void itemSelected(SpinWrapper source, int pos, long id) {

        }
    }
}
