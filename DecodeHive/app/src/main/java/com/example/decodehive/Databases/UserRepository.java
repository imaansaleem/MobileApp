package com.example.decodehive.Databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.decodehive.Databases.DAOs.ProductDao;
import com.example.decodehive.Databases.DAOs.UserDao;
import com.example.decodehive.Databases.Entities.Product;
import com.example.decodehive.Databases.Entities.User;

import java.util.List;

public class UserRepository {
    private UserRoomDatabase userRoomDatabase;
    private UserDao userDao;

    public UserRepository(Application application) {
        userRoomDatabase = UserRoomDatabase.getInstance(application);
        userDao = userRoomDatabase.userDao();
    }

    public void insertUser(User user) {
        new UserRepository.InsertAsyncTask(userRoomDatabase).execute(user);
//        productDao.insertProduct(product);
    }

    public User getUser(String email, String username, String password) {
        try {
            return new GetAsyncTask(userRoomDatabase, email, username, password).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        InsertAsyncTask(UserRoomDatabase userRoomDatabase){
            userDao = userRoomDatabase.userDao();
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }

    class GetAsyncTask extends AsyncTask<Void, User, User> {
        private UserDao userDao;
        private String email;
        private String username;
        private String password;

        GetAsyncTask(UserRoomDatabase userRoomDatabase, String email, String username, String password){
            this.userDao = userRoomDatabase.userDao();
            this.email = email;
            this.username = username;
            this.password = password;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userDao.getUser(email, username, password);
        }
    }
}
