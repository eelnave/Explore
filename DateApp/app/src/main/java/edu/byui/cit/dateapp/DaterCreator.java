package edu.byui.cit.dateapp;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DaterCreator extends AppCompatActivity{
    public  static final String TAG = "DaterCreator";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dater_creator);


        if(savedInstanceState == null) {

            DaterFrag daterFrag = new DaterFrag();
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.add(R.id.fragContainer, daterFrag );
            trans.commit();

        }

    }


 // TODO I might need this
//    protected View onCreateView(LayoutInflater inflater, ViewGroup container,
//                              Bundle savedInstState)   {
//        View view = inflater.inflate(R.layout.dater_creator, container, false);
//
//
//        return view;
//    }


    // TODO ASK IF I NEED THIS OR AT LEAST WHAT IS DOES
//    private final class SeeListener implements  View.OnClickListener {
//        InfoFragment fragment;
//        @Override
//        public void onClick(View view) {
//            try{
//                if(fragment == null || fragment.isDetached()) {
//                fra
//            }
//        }
//    }



}
