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
                DaterCreatorContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DaterCreatorContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL);" ;

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DaterCreatorContract.UserEntry.TABLE_NAME);


    }
}
