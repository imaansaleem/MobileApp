package com.example.booksellingmobileapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksellingmobileapp.R;
import com.example.booksellingmobileapp.ViewModel.ProductViewModel;
import com.example.booksellingmobileapp.adapter.RVAdapter;


public class RVActivity extends AppCompatActivity{

    TextView title;
    RecyclerView RecyclerViewList;
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        initialize();

        RVAdapter adapter = new RVAdapter(RVActivity.this);
        RecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewList.setAdapter(adapter);

        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RVActivity.this, ProductInput.class);
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
