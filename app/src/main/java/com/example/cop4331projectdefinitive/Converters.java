package com.example.cop4331projectdefinitive;

// Imports

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Uses Gson to convert the Order object to a string that can be represented in the AppDatabase, and
 * vice versa.
 */

public class Converters {
    @TypeConverter
    public static Order fromString(String value) {
        Type orderType = new TypeToken<Order>(){}.getType();

        return new Gson().fromJson(value, orderType);
    }

    @TypeConverter
    public static String fromOrder(Order value) {
        Gson gson = new Gson();
        String json = gson.toJson(value);
        return json;
    }
}
