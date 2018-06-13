package edu.byui.cit.maintenance;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.Vehicle;
import edu.byui.cit.model.VehicleDAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(AndroidJUnit4.class)
public class TestVehicle {
	private AppDatabase db;
	private VehicleDAO vehicleDAO;

	@Before
	public void createDb() {
		Context context = InstrumentationRegistry.getTargetContext();
		db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
		vehicleDAO = db.getVehicleDAO();
	}

	@After
	public void closeDb() {
		db.close();
	}

	@Test
	public void writeUserAndReadInList() {
		Vehicle vehicle = new Vehicle();
		vehicle.setVin("sparky");
		vehicle.setYear(2017);
		vehicle.setMake("Chevrolet");
		vehicle.setModel("Bolt");
		vehicle.setColor("red");

		vehicleDAO.insert(vehicle);
		int id = vehicle.getVehicleID();
		assertFalse(id > 0);

		Vehicle found = vehicleDAO.getByVIN("sparky");
		vehicle.setVehicleID(found.getVehicleID());
		assertEquals(vehicle, found);

		List<Vehicle> all = vehicleDAO.getAll();
		assertEquals(1, all.size());
		assertEquals(vehicle, all.get(0));
	}
}
