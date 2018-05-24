package edu.byui.cit.record;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.Goal;
import edu.byui.cit.model.GoalDAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(AndroidJUnit4.class)
public class TestGoal {
	private AppDatabase db;
	private GoalDAO goalDAO;

	@Before
	public void createDb() {
		Context context = InstrumentationRegistry.getTargetContext();
		db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
		goalDAO = db.getGoalDAO();
	}

	@After
	public void closeDb() {
		db.close();
	}

	@Test
	public void writeUserAndReadInList() {
		Goal goal = new Goal();
		goal.setType(Goal.Type.bool);
		goal.setFrequency(Goal.Frequency.weekly);
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.set(2018, 5, 1);
		end.set(2018, 11, 1);
		goal.setStart(start.getTime());
		goal.setEnd(end.getTime());

		goalDAO.insert(goal);
		int id = goal.getGoalID();
		assertFalse(id > 0);

		List<Goal> all = goalDAO.getAll();
		assertEquals(1, all.size());
		goal.setGoalID(all.get(0).getGoalID());
		assertEquals(goal, all.get(0));
	}
}
