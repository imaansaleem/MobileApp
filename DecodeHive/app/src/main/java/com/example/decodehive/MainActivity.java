package com.example.decodehive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.decodehive.Databases.Entities.Product;
import com.example.decodehive.ViewModel.ProductViewModel;
import com.example.decodehive.activities.ProductInput;

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
                if (products != null && !products.isEmpty()) {
                    // Products have loaded, navigate to the next screen
                    navigateToNextScreen();
                } else {
                    // Handle the case where no products are available
                    Toast.makeText(MainActivity.this, "No products available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navigateToNextScreen() {
        Intent intent = new Intent(MainActivity.this, ProductInput.class);
        startActivity(intent);
    }
}