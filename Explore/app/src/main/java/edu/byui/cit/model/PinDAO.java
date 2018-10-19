package edu.byui.cit.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface PinDAO {
	@Query("SELECT * FROM Pin")
	List<Pin> getAll();

	@Query("SELECT * FROM Pin WHERE PinID IN (:PinIDs)")
	List<Pin> getAllByID(int[] PinIDs);

	@Query("SELECT * FROM Pin WHERE PinID = :id")
    Pin getByID(int id);

	@Insert
	long insert(Pin Pin);

	@Insert
	void insertAll(Pin... Pins);

	@Delete
	int delete(Pin Pin);

	@Query("DELETE FROM Pin")
	void clearTable();
}
