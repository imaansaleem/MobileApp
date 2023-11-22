package com.example.booksellingmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.booksellingmobileapp.Databases.Entities.Product;
import com.example.booksellingmobileapp.ViewModel.ProductViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>(){
            @Override
            public void onChanged(List<Product> products) {
                Toast.makeText(MainActivity.this, "fine", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
