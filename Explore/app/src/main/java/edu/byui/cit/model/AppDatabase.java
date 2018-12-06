package edu.byui.cit.model;
//these imports are needed to get the Room database to work.
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
/*
this is the class where we create the database object.  Remember, this is
object-oriented programming, so therefore,
the database is an object.
the Android Studio training at https://developer.android
.com/training/data-storage/room/ provides excellent information.
*/

//we first say that this class is a database with the @Database annotation.
//we have to list any entities, the version number, and if we can export the
// schema.
//we have listed Pin as the only entity, because it is the only entity we
// have created.
//we use version 1 because we didn't want to bother using version 2.
//we also said that you couldn't export the schema, we did this for
// performance reasons, but it could be true if that is
//the direction you are going to go.
@Database(entities = { Pin.class }, version = 1, exportSchema = false)
//we also created a converters class, annotated with @TypeConverters.  It is
// not possible to store a date to the Room database.
//We use the TypeConverters to change date into timestamp and timestamp back into date.
@TypeConverters({Converters.class})
//here we create the class 'AppDatabase', it extends RoomDatabase because it
// is a RoomDatabase class.
public abstract class AppDatabase extends RoomDatabase {
	//here we create the database object.  We also give it the singleton
	// attribute.  We only want one instance of the database running
	//at any given time.  If we did not have this, every database transaction
	// would create a new database and things go crazy.
	private static AppDatabase singleton;

	//this method finds out if a database is running, and if it is not it
	// passes the app context into the database and builds the database in-memory.
	public static AppDatabase getInstance(Context appCtx) {
		//this if statement checks if the database does not have an instance
		if (singleton == null) {
			//if there is no instance, then it builds the database using the
			// context appCTx, the database class, a name (any name will do),
			// and we
			//allowed main thread queries, and then we tell it to build.
			//WARNING: large queries will crash the app.  Wrap a new Thread
			// Runnable around any database transaction (like PinDAO.insert).
			//we haven't figured out new Thread Runnables that well, so that will be up to you.
			singleton = Room.databaseBuilder(
					appCtx, AppDatabase.class, "Record")
					.allowMainThreadQueries().build();
		}
		//this returns the singleton that we created above.
		return singleton;
	}

	// this instantiates the PinDAO object using the getPinDAO() method.
	public abstract PinDAO getPinDAO();
}
