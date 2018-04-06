package edu.byui.cit.dateapp;


import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class DaterCreator extends AppCompatActivity{
    public  static final String TAG = "DaterCreator";


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dater_creator);


        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);
        actBar.setDisplayShowTitleEnabled(false);
        if(savedInstanceState == null) {

            DaterFrag daterFrag = new DaterFrag();
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.add(R.id.fragContainer, daterFrag );
            trans.commit();
            DaterCreatorDBHelper ourDB = new DaterCreatorDBHelper(daterFrag.getContext());

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//		Log.v(TAG, getClass().getSimpleName() + ".onCreateOptionsMenu()");

        // Inflate our menu from the resources by using the menu inflater.
        // TODO WHAT DOES THIS DO
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return true;
    }


    //TODO TEST THIS TO SEE WHAT IT DOES
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
//		Log.v(TAG, getClass().getSimpleName() + ".onOptionsItemSelected()");
        switch (item.getItemId()) {
            case android.R.id.home:
                // Respond to the user pressing the "back/up" button on the
                // action bar in the same way as if the user pressed the
                // left-facing triangle icon on the main android toolbar.
                onBackPressed();
                return true;
//            case R.id.actAbout:
//                if (about == null || about.isDetached()) {
//                    about = new About();
//                    about.setDescripID(1);
//                }
//
//                // Replace whatever is in the fragment_container
//                // view with the About fragment.
//                switchFragment(about);
//
//                // Return true to indicate that this
//                // method handled the item selected event.
//                return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void switchFragment(InfoFragment fragment) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fragContainer, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }

}
