package edu.byui.cit.record;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface MyDao
{
    @Insert
    public void addGoal (goal_table goal);

}
