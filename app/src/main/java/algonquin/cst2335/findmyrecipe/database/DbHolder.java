package algonquin.cst2335.findmyrecipe.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * This singleton holds the one holy reference to the database. This should be initialized in the first
 * activity the app starts.
 */
public class DbHolder {

    private static AppDatabase db;

    /**
     * Set the database reference, just needs context.
     * @param context
     */
    public static void initDb (Context context)  {
        if(db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, "recipedb").fallbackToDestructiveMigration().build();
        }
        //NOP NOP NOP ... it already exists, so silently do nothing.
        //....
        //OKAY OKAY WE'LL SAY SOMETHING ABOUT IT, GOSH
        Log.w("DbHolder", "Attempted to initialize database, but it's already been done.");
    }

    /**
     * Get the database reference
     * @return database reference
     */
    public static AppDatabase getDb()  {

        if(db == null)  {
            Log.e("DbHolder", "Attempted to get database reference, but it hadn't been initialized yet");
            throw new NullPointerException("Attempted to get database reference, but it hadn't been initialized yet");
        }
        return db;
    }

}
