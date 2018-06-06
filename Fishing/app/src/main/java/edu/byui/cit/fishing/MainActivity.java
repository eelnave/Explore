package edu.byui.cit.fishing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


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

public class MainActivity extends AppCompatActivity {
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
                }

            /*
     * Preparing the list data
     */
            private void prepareListData() {
                    listDataHeader = new ArrayList<String>();
                    listDataChild = new HashMap<String, List<String>>();

                            // Adding child data
                                    listDataHeader.add("Bodies of Water");
                    listDataHeader.add("Species of Choice");
                    listDataHeader.add("Season");

                            // Adding child data
                                    List<String> water = new ArrayList<String>();
                    water.add("Henry's Fork");
                    water.add("Snake River");
                    water.add("Teton River");
                    water.add("Fall River");
                    water.add("Warm River");

                            List<String> species = new ArrayList<String>();
                    species.add("Rainbow Trout");
                    species.add("Brown Trout");
                    species.add("Cutthroat Trout");
                    species.add("Brook Trout");

                            List<String> season = new ArrayList<String>();
                    season.add("Spring");
                    season.add("Summer");
                    season.add("Fall");
                    season.add("Winter");

                            listDataChild.put(listDataHeader.get(0), water); // Header, Child data
                    listDataChild.put(listDataHeader.get(1), species);
                    listDataChild.put(listDataHeader.get(2), season);
               }
}