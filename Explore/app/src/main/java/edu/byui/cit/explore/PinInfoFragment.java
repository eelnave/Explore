package edu.byui.cit.explore;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PinInfoFragment extends Fragment {
    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIn) {
        return inflater.inflate(R.layout.pin_info_frag, container, false);
    }
}
