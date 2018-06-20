package com.example.tylerruss.expandablelisttest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

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

        expandableListDetail.put("Species", species);
        expandableListDetail.put("Month", month);
        expandableListDetail.put("Body of Water", water);
        return expandableListDetail;
    }
}