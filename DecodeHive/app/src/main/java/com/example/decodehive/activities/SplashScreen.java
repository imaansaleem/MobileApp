package com.example.decodehive.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.decodehive.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Handler object and uses its postDelayed method to schedule a task to be executed after a specified delay.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // this function will execute after 3000 milliseconds (3 seconds)
//                Intent intent = new Intent(SplashScreen.this, Signup.class);
                Intent intent = new Intent(SplashScreen.this, Signup.class);
                //The startActivity method is called with the Intent, indicating that the MainActivity is going to be started.
                startActivity(intent);
                //SplashScreen activity is being closed and removed from the activity stack.
                finish();
            }
        }, 3000);
    }
}