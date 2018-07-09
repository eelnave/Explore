package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.byui.cit.widget.CITFragment;

public class Help extends CITFragment {
    @Override
    protected String getTitle() {
        return "Help";
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.help, container,  false);
        String helpTitle = "How to use the Maintenance App";
        TextView title = view.findViewById(R.id.textTitle);
        title.setText(helpTitle);

        return view;
    }
}
