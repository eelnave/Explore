package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.EditInteger;
import edu.byui.cit.widget.WidgetWrapper;

public class Mileage extends CITFragment {
    private EditInteger newMileage;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstState) {
        View view = inflater.inflate(R.layout.change_mileage, container, false);

        newMileage = new EditInteger(view, R.id.new_mileage);

        new ButtonWrapper(view, R.id.btn_submit_mileage, new SubmitMileage());

        return view;
    }

    @Override
    protected String getTitle() { return "Maintenance"; }

    private final class SubmitMileage implements ClickListener {
        @Override
        public void clicked(WidgetWrapper source) {
            int mileage = newMileage.getInt();

            // TO:DO Store the value in local storage.
        }
    }

}
