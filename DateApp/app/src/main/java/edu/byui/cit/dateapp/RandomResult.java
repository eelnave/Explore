package edu.byui.cit.dateapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RandomResult extends InfoFragment{
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstState) {
        View view = inflater.inflate(R.layout.random_result, container, false);
        return view;
    }
}