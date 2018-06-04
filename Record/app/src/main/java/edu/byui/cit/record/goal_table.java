package edu.byui.cit.record;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "goal_table")
public class goal_table {

    @PrimaryKey
private int goal_id;

    @ColumnInfo(name = "goal_name")
private String goal_name;
private String goal_description;
private boolean goal_type;
private String goal_frequency;

    public int getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(int goal_id) {
        this.goal_id = goal_id;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public void setGoal_description(String goal_description) {
        this.goal_description = goal_description;
    }

    public boolean isGoal_type() {
        return goal_type;
    }

    public void setGoal_type(boolean goal_type) {
        this.goal_type = goal_type;
    }

    public String getGoal_frequency() {
        return goal_frequency;
    }

    public void setGoal_frequency(String goal_frequency) {
        this.goal_frequency = goal_frequency;
    }
}
