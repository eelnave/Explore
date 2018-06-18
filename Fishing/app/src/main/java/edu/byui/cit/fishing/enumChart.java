package edu.byui.cit.fishing;

public class enumChart {

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


    // a method that prints a String corresponding to the day value
    // that is passed in.
    public static void speciesOfChoice(species theSpecies)
    {
        switch (theSpecies)
        {
            case Rainbow:
            case Cutthroat:
            case Brown:
            case Brook:

            default:        System.out.println("What species are you fishing for?");
        }
    }

    public static void waterOfChoice(water theWater)
    {
        switch (theWater)
        {
            case UpperHenry:
            case LowerHenry:
            case SnakeRiver:
            case TetonRiver:
            case WarmRiver:
            case FallRiver:

            default:        System.out.println("What body of water are you fishing?");
        }
    }

    public static void monthOfChoice(month theMonth)
    {
        switch (theMonth)
        {
            case January:
            case February:
            case March:
            case April:
            case May:
            case June:
            case July:
            case August:
            case September:
            case October:
            case November:
            case December:

            default:        System.out.println("What month are you fishing?");
        }
    }

}

