package edu.byui.cit.dateapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class About extends InfoFragment {
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container,
               Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);
       String nameAndVersion = getString(R.string.app_name) ;
//               + " " + getString(R.string.versionName); Gets the Version name of the app
        TextView version = view.findViewById(R.id.txtVersion);
        version.setText(nameAndVersion);
        return view;
    }

}
