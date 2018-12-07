package edu.byui.cit.explore;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class PinInfoFragment extends CITFragment {
    @Override
    protected String getTitle() {
        return getString(R.string.pinInfo);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
       return inflater.inflate(R.layout.pin_info_frag, container, false);
    }
}
