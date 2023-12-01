package com.example.decodehive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.decodehive.R;
import com.example.decodehive.ViewModel.ProductViewModel;
import com.example.decodehive.adapter.RVAdapter;

public class RecyclerView extends AppCompatActivity {


    TextView title;
    androidx.recyclerview.widget.RecyclerView RecyclerViewList;
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initialize();

        RVAdapter adapter = new RVAdapter(RecyclerView.this);
        RecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewList.setAdapter(adapter);

        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerView.this, ProductInput.class);
                startActivity(intent);
            }
        });

        productViewModel.getAllProducts().observe(this, productList -> {
            // Update the adapter when the LiveData changes
            adapter.setProductList(productList);
            RecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
            // Notify the RecyclerView of changes
            RecyclerViewList.setAdapter(adapter);
        });
    }

    void initialize(){
        title = findViewById(R.id.Title);
        RecyclerViewList = findViewById(R.id.recyclerViewArea);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
    }
}