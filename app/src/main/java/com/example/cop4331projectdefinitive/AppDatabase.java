package com.example.cop4331projectdefinitive;

// Imports

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * AppDatabase extends the RoomDatabase functionality by setting the database entity to the User class
 * and TypeConverters to the Converters class. Lastly, sets the DAO to the UserDao class.
 */

@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
