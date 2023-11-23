package com.example.decodehive.Databases;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.decodehive.Databases.DAOs.ProductDao;
import com.example.decodehive.Databases.Entities.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class ProductRoomDatabase extends RoomDatabase {

    public static final String DATABASE_NAME="ProductRoomDatabase";
    public abstract ProductDao productDao();
    private static volatile ProductRoomDatabase INSTANCE;

    public static ProductRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                    ProductRoomDatabase.class, DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao productDao;
        PopulateAsyncTask(ProductRoomDatabase productRoomDatabase){
            productDao = productRoomDatabase.productDao();
        }
        @Override
        protected Void doInBackground(Void... voids){
            productDao.deleteAll();
            return null;
        }
    }
}
