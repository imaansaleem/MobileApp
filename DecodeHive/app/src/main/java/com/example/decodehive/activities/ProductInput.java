package com.example.decodehive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.decodehive.Databases.Entities.Product;
import com.example.decodehive.R;
import com.example.decodehive.ViewModel.ProductViewModel;

public class ProductInput extends AppCompatActivity {
    EditText editTextBookName;
    EditText editTextISBN;
    EditText editTextDescription;
    EditText editTextPrice;
    Button btnAddProduct;
    ProductViewModel productViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_input);
        initialize();
        AddProductToDatabase();
    }

    void initialize() {
        editTextBookName = findViewById(R.id.editTextBookName);
        editTextISBN = findViewById(R.id.editTextISBN);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPrice = findViewById(R.id.editTextPrice);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
    }

    public void AddProductToDatabase() {
        final boolean[] isValid = {true}; // Flag to check overall validity
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName = editTextBookName.getText().toString();
                String ISBN = editTextISBN.getText().toString();
                String description = editTextDescription.getText().toString();
                String priceStr = editTextPrice.getText().toString();

                if (bookName.isEmpty()) {
                    // Validation failed, show an error or toast indicating fields are required
                    editTextBookName.setError("BookName must not be Empty");
                    isValid[0] = false;
                }

                if(ISBN.isEmpty()){
                    editTextISBN.setError("ISBN must not be Empty");
                    isValid[0] = false;
                }

                if(description.isEmpty()){
                    editTextDescription.setError("Description must not be Empty");
                    isValid[0] = false;
                }

                if(priceStr.isEmpty()){
                    editTextPrice.setError("Price must not be Empty");
                    isValid[0] = false;
                }

                double price;
                try {
                    price = Double.parseDouble(priceStr);
                    // Additional checks for negative prices or other constraints if needed
                    if (price < 0) {
                        editTextPrice.setError("Price cannot be negative");
                        isValid[0] = false;
                    }
                } catch (NumberFormatException e) {
                    // Invalid price format
                    Toast.makeText(getApplicationContext(), "Enter a valid price", Toast.LENGTH_SHORT).show();
                    isValid[0] = false;
                }
            }
        });

        if(isValid[0]){
            String bookName = editTextBookName.getText().toString();
            String ISBN = editTextISBN.getText().toString();
            String description = editTextDescription.getText().toString();
            double price = Double.parseDouble(editTextPrice.getText().toString());

            // Create a Product object with the input data
            Product newProduct = new Product();
            newProduct.setBookName(bookName);
            newProduct.setISBN(ISBN);
            newProduct.setDescription(description);
            newProduct.setPrice(price);

            // Assuming you have a ProductViewModel instance named productViewModel
            productViewModel.insertProduct(newProduct);

//            Intent intent = new Intent(ProductInput.this, RVActivity.class);
//            startActivity(intent);
        }

    }
}