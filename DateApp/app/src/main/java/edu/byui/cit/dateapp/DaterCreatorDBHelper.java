package edu.byui.cit.dateapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        Log.i("example", "We are in the dataHelper");

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DaterCreatorContract.UserEntry.TABLE_NAME);


    }

    public boolean insertDataIntoDatesDone(int userID, int datesInfoID){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DaterCreatorContract.DatesDone.USER_ID_COLUMN, userID);
            contentValues.put(DaterCreatorContract.DatesDone.DATE_INFO_ID, datesInfoID);
            long result = db.insert(DaterCreatorContract.DatesDone.TABLE_NAME, null, contentValues);
            if(result == -1)
                return false;
            else
                return true;

    }

    public boolean insertDataIntoUser(String name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = 0;
        if(result == -1)
            return false;
        else
            return true;

    }


}
