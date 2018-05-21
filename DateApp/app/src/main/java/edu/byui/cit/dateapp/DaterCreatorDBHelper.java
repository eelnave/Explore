package edu.byui.cit.dateapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.byui.cit.dateapp.data.DaterCreatorContract;


public class DaterCreatorDBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "datercreator.db";
	private static final int DATABASE_VERSION = 1;


	public DaterCreatorDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		final String CREATE_USER_TABLE = "CREATE TABLE " +
				DaterCreatorContract.UserEntry.TABLE_NAME + " ( " +
				DaterCreatorContract.UserEntry._ID + " INTEGER PRIMARY KEY " +
				"AUTOINCREMENT, " + DaterCreatorContract.UserEntry
				.COLUMN_NAME + "TEXT;";
		final String CREATE_DATE_INFO = "CREATE TABLE " + DaterCreatorContract
				.DateInfo.TABLE_NAME + " ( " +
				DaterCreatorContract.DateInfo._ID + " INTEGER PRIMARY KEY " +
				"AUTOINCREMENT, "
				+ DaterCreatorContract.DateInfo.DATE_NAME_COLUMN + " TEXT NOT" +
				" " +
				"NULL, " + DaterCreatorContract.DateInfo
				.DATE_GROUP_SIZE_COLUMN + " text,  "
				+ DaterCreatorContract.DateInfo.DATE_TEMP_COLUMN + " TEXT, " +
				DaterCreatorContract.DateInfo.DATE_VENUE_COLUMN + " TEXT, "
				+ DaterCreatorContract.DateInfo.DATE_DESCRIPTION_COLUMN + " " +
				"TEXT NOT NULL, "
				+ DaterCreatorContract.DateInfo.DATE_ACTIVITY_LEVEL_COLUMN +
				"" +
				" INTEGER, "
				+ DaterCreatorContract.DateInfo.DATE_COST_COLUMN_ + " INTEGER" +
				" " +
				");";
		final String CREATE_DATES_DONE = "CREATE TABLE " +
				DaterCreatorContract.DatesDone.TABLE_NAME + " ( "
				+ DaterCreatorContract.DatesDone._ID + " INTEGER PRIMARY KEY" +
				" " +
				"AUTOINCREMENT, " + DaterCreatorContract.DatesDone
				.USER_ID_COLUMN
				+ " INTEGER NOT NULL" + DaterCreatorContract.DatesDone
				.DATE_INFO_ID + " INTEGER NOT NULL, " +
				" FOREIGN KEY(" + DaterCreatorContract.DatesDone
				.USER_ID_COLUMN + ") REFERENCES " + DaterCreatorContract
				.UserEntry.TABLE_NAME
				+ "(" + DaterCreatorContract.UserEntry._ID + "), "
				+ "FOREIGN KEY(" + DaterCreatorContract.DatesDone.DATE_INFO_ID
				+ ") REFERENCES " + DaterCreatorContract.DateInfo.TABLE_NAME
				+ "(" + DaterCreatorContract.DateInfo._ID + ")" + ");";
		Log.i("example", "We are in the dataHelper");

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
		final String INSERT_DATA = "INSERT INTO" + DaterCreatorContract
				.DateInfo.TABLE_NAME + " ( "
				+ DaterCreatorContract.DateInfo.DATE_NAME_COLUMN + ", "
				+ DaterCreatorContract.DateInfo.DATE_DESCRIPTION_COLUMN + ", "
				+ DaterCreatorContract.DateInfo.DATE_COST_COLUMN_ + ", "
				+ DaterCreatorContract.DateInfo.DATE_ACTIVITY_LEVEL_COLUMN +
				", "
				+ DaterCreatorContract.DateInfo.DATE_TEMP_COLUMN + ", "
				+ DaterCreatorContract.DateInfo.DATE_VENUE_COLUMN + ")"
				+ " VALUES "
				+ "("
				+ " \'Shooting star night Golf\'" + ", "
				+ " \'Grab some golf balls and glow sticks. Check you local " +
				"DI" +
				" for drivers. Head out to the sand dunes (or anywhere high)" +
				" " +
				"and break the glow stick open and cover golf balls in " +
				"fluorescent light. See hoe far you can hit them and watch " +
				"them soar \'" + ", "
				+ "2" + ", "
				+ "2" + ", "
				+ "\'warm\'" + ", "
				+ "\'outside\'"
				+ "), "

				+ "("
				+ " \'Netflix in Silence\'" + ", "
				+ " \'Watch a Netflix movie neither of you have seen and turn" +
				" " +
				"the sound off and see what kind of story you guys can come " +
				"up with! You can spice it up by ordering take out or ice " +
				"cream \'" + ", "
				+ "1" + ", "
				+ "1" + ", "
				+ "\'any\'" + ", "
				+ "\'inside\'"
				+ "), "

				+ "("
				+ " \'Arcade Night\'" + ", "
				+ " \'See how many tickets you can get and what fun prizes " +
				"you" +
				" can win!\'" + ", "
				+ "2" + ", "
				+ "2" + ", "
				+ "\'any\'" + ", "
				+ "\'inside\'"
				+ "), "

				+ "("
				+ " \'Paint night\'" + ", "
				+ " \'Go to Porters and pick out \'" + ", "
				+ "1" + ", "
				+ "1" + ", "
				+ "\'any\'" + ", "
				+ "\'inside\'"
				+ "), "
				+ ");";
	}


	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL(
				"DROP TABLE IF EXISTS " +
						DaterCreatorContract.UserEntry.TABLE_NAME);
	}


	public boolean insertDataIntoDatesDone(int userID, int datesInfoID) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DaterCreatorContract.DatesDone.USER_ID_COLUMN,
				userID);
		contentValues.put(DaterCreatorContract.DatesDone.DATE_INFO_ID,
				datesInfoID);
		long result = db.insert(DaterCreatorContract.DatesDone.TABLE_NAME,
				null,
				contentValues);
		return result != -1;
	}


	public boolean insertDataIntoUser(String name) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DaterCreatorContract.UserEntry.COLUMN_NAME, name);
		long result = 0;
		return result != -1;
	}
}
