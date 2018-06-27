package edu.byui.cit.fishing;

import android.content.res.Resources;
import android.widget.Switch;
import android.widget.TextView;
import com.google.android.gms.common.util.Strings;

import java.util.Arrays;
import java.util.List;


public class Choices extends MainActivity {
    private String SelectedSpecies;
    private String SelectedMonth;
    private String SelectedWater;


    public Choices(String selectedSpecies, String selectedWater, String selectedMonth) {
        SelectedSpecies = selectedSpecies;
        SelectedMonth = selectedMonth;
        SelectedWater = selectedWater;
    }

    public String getSelectedSpecies() {
        return SelectedSpecies;
    }

    public void setSelectedSpecies(String selectedSpecies) {
        SelectedSpecies = selectedSpecies;
    }

    public String getSelectedMonth() {
        return SelectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        SelectedMonth = selectedMonth;
    }

    public String getSelectedWater() {
        return SelectedWater;
    }

    public void setSelectedWater(String selectedWater) {
        SelectedWater = selectedWater;
    }

    public enum water
    {
        UpperHenry,
        LowerHenry,
        SnakeRiver,
        TetonRiver,
        WarmRiver,
        FallRiver,
       /* AshtonRes,
        IslandParkRes,
        HenrysLake,
        RirieRes,
        WakesideLake,
        MudLake,
        PalisadesRes */
    }

    public enum month{
        January,
        February,
        March,
        April,
        May,
        June,
        July,
        August,
        September,
        October,
        November,
        December
    }
    public enum species {
        Rainbow,
        Cutthroat,
        Brown,
        Brook
        /* LakeTrout,
        Kokanee,
        SmallMouth,
        LargeMouth */

    }

   // convert species strings to enums
    public Choices.species speciesStringToEnum(String selectedSpecies){

        Choices.species speciesEnum = species.Rainbow;
        switch (selectedSpecies) {
            case "Rainbow Trout":
                speciesEnum = species.Rainbow;
                break;
            case "Cutthroat Trout":
                speciesEnum = species.Cutthroat;
                break;
            case "Brown Trout":
                speciesEnum = species.Brown;
                break;
            case "Brook trout":
                speciesEnum = species.Brook;
                break;
            default:
                break;
        }
        return speciesEnum;
    }

    //convert water strings to enums
    public Choices.water waterStringToEnum(String selectedWater){

        Choices.water waterEnum = water.UpperHenry;
        switch (selectedWater) {
            case "Upper Henry's Fork":
                waterEnum = water.UpperHenry;
                break;
            case "Lower Henry's Fork":
                waterEnum = water.LowerHenry;
                break;
            case "Snake River":
                waterEnum = water.SnakeRiver;
                break;
            case "Teton River":
                waterEnum = water.TetonRiver;
                break;
            case "Warm River":
                waterEnum = water.WarmRiver;
                break;
            case "Fall River":
                waterEnum = water.FallRiver;
                break;
            default:

                break;
        }
        return waterEnum;
    }

    //convert water strings to enums
    public Choices.month monthStringToEnum(String selectedMonth){

        Choices.month monthEnum = month.January;
        switch (selectedMonth) {
            case "January":
                monthEnum = month.January;
                break;
            case "February":
                monthEnum = month.February;
                break;
            case "March":
                monthEnum = month.March;
                break;
            case "April":
                monthEnum = month.April;
                break;
            case "May":
                monthEnum = month.May;
                break;
            case "June":
                monthEnum = month.June;
                break;
            case "July":
                monthEnum = month.July;
                break;
            case "August":
                monthEnum = month.August;
                break;
            case "September":
                monthEnum = month.September;
                break;
            case "October":
                monthEnum = month.October;
                break;
            case "November":
                monthEnum = month.November;
                break;
            case "December":
                monthEnum = month.December;
                break;
            default:

                break;
        }
        return monthEnum;
    }


    // compute fly output based on water,species, and month
    // String selectedSpecies taken out as parameter temp
    public String computeFly(Resources res, String selectedWater, String selectedMonth){
        //var to hold fly?
        String reccomendedFly = " holder";


    // sample case
        switch(selectedWater){
            case "Upper Henry's Fork":

            switch (selectedMonth){
                case "January":
                    reccomendedFly = res.getString(R.string.upperHenrysJan);
                    System.out.println(reccomendedFly);
                    break;



            }
            default: break;
            }
            return reccomendedFly;

        }






    }


