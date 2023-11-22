package com.example.booksellingmobileapp.Databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.booksellingmobileapp.Databases.DAOs.ProductDao;
import com.example.booksellingmobileapp.Databases.Entities.Product;

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
        //productDao.insertProduct(product);
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public void deleteProduct(Product product) {
        productDao.deleteProduct(product);
    }

    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }

    class InsertAsyncTask extends AsyncTask<Product, Void, Void> {
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
}
