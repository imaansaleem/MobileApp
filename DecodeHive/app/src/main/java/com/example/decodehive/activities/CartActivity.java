package com.example.decodehive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.decodehive.Databases.Entities.Product;
import com.example.decodehive.Interfaces.IRecyclerViewData;
import com.example.decodehive.Databases.Entities.User;
import com.example.decodehive.R;
import com.example.decodehive.adapter.RVAdapter;
import com.example.decodehive.fragments.CartItem;

import java.util.List;


public class CartActivity extends AppCompatActivity implements IRecyclerViewData {

    User user;
    List<Product> cart;
    RecyclerView cartList;
    double totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        user = User.getLoggedInUser();
        if(user == null) {
            return;
        }

        Log.d("here", "User is logged in");
        cart = user.getCart();
        if(cart == null || cart.size() == 0) {
            return;
        }

        Log.d("here", "Cart is not empty");
        cartList = findViewById(R.id.cart_recyclerViewArea);

        cart.forEach(item -> totalPrice += item.getPrice());

        RVAdapter adapter = new RVAdapter(CartActivity.this, cart, this);
        adapter.setProductList(cart);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        cartList.setAdapter(adapter);

        CartItem cartItemFragment = (CartItem) getSupportFragmentManager().findFragmentById(R.id.cart_item_fragment);
        assert cartItemFragment != null;
        cartItemFragment.setTotalPrice(String.valueOf(totalPrice));

        // Find the button within the CartItem fragment
        Button placeOrderButton = cartItemFragment.requireView().findViewById(R.id.place_order_button);

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "Your order have been placed. Happy Shopping!", Toast.LENGTH_SHORT).show();
                user.emptyCart();
                finish();
            }
        });
    }

    @Override
    public void sendData(Product listData) { }
}