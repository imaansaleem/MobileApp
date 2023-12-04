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
            new PopulateAsyncTask(INSTANCE).execute();
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

            //dummy Data
            productDao.insertProduct(new Product(2, "Don't Make Me Think", "0132350882", "Don't Make Me Think by Steve Krug is a timeless guide to web usability, emphasizing a common-sense approach. Krug's insights on usability testing, navigation, and accessibility make it essential for web designers and developers seeking to create user-friendly websites.", 3.99));
            productDao.insertProduct(new Product(3, "The Art Of Invisibility", "0990582906", "The Art of Invisibility is a must-read guide to online privacy and digital security. In this book, the author provides vital strategies and real-world tactics to help readers protect their online identity and maintain personal privacy in today's digital age.", 4.99));
            productDao.insertProduct(new Product(4, "Database Design", "3950307820", "Database Design for Mere Mortals is an essential guide for anyone interested in database design. It takes a practical, easy-to-follow approach to database concepts and design, making it perfect for beginners and professionals alike.", 8.99));
            productDao.insertProduct(new Product(5, "Fundamentals of DBMS", "1449373321", "Fundamentals of DBMS provides a solid foundation in Database Management Systems (DBMS). This book offers comprehensive insights into database concepts, making it a crucial resource for students and professionals looking to grasp the core principles of DBMS.", 7.99));
            productDao.insertProduct(new Product(6, "C# Programming", "0735619670", "A Quick Guide to C# Programming is a fast-track resource for learning C# programming. With its concise explanations and hands-on examples, this book is ideal for beginners and intermediate programmers looking to master C# quickly.", 3.49));
            productDao.insertProduct(new Product(7, "The Pragmatic Programmer", "0201616223", "The Pragmatic Programmer is an indispensable resource for software developers. This book offers practical advice and timeless wisdom for becoming a more effective and efficient programmer. It is a must-read for both novice and experienced developers seeking to elevate their coding skills and practices.", 0.99));
            productDao.insertProduct(new Product(8, "System Design Interview", "9781718501151", "It provides a comprehensive guide for software engineers preparing for technical interviews focused on system design. It offers valuable insights, practical advice, and real-world examples to help readers tackle complex system design questions effectively.", 2.99));
            productDao.insertProduct(new Product(9, "Python Programming", "9781234567890", "It is an excellent resource for those looking to learn Python from scratch. It covers fundamental concepts and practical examples to help beginners build a strong foundation in Python programming.", 4.49));
            productDao.insertProduct(new Product(10, "Data Wrangling with SQL", "9780987654321", "It is an indispensable guide for anyone working with data for data professionals and analysts with the skills needed to clean, reshape, and transform data effectively using SQL. It offers practical guidance on using SQL for data cleansing, transformation, and manipulation.", 6.99));
            return null;
        }
    }
}
