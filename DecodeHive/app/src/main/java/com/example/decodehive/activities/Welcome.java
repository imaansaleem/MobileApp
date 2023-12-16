package com.example.decodehive.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.decodehive.R;

public class Welcome extends AppCompatActivity {

    TextView welcome;
    Button start, cart, signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initialize();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String name = bundle.getString("name", "User");
        String text = "WELCOME\n" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase() + "!";
        welcome.setText(text);
        clickListeners();
    }

    void initialize(){
        welcome = findViewById(R.id.welcomeText);
        start = findViewById(R.id.letsGo);
        cart = findViewById(R.id.my_cart);
        signout = findViewById(R.id.signout_button);
    }

    public void clickListeners(){
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }
}