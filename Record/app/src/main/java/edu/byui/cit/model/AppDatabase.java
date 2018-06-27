package edu.byui.cit.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;


@Database(entities = { Goal.class }, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
	private static AppDatabase singleton;

	public static AppDatabase getInstance(Context appCtx) {
		if (singleton == null) {
			singleton = Room.databaseBuilder(
					appCtx, AppDatabase.class, "Record").allowMainThreadQueries().build();
		}
		return singleton;
	}

	public abstract GoalDAO getGoalDAO();
}
