package com.example.cop4331projectdefinitive;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.JsonElement;

@Entity
public class User {
    public User(String userName, int password, String userType) {
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
    public int password;

    @ColumnInfo(name = "user_type")
    public String userType;

    @ColumnInfo(name = "user_order")
    public Order order;
}
