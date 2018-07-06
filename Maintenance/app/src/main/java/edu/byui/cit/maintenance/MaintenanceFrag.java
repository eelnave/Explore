package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.byui.cit.widget.CITFragment;

public class MaintenanceFrag extends CITFragment{

    private OilFrag fragAct;
    private Button oil;
    private Button Brakes;



    @Override
    protected View createView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstState) {

        View view = inflater.inflate(R.layout.maintenance_frag, container, false);

        // create oil onClickListener
        // prepend "view" to view.findViewById(R.id.oil); because you are outside of MainActivity
        oil = view.findViewById(R.id.Oil);
        oil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragAct == null || fragAct.isDetached()) {
                    fragAct = new OilFrag();
                }
                // hide FAB on fragment fragAct
                //fab.hide();
                //switch to fragment fragAct (for viewing vehicle details)
                switchFragment(fragAct);
            }
        });


        // create brakes onClickListener
        // prepend "view" to view.findViewById(R.id.oil); because you are outside of MainActivity
        brakes = view.findViewById(R.id.Brakes);
        brakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragAct == null || fragAct.isDetached()) {
                    fragAct = new BrakesFrag();
                }
                // hide FAB on fragment fragAct
                //fab.hide();
                //switch to fragment fragAct (for viewing vehicle details)
                switchFragment(fragAct);
            }
        });



        return view;
    }


    //added switchFragment for oil onClickListener
    public void switchFragment(CITFragment fragment) {
        // Replace whatever is in the fragContainer view with
        // fragment, and add the transaction to the back stack so
        // that the user can navigate back.
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fragContainer, fragment, "thing");
        trans.addToBackStack(null);
        trans.commit();
    }


    @Override
    protected String getTitle() {
        return "Maintenance";
    }


}
