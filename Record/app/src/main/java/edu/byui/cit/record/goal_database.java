package edu.byui.cit.record;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {goal_table.class}, version = 1)
public abstract class goal_database extends RoomDatabase {

    public abstract MyDao myDao();

}
