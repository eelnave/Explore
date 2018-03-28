package edu.byui.cit.dateapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Frisbeedust on 3/28/2018.
 */

public class NewDate extends InfoFragment{
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstState) {
        View view = inflater.inflate(R.layout.new_date, container, false);
        return view;
    }
}
