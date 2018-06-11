package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class DisplayFragment extends CITFragment{
    @Override
    protected String getTitle() {
        return "Display";
    }

    @Override

    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_frag, container, false);

        return view;
    }
}
