package com.example.decodehive.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.decodehive.R;

public class CartItem extends Fragment {

    TextView totalPrice;
    Button placeOrder;
    View.OnClickListener placeOrderClickListener;

    public CartItem() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_item, container, false);
        totalPrice = view.findViewById(R.id.total_price);
        placeOrder = view.findViewById(R.id.place_order_button);
        placeOrder.setOnClickListener(placeOrderClickListener);
        return view;
    }

    public void setTotalPrice(String price) {
        totalPrice.setText(String.format("$%.2s", price));
    }

    public void setPlaceOrderClickListener(View.OnClickListener listener) {
        this.placeOrderClickListener = listener;
    }
}