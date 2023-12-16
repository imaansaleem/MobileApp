package com.example.decodehive.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.decodehive.Databases.Entities.Product;
import com.example.decodehive.Databases.Entities.User;
import com.example.decodehive.Databases.ProductRepository;
import com.example.decodehive.Databases.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private User user;
    
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }
    public User getUser(String email, String username, String password) {
        User x = userRepository.getUser(email, username, password);
        if (x != null)
            user = x;
        return x;
    }

    public User getLoggedInUser() {
        return user;
    }
}
