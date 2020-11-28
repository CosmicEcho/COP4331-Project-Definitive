package com.example.cop4331projectdefinitive;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE user_name LIKE :email")
    User findByUsername(String email);

    @Query("SELECT * FROM user WHERE user_order NOT LIKE :inString")
    List<User> findAllWithOrders(String inString);

    @Update
    public void updateUser(User... users);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
