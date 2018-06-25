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

//    public enum water
//    {
//        UpperHenry,
//        LowerHenry,
//        SnakeRiver,
//        TetonRiver,
//        WarmRiver,
//        FallRiver,
//       /* AshtonRes,
//        IslandParkRes,
//        HenrysLake,
//        RirieRes,
//        WakesideLake,
//        MudLake,
//        PalisadesRes */
//    }
//
//    public enum month{
//        January,
//        February,
//        March,
//        April,
//        May,
//        June,
//        July,
//        August,
//        September,
//        October,
//        November,
//        December
//    }
//    public enum species {
//        Rainbow,
//        Cutthroat,
//        Brown,
//        Brook
//        /* LakeTrout,
//        Kokanee,
//        SmallMouth,
//        LargeMouth */
//
//    }
//
//   // convert species strings to enums
//    public Choices.species speciesStringToEnum(String selectedSpecies){
//
//        Choices.species speciesEnum = species.Rainbow;
//        switch (selectedSpecies) {
//            case "Rainbow Trout":
//                speciesEnum = species.Rainbow;
//                break;
//            case "Cutthroat Trout":
//                speciesEnum = species.Cutthroat;
//                break;
//            case "Brown Trout":
//                speciesEnum = species.Brown;
//                break;
//            case "Brook trout":
//                speciesEnum = species.Brook;
//                break;
//            default:
//                break;
//        }
//        return speciesEnum;
//    }
//
//    //convert water strings to enums
//    public Choices.water waterStringToEnum(String selectedWater){
//
//        Choices.water waterEnum = water.UpperHenry;
//        switch (selectedWater) {
//            case "Upper Henry's Fork":
//                waterEnum = water.UpperHenry;
//                break;
//            case "Lower Henry's Fork":
//                waterEnum = water.LowerHenry;
//                break;
//            case "Snake River":
//                waterEnum = water.SnakeRiver;
//                break;
//            case "Teton River":
//                waterEnum = water.TetonRiver;
//                break;
//            case "Warm River":
//                waterEnum = water.WarmRiver;
//                break;
//            case "Fall River":
//                waterEnum = water.FallRiver;
//                break;
//            default:
//
//                break;
//        }
//        return waterEnum;
//    }
//
//    //convert water strings to enums
//    public Choices.month monthStringToEnum(String selectedMonth){
//
//        Choices.month monthEnum = month.January;
//        switch (selectedMonth) {
//            case "January":
//                monthEnum = month.January;
//                break;
//            case "February":
//                monthEnum = month.February;
//                break;
//            case "March":
//                monthEnum = month.March;
//                break;
//            case "April":
//                monthEnum = month.April;
//                break;
//            case "May":
//                monthEnum = month.May;
//                break;
//            case "June":
//                monthEnum = month.June;
//                break;
//            case "July":
//                monthEnum = month.July;
//                break;
//            case "August":
//                monthEnum = month.August;
//                break;
//            case "September":
//                monthEnum = month.September;
//                break;
//            case "October":
//                monthEnum = month.October;
//                break;
//            case "November":
//                monthEnum = month.November;
//                break;
//            case "December":
//                monthEnum = month.December;
//                break;
//            default:
//
//                break;
//        }
//        return monthEnum;
//    }


    // compute fly output based on water,species, and month
    // String selectedSpecies taken out as parameter temp
    public String computeFly(Resources res, String selectedWater, String selectedMonth){
        //var to hold fly?
        String reccomendedFly = " holder";


    // Upper Henry's selected
        switch(selectedWater){
            case "Upper Henry's Fork":

                //Filter by month
            switch (selectedMonth){
                case "January":
                    reccomendedFly = res.getString(R.string.upperHenrysJan);
                    return reccomendedFly;

                case "February":
                    reccomendedFly = res.getString(R.string.upperHenrysFeb);
                    return reccomendedFly;

                case "March":
                    reccomendedFly = res.getString(R.string.upperHenrysMar);
                    return reccomendedFly;

                case "April":
                    reccomendedFly = res.getString(R.string.upperHenrysApr);
                    return reccomendedFly;

                case "May":
                    reccomendedFly = res.getString(R.string.upperHenrysMay);
                    return reccomendedFly;

                case "June":
                    reccomendedFly = res.getString(R.string.upperHenrysJun);
                    return reccomendedFly;

                case "July":
                    reccomendedFly = res.getString(R.string.upperHenrysJul);
                    return reccomendedFly;

                case "August":
                    reccomendedFly = res.getString(R.string.upperHenrysAug);
                    return reccomendedFly;

                case "September":
                    reccomendedFly = res.getString(R.string.upperHenrysSep);
                    return reccomendedFly;

                case "October":
                    reccomendedFly = res.getString(R.string.upperHenrysOct);
                    return reccomendedFly;

                case "November":
                    reccomendedFly = res.getString(R.string.upperHenrysNov);
                    return reccomendedFly;

                case "December":
                    reccomendedFly = res.getString(R.string.upperHenrysDec);
                    return reccomendedFly;

                default: throw new IllegalArgumentException("Invalid Combination");
            }

            case "Lower Henry's Fork":

                switch (selectedMonth){
                    case "January":
                        reccomendedFly = res.getString(R.string.lowerHenrysJan);
                        return reccomendedFly;

                    case "February":
                        reccomendedFly = res.getString(R.string.lowerHenrysFeb);
                        return reccomendedFly;

                    case "March":
                        reccomendedFly = res.getString(R.string.lowerHenrysMar);
                        return reccomendedFly;

                    case "April":
                        reccomendedFly = res.getString(R.string.lowerHenrysApr);
                        return reccomendedFly;

                    case "May":
                        reccomendedFly = res.getString(R.string.lowerHenrysMay);
                        return reccomendedFly;

                    case "June":
                        reccomendedFly = res.getString(R.string.lowerHenrysJun);
                        return reccomendedFly;

                    case "July":
                        reccomendedFly = res.getString(R.string.lowerHenrysJul);
                        return reccomendedFly;

                    case "August":
                        reccomendedFly = res.getString(R.string.lowerHenrysAug);
                        return reccomendedFly;

                    case "September":
                        reccomendedFly = res.getString(R.string.lowerHenrysSep);
                        return reccomendedFly;

                    case "October":
                        reccomendedFly = res.getString(R.string.lowerHenrysOct);
                        return reccomendedFly;

                    case "November":
                        // WET vs regular ???
                        reccomendedFly = res.getString(R.string.lowerHenrysNovWet);
                        return reccomendedFly;

                    case "December":
                        reccomendedFly = res.getString(R.string.lowerHenrysDec);
                        return reccomendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }
            case "Snake River":

                switch (selectedMonth){
                    case "January":
                        reccomendedFly = res.getString(R.string.snakeRiverJan);
                        return reccomendedFly;

                    case "February":
                        reccomendedFly = res.getString(R.string.snakeRiverFeb);
                        return reccomendedFly;

                    case "March":
                        reccomendedFly = res.getString(R.string.snakeRiverMar);
                        return reccomendedFly;

                    case "April":
                        reccomendedFly = res.getString(R.string.snakeRiverApr);
                        return reccomendedFly;

                    case "May":
                        reccomendedFly = res.getString(R.string.snakeRiverMay);
                        return reccomendedFly;

                    case "June":
                        reccomendedFly = res.getString(R.string.snakeRiverJun);
                        return reccomendedFly;

                    case "July":
                        reccomendedFly = res.getString(R.string.snakeRiverJul);
                        return reccomendedFly;

                    case "August":
                        reccomendedFly = res.getString(R.string.snakeRiverAug);
                        return reccomendedFly;

                    case "September":
                        reccomendedFly = res.getString(R.string.snakeRiverSep);
                        return reccomendedFly;

                    case "October":
                        reccomendedFly = res.getString(R.string.snakeRiverOct);
                        return reccomendedFly;

                    case "November":
                        reccomendedFly = res.getString(R.string.snakeRiverNov);
                        return reccomendedFly;

                    case "December":
                        reccomendedFly = res.getString(R.string.snakeRiverDec);
                        return reccomendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }
            case "Teton River":

                switch (selectedMonth){
                    case "January":
                        reccomendedFly = res.getString(R.string.tetonJan);
                        return reccomendedFly;

                    case "February":
                        reccomendedFly = res.getString(R.string.tetonFeb);
                        return reccomendedFly;

                    case "March":
                        reccomendedFly = res.getString(R.string.tetonMar);
                        return reccomendedFly;

                    case "April":
                        reccomendedFly = res.getString(R.string.tetonApr);
                        return reccomendedFly;

                    case "May":
                        reccomendedFly = res.getString(R.string.tetonMay);
                        return reccomendedFly;

                    case "June":
                        reccomendedFly = res.getString(R.string.tetonJun);
                        return reccomendedFly;

                    case "July":
                        reccomendedFly = res.getString(R.string.tetonJul);
                        return reccomendedFly;

                    case "August":
                        reccomendedFly = res.getString(R.string.tetonAug);
                        return reccomendedFly;

                    case "September":
                        reccomendedFly = res.getString(R.string.tetonSep);
                        return reccomendedFly;

                    case "October":
                        reccomendedFly = res.getString(R.string.tetonOct);
                        return reccomendedFly;

                    case "November":
                        reccomendedFly = res.getString(R.string.tetonNov);
                        return reccomendedFly;

                    case "December":
                        reccomendedFly = res.getString(R.string.tetonDec);
                        return reccomendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }
            case "Warm River":

                switch (selectedMonth){
                    case "January":
                        reccomendedFly = res.getString(R.string.warmJan);
                        return reccomendedFly;

                    case "February":
                        reccomendedFly = res.getString(R.string.warmFeb);
                        return reccomendedFly;

                    case "March":
                        reccomendedFly = res.getString(R.string.warmMar);
                        return reccomendedFly;

                    case "April":
                        reccomendedFly = res.getString(R.string.warmApr);
                        return reccomendedFly;

                    case "May":
                        // WET FLIES VS NOT?
                        reccomendedFly = res.getString(R.string.warmMayWetFlies);
                        return reccomendedFly;

                    case "June":
                        reccomendedFly = res.getString(R.string.warmJun);
                        return reccomendedFly;

                    case "July":
                        reccomendedFly = res.getString(R.string.warmJul);
                        return reccomendedFly;

                    case "August":
                        reccomendedFly = res.getString(R.string.warmAug);
                        return reccomendedFly;

                    case "September":
                        reccomendedFly = res.getString(R.string.warmSep);
                        return reccomendedFly;

                    case "October":
                        reccomendedFly = res.getString(R.string.warmOct);
                        return reccomendedFly;

                    case "November":
                        reccomendedFly = res.getString(R.string.warmNov);
                        return reccomendedFly;

                    case "December":
                        reccomendedFly = res.getString(R.string.warmDec);
                        return reccomendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }

            case "Fall River":

                switch (selectedMonth){
                    case "January":
                        reccomendedFly = res.getString(R.string.fallJan);
                        return reccomendedFly;

                    case "February":
                        reccomendedFly = res.getString(R.string.fallFeb);
                        return reccomendedFly;

                    case "March":
                        reccomendedFly = res.getString(R.string.fallMar);
                        return reccomendedFly;

                    case "April":
                        reccomendedFly = res.getString(R.string.fallApr);
                        return reccomendedFly;

                    case "May":
                        reccomendedFly = res.getString(R.string.fallMay);
                        return reccomendedFly;

                    case "June":
                        reccomendedFly = res.getString(R.string.fallJunWetFlies);
                        return reccomendedFly;

                    case "July":
                        reccomendedFly = res.getString(R.string.fallJul);
                        return reccomendedFly;

                    case "August":
                        reccomendedFly = res.getString(R.string.fallAug);
                        return reccomendedFly;

                    case "September":
                        reccomendedFly = res.getString(R.string.fallSep);
                        return reccomendedFly;

                    case "October":
                        reccomendedFly = res.getString(R.string.fallOct);
                        return reccomendedFly;

                    case "November":
                        reccomendedFly = res.getString(R.string.fallNov);
                        return reccomendedFly;

                    case "December":
                        reccomendedFly = res.getString(R.string.fallDec);
                        return reccomendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }



            default: break;
            }
            return reccomendedFly;

        }






    }


