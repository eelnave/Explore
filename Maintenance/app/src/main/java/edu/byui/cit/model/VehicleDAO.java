package edu.byui.cit.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface VehicleDAO {
	@Query("SELECT * FROM vehicle")
	List<Vehicle> getAll();

	@Query("SELECT * FROM vehicle WHERE vehicleID IN (:vehicleIDs)")
	List<Vehicle> getAllByID(int[] vehicleIDs);

	@Query("SELECT * FROM vehicle WHERE vehicleID = :id")
	Vehicle getByID(int id);

	@Query("SELECT * FROM vehicle WHERE vin = :vin")
	Vehicle getByVIN(String vin);

	@Insert
	long insert(Vehicle vehicle);

	@Insert
	void insertAll(Vehicle... vehicles);

	@Delete
	int delete(Vehicle vehicle);
}
