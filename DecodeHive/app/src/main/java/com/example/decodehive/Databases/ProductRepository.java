package com.example.decodehive.Databases;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.decodehive.Databases.DAOs.ProductDao;
import com.example.decodehive.Databases.Entities.Product;

import java.util.List;

public class ProductRepository {
    private ProductRoomDatabase productRoomDatabase;
    private ProductDao productDao;
    public LiveData<List<Product>> allProducts;

    public ProductRepository(Application application) {
        productRoomDatabase = ProductRoomDatabase.getInstance(application);
        productDao = productRoomDatabase.productDao();
    // int id, String bookName, String ISBN, String description, double price
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(2, "Don't Make Me Think", "0132350882", "Don't Make Me Think by Steve Krug is a timeless guide to web usability, emphasizing a common-sense approach. Krug's insights on usability testing, navigation, and accessibility make it essential for web designers and developers seeking to create user-friendly websites.", 3.99)
        );
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(3, "The Art Of Invisibility", "0990582906", "The Art of Invisibility is a must-read guide to online privacy and digital security. In this book, the author provides vital strategies and real-world tactics to help readers protect their online identity and maintain personal privacy in today's digital age.", 4.99)
        );
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(4, "Database Design", "3950307820", "Database Design for Mere Mortals is an essential guide for anyone interested in database design. It takes a practical, easy-to-follow approach to database concepts and design, making it perfect for beginners and professionals alike.", 8.99)
        );
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(5, "Fundamentals of DBMS", "1449373321", "Fundamentals of DBMS provides a solid foundation in Database Management Systems (DBMS). This book offers comprehensive insights into database concepts, making it a crucial resource for students and professionals looking to grasp the core principles of DBMS.", 7.99)
        );
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(6, "C# Programming", "0735619670", "A Quick Guide to C# Programming is a fast-track resource for learning C# programming. With its concise explanations and hands-on examples, this book is ideal for beginners and intermediate programmers looking to master C# quickly.", 3.49)
        );
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(7, "The Pragmatic Programmer", "0201616223", "The Pragmatic Programmer is an indispensable resource for software developers. This book offers practical advice and timeless wisdom for becoming a more effective and efficient programmer. It is a must-read for both novice and experienced developers seeking to elevate their coding skills and practices.", 0.99)
        );
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(8, "System Design Interview", "9781718501151", "It provides a comprehensive guide for software engineers preparing for technical interviews focused on system design. It offers valuable insights, practical advice, and real-world examples to help readers tackle complex system design questions effectively.", 2.99)
        );
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(9, "Python Programming", "9781234567890", "It is an excellent resource for those looking to learn Python from scratch. It covers fundamental concepts and practical examples to help beginners build a strong foundation in Python programming.", 4.49)
        );
        new InsertAsyncTask(productRoomDatabase).execute(
                new Product(10, "Data Wrangling with SQL", "9780987654321", "It is an indispensable guide for anyone working with data for data professionals and analysts with the skills needed to clean, reshape, and transform data effectively using SQL. It offers practical guidance on using SQL for data cleansing, transformation, and manipulation.", 6.99)
        );

        allProducts = productDao.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insertProduct(Product product) {
        new InsertAsyncTask(productRoomDatabase).execute(product);
//        productDao.insertProduct(product);
    }

    public void updateProduct(Product product) {
        new UpdateAsyncTask(productRoomDatabase).execute(product);
    }

    public void deleteProduct(Product product) {
        new DeleteAsyncTask(productRoomDatabase).execute(product);
    }

    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }

    static class InsertAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;
        InsertAsyncTask(ProductRoomDatabase productRoomDatabase){
            productDao = productRoomDatabase.productDao();
        }
        @Override
        protected Void doInBackground(Product... products){
            productDao.insertProduct(products[0]);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;
        DeleteAsyncTask(ProductRoomDatabase productRoomDatabase){
            productDao = productRoomDatabase.productDao();
        }
        @Override
        protected Void doInBackground(Product... products){
            productDao.deleteProduct(products[0]);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;
        UpdateAsyncTask(ProductRoomDatabase productRoomDatabase){
            productDao = productRoomDatabase.productDao();
        }
        @Override
        protected Void doInBackground(Product... products){
            productDao.updateProduct(products[0]);
            return null;
        }
    }
}
