package edu.byui.cit.explore;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

public class CreatePinFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //onOptionsSelected();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsSelected(MenuItem item){
        switch (item.getItemId()) {

            case R.id.justkidding:

                // Not implemented here
                return false;
            case R.id.create:

                // Do Fragment menu item stuff here
                return true;

            default:
                break;
        }

        return false;
    }
}
