package com.example.decodehive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.decodehive.Databases.Entities.Product;
import com.example.decodehive.Databases.Entities.User;
import com.example.decodehive.R;
import com.example.decodehive.ViewModel.ProductViewModel;


public class BookDetails extends AppCompatActivity {
    TextView title, description, isbn, price, Aboutbook;
    Button cartButton, editButton, deleteButton;
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        initialize();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Product item = (Product) bundle.getParcelable("data");

        assert item != null;
        title.setText(item.getBookName());
        //setting Text to bold
        Aboutbook.setTypeface(null, Typeface.BOLD);
        description.setText(item.getDescription());
        isbn.setText(HtmlCompat.fromHtml("<b>ISBN: </b>" + item.getISBN(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        price.setText(HtmlCompat.fromHtml("<b>Price: </b>$" + item.getPrice(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        setupCartButton(item);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productViewModel.deleteProduct(item);
                Toast.makeText(BookDetails.this, "Item has been succesfully deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("name", item.getBookName());
                bundle.putString("description", item.getDescription());
                bundle.putString("isbn", item.getISBN());
                bundle.putString("price", String.valueOf(item.getPrice()));

                //intent allows communication between activity and fragments
                Intent intent = new Intent(BookDetails.this, ProductInput.class);

                //sending data to Welcome
                intent.putExtras(bundle);

                //startnext activity
                startActivity(intent);
                finish();
            }
        });
    }

    void initialize() {
        title = findViewById(R.id.details_title);
        description = findViewById(R.id.details_description);
        isbn = findViewById(R.id.details_isbn);
        price = findViewById(R.id.details_price);
        cartButton = findViewById(R.id.add_to_cart);
        Aboutbook = findViewById(R.id.header);
        editButton = findViewById(R.id.edit_product);
        deleteButton = findViewById(R.id.delete_product);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
    }

    void setupCartButton(Product item) {
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getLoggedInUser();
                if(user != null) {
                    user.addToCart(item);
                    Toast.makeText(BookDetails.this, "Item has been succesfully added to cart", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
