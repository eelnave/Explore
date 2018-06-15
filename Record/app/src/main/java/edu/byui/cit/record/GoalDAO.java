package edu.byui.cit.record;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;


@Dao
public interface GoalDAO {
	@Insert
	public void addGoal(Goal goal);
}
