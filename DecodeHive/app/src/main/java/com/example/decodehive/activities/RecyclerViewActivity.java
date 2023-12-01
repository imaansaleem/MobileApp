package com.example.decodehive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.decodehive.Databases.Entities.Product;
import com.example.decodehive.Interfaces.IRecyclerViewData;
import com.example.decodehive.R;
import com.example.decodehive.ViewModel.ProductViewModel;
import com.example.decodehive.adapter.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements IRecyclerViewData {


    TextView title;
    androidx.recyclerview.widget.RecyclerView RecyclerViewList;
    LiveData<List<Product>> listData = new LiveData<List<Product>>() {};
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initialize();
        
        listData = productViewModel.getAllProducts();
        RVAdapter adapter = new RVAdapter(RecyclerViewActivity.this, listData.getValue(), this);
        RecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewList.setAdapter(adapter);

        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewActivity.this, ProductInput.class);
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

    @Override
    public void sendData(Product listData) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", listData);

        //intent allows communication between activity and fragments
        Intent intent = new Intent(RecyclerViewActivity.this, BookDetails.class);

        //sending data to Welcome
        intent.putExtras(bundle);

        //startnext activity
        startActivity(intent);
    }
}