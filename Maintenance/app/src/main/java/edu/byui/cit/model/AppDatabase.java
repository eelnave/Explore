package edu.byui.cit.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = { Vehicle.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
	private static AppDatabase singleton;

	public static AppDatabase getInstance(Context appCtx) {
		if (singleton == null) {
			singleton = Room.databaseBuilder(
					appCtx, AppDatabase.class, "maintenance").build();
		}
		return singleton;
	}

	public abstract VehicleDAO getVehicleDAO();
}
