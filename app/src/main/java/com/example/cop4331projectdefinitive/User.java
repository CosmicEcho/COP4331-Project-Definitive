package com.example.cop4331projectdefinitive;

// Imports

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * User class that represents all of the application's potential users. Contains Strings for the userName
 * and userType, an int ID that is autogenerated upon being registered to the database, and a double
 * containing the hashed password of a given user. This class also serves as the only entity type
 * in the AppDatabase.
 */

@Entity
public class User {
    public User(String userName, double password, String userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.order = null;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "pass_word")
    public double password;

    @ColumnInfo(name = "user_type")
    public String userType;

    @ColumnInfo(name = "user_order")
    public Order order;
}
