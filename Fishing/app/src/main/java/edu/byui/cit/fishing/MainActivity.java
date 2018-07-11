package edu.byui.cit.fishing;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public String checkedSpecies;
    public String checkedMonth;
    public String checkedWater;




    public static final String TAG = "Fishing";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    TextView tvTest;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button bt_restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        bt_restart=(Button)findViewById(R.id.restart);
        bt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restartIntent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(restartIntent);
            }
        });

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

           @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                 Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();



                //get checked species and store in global variable
                if (listDataHeader.get(groupPosition).equals(listDataHeader.get(0))){
                    checkedSpecies =  listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition) ;
                    // Call method that checks if all categories have values
                    checkCategoriesNotNull(checkedSpecies,checkedMonth,checkedWater);

                    //Collapse Group on click
                    expListView.collapseGroup(0);

                    //Expand Next Group on click
                    expListView.expandGroup(1);

                    ///get checked month and store in global variable
                } else if (listDataHeader.get(groupPosition).equals(listDataHeader.get(1))){
                    checkedMonth = listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition);

                    // Call method that checks if all categories have values
                    checkCategoriesNotNull(checkedSpecies,checkedMonth,checkedWater);

                    //Collapse Group on click
                    expListView.collapseGroup(1);

                    //Expand Next Group on click
                    expListView.expandGroup(2);

                    ///get checked water and store in global variable
                } else if (listDataHeader.get(groupPosition).equals(listDataHeader.get(2))) {
                    checkedWater = listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition) ;
                    //Collapse Group on click
                    expListView.collapseGroup(2);

                    // Call method that checks if all categories have values
                    checkCategoriesNotNull(checkedSpecies,checkedMonth,checkedWater);

                    // Clear expandableListView
                    listDataHeader.clear();

                } else {
                    // do nothing
                }

                return true;
            }
        });
    }

            /*
     * Preparing the list data
     */
            private void prepareListData() {
                    listDataHeader = new ArrayList<String>();
                    listDataChild = new HashMap<String, List<String>>();

                            // Adding child data
                listDataHeader.add("Species");
                listDataHeader.add("Body of Water");
                listDataHeader.add("Month");

                            // Adding child data

                // Stores array values from strings.xml into string list.
                List<String> species = Arrays.asList(getResources().getStringArray(R.array.species));
                List<String> month   = Arrays.asList(getResources().getStringArray(R.array.month));
                List<String> water   = Arrays.asList(getResources().getStringArray(R.array.water));

                listDataChild.put(listDataHeader.get(0), species);
                listDataChild.put(listDataHeader.get(1), water);
                listDataChild.put(listDataHeader.get(2), month);
                 // Header, Child data

               }

               // Check that 3 variables are not null. If not null, call the method to compute fly.
               private void checkCategoriesNotNull(String checkedSpecies, String checkedMonth, String checkedWater){
                if(checkedSpecies != null && checkedMonth != null && checkedWater != null){
                    // Create instance of choices class and set values.
                    Choices selectedChoices = new Choices(checkedSpecies,checkedWater,checkedMonth);
                    String flyOutput = selectedChoices.computeFly(getResources(),checkedMonth,checkedWater);


                   tvTest = (TextView) findViewById(R.id.tvTestOutput);
                    tvTest.setText(flyOutput);

                } else {
                    // do nothing because not all categories have values

                }
               }
}