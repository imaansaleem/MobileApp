package com.example.decodehive.Databases.Entities;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id=0;
    @ColumnInfo
    String email;

    @ColumnInfo
    String username;
    @ColumnInfo
    String password;

    @Ignore
    static User loggedInUser = null;
    @Ignore
    List<com.example.decodehive.Databases.Entities.Product> cart = new ArrayList<>();
    @Ignore
    public static User getLoggedInUser() {
        Log.d("userss-jwqd", loggedInUser.email);
        return loggedInUser;
    }
    @Ignore
    public static void setLoggedInUser(User loggedInUser) {
        Log.d("userss", loggedInUser.email);
        User.loggedInUser = loggedInUser;
    }

    public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        cart = new ArrayList<>();
    }

    @Ignore
    public void addToCart(Product item) {
        if(loggedInUser == null)
            return;
        cart.add(item);
    }

    @Ignore
    public List<com.example.decodehive.Databases.Entities.Product> getCart() {
        if(loggedInUser == null)
            return null;

        return cart;
    }

    @Ignore
    public void emptyCart() {
        cart.clear();
    }

}
