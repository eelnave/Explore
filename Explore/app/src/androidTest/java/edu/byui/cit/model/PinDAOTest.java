package edu.byui.cit.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class PinDAOTest{
	private AppDatabase db;
	private PinDAO	pinDAO;
	@Before
	public void createDb(){
		Context context = InstrumentationRegistry.getTargetContext();
		db = Room.inMemoryDatabaseBuilder(context,AppDatabase.class).build();
		pinDAO = db.getPinDAO();
	}

	@After
	public void closeDb(){
		db.close();
	}

	Date date = new Date();
	Pin pin0 = new Pin(1,"pin",10.0,10.0,date,"notes");
	@Test
	public void getAll() {
		pinDAO.insert(pin0);
		long id = pin0.getPinId();
		assertTrue(id > 0);
		assertTrue(pin0.getLatitude() == 10.0);

		List<Pin> all = pinDAO.getAll();
		assertEquals(1, all.size());

	}



}