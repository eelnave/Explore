package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.byui.cit.widget.CITFragment;

public class MaintenanceFrag extends CITFragment{
    private Button Oil;
    private Button Brakes;


    @Override
    protected View createView(LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstState) {
        return inflater.inflate(R.layout.maintenance_frag, container, false);
    }

    @Override
    protected String getTitle() {
        return "Maintenance";
    }


}
