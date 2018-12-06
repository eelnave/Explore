package edu.byui.cit.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


//we used this class to figure out our Pin.java class.  This class does not
// get used and is only here for information.
@Entity
public class Goal {
	public enum Type {
		none,
		bool,
		num,
		text;
	}

	public enum Frequency {
		none,
		hourly,
		daily,
		weekly,
		monthly,
		quarterly,
		yearly;
	}

	@PrimaryKey(autoGenerate = true)
	private int goalID;

	private String title;
	private String description;
	private Type type;
	private Frequency frequency;
	private Date start;
	private Date end;

	public int getGoalID() {
		return goalID;
	}

	public void setGoalID(int goalID) {
		this.goalID = goalID;
	}

	public String getTitle() { return title; }

	public void setTitle(String title) { this.title = title; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = new Date(start.getTime());
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = new Date(end.getTime());
	}


	@Override
	public boolean equals(Object obj2) {
		boolean equ = false;
		if (obj2 instanceof Goal) {
			Goal other = (Goal)obj2;
			equ = goalID == other.goalID &&
					title == other.title &&
					description == other.description &&
					type == other.type &&
					frequency == other.frequency &&
					compareObjects(start, other.start) &&
					compareObjects(end, other.end);
		}
		return equ;
	}

	private static boolean compareObjects(Object obj1, Object obj2) {
		return obj1 == null ? obj2 == null : obj1.equals(obj2);
	}

	@Override
	public String toString() {
		return "Goal: " +
				goalID + ", " +
				title + ", " +
				description + ", " +
 				type + ", " +
				frequency + ", " +
				start + ", " +
				end;
	}
}
