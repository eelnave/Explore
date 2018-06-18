package edu.byui.cit.record;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = { Goal.class }, version = 1)
public abstract class GoalDatabase extends RoomDatabase {

	public abstract GoalDAO getGoalDAO();
}
