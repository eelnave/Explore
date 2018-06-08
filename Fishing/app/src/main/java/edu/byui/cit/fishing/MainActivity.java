package edu.byui.cit.fishing;

import java.util.ArrayList;
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

        // Listview Group collasped listener
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
                List<String> species = new ArrayList<String>();
                species.add("Rainbow Trout");
                species.add("Brown Trout");
                species.add("Cutthroat Trout");
                species.add("Brook Trout");
                species.add("Lake Trout");
                species.add("Kokanee Salmon");
                species.add("Smallmouth Bass");
                species.add("Largemouth Bass");

                List<String> month = new ArrayList<String>();
                month.add("January");
                month.add("February");
                month.add("March");
                month.add("April");
                month.add("May");
                month.add("June");
                month.add("July");
                month.add("August");
                month.add("September");
                month.add("October");
                month.add("November");
                month.add("December");

                                    List<String> water = new ArrayList<String>();
                    water.add("Upper Henry's Fork");
                    water.add("Lower Henry's Fork");
                    water.add("Snake River");
                    water.add("Teton River");
                    water.add("Fall River");
                    water.add("Warm River");
                    water.add("Henry's Lake");
                    water.add("Island Park Reservoir");
                    water.add("Ashton Reservoir");
                    water.add("Ririe Reservoir");
                    water.add("Wakeside Lake");
                    water.add("Mud Lake");



                listDataChild.put(listDataHeader.get(0), species);
                listDataChild.put(listDataHeader.get(1), month);
                listDataChild.put(listDataHeader.get(2), water); // Header, Child data

               }
}