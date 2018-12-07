package edu.byui.cit.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;


//this is the database object class for Pin.  You must be thinking, why do I
// need this when I have a Pin object, right?
//well guess, what?  I had the exact question too.
//simply stated, the database doesn't know what objects are, it just wants
// everything nice and neat in a formatted table.  Its frustrating.
//I digress.
//The @Dao annotation is used to declare that this DAO is, in fact, a DAO.
@Dao
//The DAO is actually an interface.
public interface PinDAO {
	//here you can use annotations found from https://developer.android
	// .com/training/data-storage/room/
	//@Query is an annotation that says 'this is a query'.  You can then put
	// in your own custom query.
	//This particular query selects everything from the Pin table and puts it
	// into a List collection.  We give it a method name called getAll();
	@Query("SELECT * FROM Pin")
	List<Pin> getAll();

	//@Insert is an annotation that says 'insert this into Pin'.  It writes
	// it's own SQL so we don't have to.
	//You must pass something into the insert() method.  We pass in Pin, so
	// that a Pin gets inserted into the database.
	@Insert
	void insert(Pin pin);

	//By now, you should probably understand what delete means.  You're in CIT 360.  Figure it out.
	@Delete
	void delete(Pin pin);

	@Query("DELETE FROM Pin WHERE latitude = :latitude AND longitude = :longitude")
	void deletePin(double latitude, double longitude);

	//This is the delete all query.  It takes everything from the Pin table and deletes it all.
	@Query("DELETE FROM Pin")
	void clearTable();
}
