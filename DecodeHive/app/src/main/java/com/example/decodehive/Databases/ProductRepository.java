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
        allProducts = productDao.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insertProduct(Product product) {
        new InsertAsyncTask(productRoomDatabase).execute(product);
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
