package com.example.decodehive.Databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.decodehive.Databases.DAOs.UserDao;
import com.example.decodehive.Databases.Entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {
    public static final String DATABASE_NAME="UserRoomDatabase";
    public abstract UserDao userDao();
    private static volatile UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                    UserRoomDatabase.class, DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        PopulateAsyncTask(UserRoomDatabase userRoomDatabase){
            userDao = userRoomDatabase.userDao();
        }
        @Override
        protected Void doInBackground(Void... voids){
            User user = new User("imaan@gmail.com", "Imaan", "1234");
            userDao.insertUser(user);
            return null;
        }
    }
}
