package edu.byui.cit.fishing;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class MainActivity extends Activity {

    public String checkedSpecies;
    public String checkedMonth;
    public String checkedWater;



    public static final String TAG = "Fishing";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

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

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collapsed listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                 Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
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
                    checkedSpecies = listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition);

                    ///get checked month and store in global variable
                } else if (listDataHeader.get(groupPosition).equals(listDataHeader.get(1))){
                    checkedMonth = listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition);

                    ///get checked water and store in global variable
                } else if (listDataHeader.get(groupPosition).equals(listDataHeader.get(2))) {
                    checkedWater = listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition);
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
                listDataHeader.add("Month");
                listDataHeader.add("Body of Water");

                            // Adding child data

                // Stores array values from strings.xml into string list.
                List<String> species = Arrays.asList(getResources().getStringArray(R.array.species));
                List<String> month   = Arrays.asList(getResources().getStringArray(R.array.month));
                List<String> water   = Arrays.asList(getResources().getStringArray(R.array.water));

                listDataChild.put(listDataHeader.get(0), species);
                listDataChild.put(listDataHeader.get(1), month);
                listDataChild.put(listDataHeader.get(2), water); // Header, Child data

               }

               private void checkCategoriesNotNull(String checkedSpecies, String checkedMonth, String checkedWater){
                if(checkedSpecies != null && checkedMonth != null && checkedWater != null){
                    // Create instance of choices class and set values.


                } else {
                    // do nothing because not all categories have values
                }
               }
}