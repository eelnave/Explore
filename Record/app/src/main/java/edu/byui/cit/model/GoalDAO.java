package edu.byui.cit.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

//interface to interact with database
@Dao
public interface GoalDAO {
	@Query("SELECT * FROM goal")
	List<Goal> getAll();

	@Query("SELECT * FROM goal WHERE goalID IN (:goalIDs)")
	List<Goal> getAllByID(int[] goalIDs);

	@Query("SELECT * FROM goal WHERE goalID = :id")
	Goal getByID(int id);

	@Insert
	long insert(Goal goal);

	@Insert
	void insertAll(Goal... goals);

	@Delete
	int delete(Goal goal);

	@Query("DELETE FROM goal")
	void clearTable();
}
