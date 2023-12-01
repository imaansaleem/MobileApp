package com.example.decodehive.Databases.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.decodehive.Databases.Entities.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM USER WHERE (email = :email OR username = :username) AND password = :password")
    User getUser(String email, String username, String password);
}
