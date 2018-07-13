package edu.byui.cit.fishing;

import android.content.res.Resources;



public class Choices extends MainActivity {
    String SelectedSpecies;
    String SelectedMonth;
    String SelectedWater;


    public Choices(String selectedSpecies, String selectedWater, String selectedMonth) {
        SelectedSpecies = selectedSpecies;
        SelectedMonth = selectedMonth;
        SelectedWater = selectedWater;
    }

//    public String getSelectedSpecies() {
//        return SelectedSpecies;
//    }
//
//    public void setSelectedSpecies(String selectedSpecies) {
//        SelectedSpecies = selectedSpecies;
//    }
//
//    public String getSelectedMonth() {
//        return SelectedMonth;
//    }
//
//    public void setSelectedMonth(String selectedMonth) {
//        SelectedMonth = selectedMonth;
//    }
//
//    public String getSelectedWater() {
//        return SelectedWater;
//    }
//
//    public void setSelectedWater(String selectedWater) {
//        SelectedWater = selectedWater;
//    }

    // compute fly output based on water,species, and month
    // String selectedSpecies taken out as parameter temp
    public String computeFly(Resources res, String selectedWater, String selectedMonth){
        //var to hold fly?
        String recommendedFly = " holder";


    // Upper Henry's selected
        switch(selectedWater){
            case "Upper Henry's Fork":

                //Filter by month
            switch (selectedMonth){
                case "January":
                    recommendedFly = res.getString(R.string.upperHenrysJan);
                    return recommendedFly;

                case "February":
                    recommendedFly = res.getString(R.string.upperHenrysFeb);
                    return recommendedFly;

                case "March":
                    recommendedFly = res.getString(R.string.upperHenrysMar);
                    return recommendedFly;

                case "April":
                    recommendedFly = res.getString(R.string.upperHenrysApr);
                    return recommendedFly;

                case "May":
                    recommendedFly = res.getString(R.string.upperHenrysMay);
                    return recommendedFly;

                case "June":
                    recommendedFly = res.getString(R.string.upperHenrysJun);
                    return recommendedFly;

                case "July":
                    recommendedFly = res.getString(R.string.upperHenrysJul);
                    return recommendedFly;

                case "August":
                    recommendedFly = res.getString(R.string.upperHenrysAug);
                    return recommendedFly;

                case "September":
                    recommendedFly = res.getString(R.string.upperHenrysSep);
                    return recommendedFly;

                case "October":
                    recommendedFly = res.getString(R.string.upperHenrysOct);
                    return recommendedFly;

                case "November":
                    recommendedFly = res.getString(R.string.upperHenrysNov);
                    return recommendedFly;

                case "December":
                    recommendedFly = res.getString(R.string.upperHenrysDec);
                    return recommendedFly;

                default: throw new IllegalArgumentException("Invalid Combination");
            }

            case "Lower Henry's Fork":

                switch (selectedMonth){
                    case "January":
                        recommendedFly = res.getString(R.string.lowerHenrysJan);
                        return recommendedFly;

                    case "February":
                        recommendedFly = res.getString(R.string.lowerHenrysFeb);
                        return recommendedFly;

                    case "March":
                        recommendedFly = res.getString(R.string.lowerHenrysMar);
                        return recommendedFly;

                    case "April":
                        recommendedFly = res.getString(R.string.lowerHenrysApr);
                        return recommendedFly;

                    case "May":
                        recommendedFly = res.getString(R.string.lowerHenrysMay);
                        return recommendedFly;

                    case "June":
                        recommendedFly = res.getString(R.string.lowerHenrysJun);
                        return recommendedFly;

                    case "July":
                        recommendedFly = res.getString(R.string.lowerHenrysJul);
                        return recommendedFly;

                    case "August":
                        recommendedFly = res.getString(R.string.lowerHenrysAug);
                        return recommendedFly;

                    case "September":
                        recommendedFly = res.getString(R.string.lowerHenrysSep);
                        return recommendedFly;

                    case "October":
                        recommendedFly = res.getString(R.string.lowerHenrysOct);
                        return recommendedFly;

                    case "November":
                        // WET vs regular ???
                        recommendedFly = res.getString(R.string.lowerHenrysNov);
                        return recommendedFly;

                    case "December":
                        recommendedFly = res.getString(R.string.lowerHenrysDec);
                        return recommendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }
            case "Snake River":

                switch (selectedMonth){
                    case "January":
                        recommendedFly = res.getString(R.string.snakeRiverJan);
                        return recommendedFly;

                    case "February":
                        recommendedFly = res.getString(R.string.snakeRiverFeb);
                        return recommendedFly;

                    case "March":
                        recommendedFly = res.getString(R.string.snakeRiverMar);
                        return recommendedFly;

                    case "April":
                        recommendedFly = res.getString(R.string.snakeRiverApr);
                        return recommendedFly;

                    case "May":
                        recommendedFly = res.getString(R.string.snakeRiverMay);
                        return recommendedFly;

                    case "June":
                        recommendedFly = res.getString(R.string.snakeRiverJun);
                        return recommendedFly;

                    case "July":
                        recommendedFly = res.getString(R.string.snakeRiverJul);
                        return recommendedFly;

                    case "August":
                        recommendedFly = res.getString(R.string.snakeRiverAug);
                        return recommendedFly;

                    case "September":
                        recommendedFly = res.getString(R.string.snakeRiverSep);
                        return recommendedFly;

                    case "October":
                        recommendedFly = res.getString(R.string.snakeRiverOct);
                        return recommendedFly;

                    case "November":
                        recommendedFly = res.getString(R.string.snakeRiverNov);
                        return recommendedFly;

                    case "December":
                        recommendedFly = res.getString(R.string.snakeRiverDec);
                        return recommendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }
            case "Teton River":

                switch (selectedMonth){
                    case "January":
                        recommendedFly = res.getString(R.string.tetonJan);
                        return recommendedFly;

                    case "February":
                        recommendedFly = res.getString(R.string.tetonFeb);
                        return recommendedFly;

                    case "March":
                        recommendedFly = res.getString(R.string.tetonMar);
                        return recommendedFly;

                    case "April":
                        recommendedFly = res.getString(R.string.tetonApr);
                        return recommendedFly;

                    case "May":
                        recommendedFly = res.getString(R.string.tetonMay);
                        return recommendedFly;

                    case "June":
                        recommendedFly = res.getString(R.string.tetonJun);
                        return recommendedFly;

                    case "July":
                        recommendedFly = res.getString(R.string.tetonJul);
                        return recommendedFly;

                    case "August":
                        recommendedFly = res.getString(R.string.tetonAug);
                        return recommendedFly;

                    case "September":
                        recommendedFly = res.getString(R.string.tetonSep);
                        return recommendedFly;

                    case "October":
                        recommendedFly = res.getString(R.string.tetonOct);
                        return recommendedFly;

                    case "November":
                        recommendedFly = res.getString(R.string.tetonNov);
                        return recommendedFly;

                    case "December":
                        recommendedFly = res.getString(R.string.tetonDec);
                        return recommendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }
            case "Warm River":

                switch (selectedMonth){
                    case "January":
                        recommendedFly = res.getString(R.string.warmJan);
                        return recommendedFly;

                    case "February":
                        recommendedFly = res.getString(R.string.warmFeb);
                        return recommendedFly;

                    case "March":
                        recommendedFly = res.getString(R.string.warmMar);
                        return recommendedFly;

                    case "April":
                        recommendedFly = res.getString(R.string.warmApr);
                        return recommendedFly;

                    case "May":
                        // WET FLIES VS NOT?
                        recommendedFly = res.getString(R.string.warmMay);
                        return recommendedFly;

                    case "June":
                        recommendedFly = res.getString(R.string.warmJun);
                        return recommendedFly;

                    case "July":
                        recommendedFly = res.getString(R.string.warmJul);
                        return recommendedFly;

                    case "August":
                        recommendedFly = res.getString(R.string.warmAug);
                        return recommendedFly;

                    case "September":
                        recommendedFly = res.getString(R.string.warmSep);
                        return recommendedFly;

                    case "October":
                        recommendedFly = res.getString(R.string.warmOct);
                        return recommendedFly;

                    case "November":
                        recommendedFly = res.getString(R.string.warmNov);
                        return recommendedFly;

                    case "December":
                        recommendedFly = res.getString(R.string.warmDec);
                        return recommendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }

            case "Fall River":

                switch (selectedMonth){
                    case "January":
                        recommendedFly = res.getString(R.string.fallJan);
                        return recommendedFly;

                    case "February":
                        recommendedFly = res.getString(R.string.fallFeb);
                        return recommendedFly;

                    case "March":
                        recommendedFly = res.getString(R.string.fallMar);
                        return recommendedFly;

                    case "April":
                        recommendedFly = res.getString(R.string.fallApr);
                        return recommendedFly;

                    case "May":
                        recommendedFly = res.getString(R.string.fallMay);
                        return recommendedFly;

                    case "June":
                        recommendedFly = res.getString(R.string.fallJun);
                        return recommendedFly;

                    case "July":
                        recommendedFly = res.getString(R.string.fallJul);
                        return recommendedFly;

                    case "August":
                        recommendedFly = res.getString(R.string.fallAug);
                        return recommendedFly;

                    case "September":
                        recommendedFly = res.getString(R.string.fallSep);
                        return recommendedFly;

                    case "October":
                        recommendedFly = res.getString(R.string.fallOct);
                        return recommendedFly;

                    case "November":
                        recommendedFly = res.getString(R.string.fallNov);
                        return recommendedFly;

                    case "December":
                        recommendedFly = res.getString(R.string.fallDec);
                        return recommendedFly;

                    default: throw new IllegalArgumentException("Invalid Combination");
                }

            default: break;
            }
            return recommendedFly;

        }

    }


