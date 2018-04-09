package edu.byui.cit.dateapp;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DaterFrag extends InfoFragment {
    public static final String TAG = "DaterFrag";

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu, container, false);
        Button randBtn = view.findViewById(R.id.randomBtn);
        Button filterBtn = view.findViewById(R.id.filterBtn);
        Button addBtn = view.findViewById(R.id.addBtn);

        randBtn.setOnClickListener(new RandomListener());
        filterBtn.setOnClickListener(new FilterListener());
        addBtn.setOnClickListener(new AddListener());



        return view;
    }

//    private final class FilterListener implements View.OnClickListener {
//        InfoFragment fragment;
//        @Override
//        public void onClick(View view) {
//            try {
//                if (fragment == null || fragment.isDetached()) {
//                    fragment = FragGroup.class.newInstance();
//                    //this is a random ID I gave it. Why does it need an ID? Beats me.
//                    fragment.setDescripID(1010);
//                }
//            }
//            catch (Exception ex) {
//                // Log.e(KindnessActivity.TAG,
//                //            "cannot instantiate Categories fragment", ex);
//            }
//            switchFragment(fragment);
//        }
//    }
//
//    private final class RandomListener implements View.OnClickListener {
//        InfoFragment fragment;
//        @Override
//        public void onClick(View view) {
//            try {
//                if (fragment == null || fragment.isDetached()) {
//                    fragment = FragGroup.class.newInstance();
//                    //this is a random ID I gave it. Why does it need an ID? Beats me.
//                    fragment.setDescripID(1011);
//                }
//            }
//            catch (Exception ex) {
//                // Log.e(KindnessActivity.TAG,
//                //            "cannot instantiate Categories fragment", ex);
//            }
//            switchFragment(fragment);
//        }
//    }
//
//
    //This method gives functionality for the Add Button
    private final class AddListener implements View.OnClickListener {
        InfoFragment fragment;
        @Override
        public void onClick(View view) {
            try {
                if (fragment == null || fragment.isDetached()) {
                    fragment = NewDate.class.newInstance();
                    //this is a random ID I gave it. Why does it need an ID? Beats me.
                }
            }
            catch (Exception ex) {
                // Log.e(KindnessActivity.TAG,
                //            "cannot instantiate Categories fragment", ex);
            }
            switchFragment(fragment);
        }
    }

    //This method gives functionality for the Filter Button
    private final class FilterListener implements View.OnClickListener {
        InfoFragment fragment;
        @Override
        public void onClick(View view) {
            try {
                if (fragment == null || fragment.isDetached()) {
                    fragment = FilterList.class.newInstance();
                    //this is a random ID I gave it. Why does it need an ID? Beats me.
                }
            }
            catch (Exception ex) {
                // Log.e(KindnessActivity.TAG,
                //            "cannot instantiate Categories fragment", ex);
            }
            switchFragment(fragment);
        }
    }

    private final class RandomListener implements View.OnClickListener {
        InfoFragment fragment;
        @Override
        public void onClick(View view) {
            try {
                if (fragment == null || fragment.isDetached()) {
                    fragment = RandomResult.class.newInstance();
                    //this is a random ID I gave it. Why does it need an ID? Beats me.
                }
            }
            catch (Exception ex) {
                // Log.e(KindnessActivity.TAG,
                //            "cannot instantiate Categories fragment", ex);
            }
            switchFragment(fragment);
        }
    }

    public void switchFragment(InfoFragment fragment) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fragContainer, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }

}