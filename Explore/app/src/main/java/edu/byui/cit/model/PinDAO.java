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

	@Insert
	void insert(Pin pin);

	@Delete
	void delete(Pin pin);

	@Query("DELETE FROM Pin")
	void clearTable();
	}

