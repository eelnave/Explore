package edu.byui.cit.dateapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.byui.cit.dateapp.data.DaterCreatorContract;


public class DaterCreatorDBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "datercreator.db";
    private static final int DATABASE_VERSION = 1;

    public DaterCreatorDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_USER_TABLE = "CREATE TABLE " + DaterCreatorContract.UserEntry.TABLE_NAME + " ( " +
                DaterCreatorContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT;" ;
        final String CREATE_DATE_INFO = "CREATE TABLE " + DaterCreatorContract.DateInfo.TABLE_NAME + " ( " +
                DaterCreatorContract.DateInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DaterCreatorContract.DateInfo.DATE_NAME_COLUMN + " TEXT NOT NULL, " + DaterCreatorContract.DateInfo.DATE_GROUP_SIZE_COLUMN + " text,  "
                + DaterCreatorContract.DateInfo.DATE_TEMP_COLUMN + " TEXT, " + DaterCreatorContract.DateInfo.DATE_VENUE_COLUMN + " TEXT, "
                + DaterCreatorContract.DateInfo.DATE_DESCRIPTION_COLUMN + " TEXT NOT NULL, "
                + DaterCreatorContract.DateInfo.DATE_ACTIVITY_LEVEL_COLUMN + " INTEGER, "
                + DaterCreatorContract.DateInfo.DATE_COST_COLUMN_ + " INTEGER );";
        final String CREATE_DATES_DONE = "CREATE TABLE " + DaterCreatorContract.DatesDone.TABLE_NAME + " ( "
                + DaterCreatorContract.DatesDone.DATE_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);


        // do i need to add ''
        // weather.. any?
        // inside numbers?


        /*
        temp
                + "("
                + " \'\'" + ", "
                + " \' \'" + ", "
                + "1" + ", "
                + "1" + ", "
                + "\'any\'" + ", "
                + "\'inside\'"
                + "), "
         */

                                                    //dont know it this is right
        final String INSERT_DATA = "INSERT INTO" +  DaterCreatorContract.DateInfo.TABLE_NAME + " ( "
                + DaterCreatorContract.DateInfo.DATE_NAME_COLUMN + ", "
                + DaterCreatorContract.DateInfo.DATE_DESCRIPTION_COLUMN + ", "
                + DaterCreatorContract.DateInfo.DATE_COST_COLUMN_ + ", "
                + DaterCreatorContract.DateInfo.DATE_ACTIVITY_LEVEL_COLUMN + ", "
                + DaterCreatorContract.DateInfo.DATE_TEMP_COLUMN + ", "
                + DaterCreatorContract.DateInfo.DATE_VENUE_COLUMN + ")"
                + " VALUES "
                + "("
                + " \'Shooting star night Golf\'" + ", "
                + " \'Grab some golf balls and glow sticks. Check you local DI for drivers. Head out to the sand dunes (or anywhere high) and break the glow stick open and cover golf balls in fluorescent light. See hoe far you can hit them and watch them soar \'" + ", "
                + "2" + ", "
                + "2" + ", "
                + "\'warm\'" + ", "
                + "\'outside\'"
                + "), "
                ////////////////////////////// end of that date
                + "("
                + " \'Netflix in Silence\'" + ", "
                + " \'Watch a Netflix movie neither of you have seen and turn the sound off and see what kind of story you guys can come up with! You can spice it up by ordering take out or ice cream \'" + ", "
                + "1" + ", "
                + "1" + ", "
                + "\'any\'" + ", "
                + "\'inside\'"
                + "), "
                //////////////////////////end of that date
                + "("
                + " \'Arcade Game Night\'" + ", "
                + " \'See how many ticket you can get for some little fun prizes\'" + ", "
                + "2" + ", "
                + "2" + ", "
                + "\'any\'" + ", "
                + "\'inside\'"
                + "), "

                + ");";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DaterCreatorContract.UserEntry.TABLE_NAME);


    }
}


// String Insert