package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class PrivacyFragment extends ChildFragment {
    @Override
    protected String getTitle() {
        return "Privacy";
    }

    @Override

    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.privacy_frag, container, false);

        view.findViewById(R.id.btnToMap).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                });

        return view;
    }
}