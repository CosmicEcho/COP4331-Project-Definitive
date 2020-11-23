package com.example.cop4331projectdefinitive;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

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
