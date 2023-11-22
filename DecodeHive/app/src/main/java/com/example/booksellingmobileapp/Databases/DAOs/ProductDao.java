package com.example.booksellingmobileapp.Databases.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.booksellingmobileapp.Databases.Entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM Product WHERE id = :productId")
    Product getProductById(int productId);


    @Query("DELETE FROM Product")
    void deleteAll();

    //onConflict: duplicate data must be replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);
}
