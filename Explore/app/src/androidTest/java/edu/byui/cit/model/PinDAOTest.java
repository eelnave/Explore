package edu.byui.cit.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import edu.byui.cit.explore.R;
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
    Pin pin0 = new Pin(R.drawable.icon_person, 10.0, 10.0, date, "notes");
	Double delta = 0.00001;
	@Test
	public void getAll() {
		// check pin0 assertions
		assertTrue(pin0.getPinId() > 0);
		assertEquals(1,pin0.getPinId());
        assertTrue(pin0.getIconName() == R.drawable.icon_person);
		assertEquals("pin", pin0.getIconName());
		assertTrue(pin0.getLatitude() == 10.0);
		assertEquals(10.0, (double)pin0.getLatitude(), delta);
		assertTrue(pin0.getLongitude() == 15.0);
		assertEquals(15.0, (double)pin0.getLongitude(), delta);
		assertEquals(date,pin0.getDate());
		assertTrue(pin0.getNotes() == "notes");
		// test database assertions
		pinDAO.insert(pin0);

		List<Pin> all = pinDAO.getAll();
		assertEquals(1, all.size());
		Pin stored = all.get(0);

		//testing to see if the values got stored in pin
		assertTrue(pin0.getPinId() > 0);
		assertEquals(1,pin0.getPinId());
        assertTrue(pin0.getIconName() == R.drawable.icon_person);
		assertEquals("pin", pin0.getIconName());
		assertTrue(pin0.getLatitude() == 10.0);
		assertEquals(10.0, (double)pin0.getLatitude(), delta);
		assertTrue(pin0.getLongitude() == 15.0);
		assertEquals(15.0, (double)pin0.getLongitude(), delta);
		assertEquals(date,pin0.getDate());
		assertTrue(pin0.getNotes() == "notes");


	}
}