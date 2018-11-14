package edu.byui.cit.fishing.model;

		import java.util.List;
		import android.arch.persistence.room.Dao;
		import android.arch.persistence.room.Delete;
		import android.arch.persistence.room.Insert;
		import android.arch.persistence.room.Query;

@Dao
public interface FlyDataDAO {
	@Query("SELECT * FROM FlyData")
	List<Fly> getAll();

	@Insert
	void insert(FlyData fly);

	@Delete
	void delete(FlyData fly);

	@Query("DELETE FROM FlyData")
	void clearTable();
}