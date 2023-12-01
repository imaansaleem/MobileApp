package com.example.decodehive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.decodehive.Databases.Entities.User;
import com.example.decodehive.R;
import com.example.decodehive.ViewModel.ProductViewModel;
import com.example.decodehive.ViewModel.UserViewModel;

import java.util.ArrayList;

public class Signup extends AppCompatActivity {

    LinearLayout signinLinearLayout, signupLinearLayout;

    //sign_in_now /sign_up_now
    TextView signinText, signupText;
    //Email, username
    EditText signupEmail, signinName, signupName;
    //pasword
    EditText signinPassword, signupPassword, confirmPassword;
    Button signinButton, signupButton;
    String username = null, password = null, confirmPass = null, email = null;
    Spinner signinSignupSpinner;
    UserViewModel userViewModel;

    // to store all signedup users
    static ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Called when the app is executed for the first time
        super.onCreate(savedInstanceState);
        //link this activity to main file
        setContentView(R.layout.activity_signup);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //one user is always there
        addUser("imaan@gmail.com", "Imaan", "1234");
        User user = new User("imaan@gmail.com", "Imaan", "1234");
        userViewModel.inserUser(user);

        signinSignupSpinner = findViewById(R.id.signin_signup_options);
        signinLinearLayout = findViewById(R.id.signin_linear);
        signupLinearLayout = findViewById(R.id.signup_linear);

        initialize();
        initializeSpinner();
        clickListeners();
    }

    //Linking Xml with data members
    public void initialize() {
        signinText = findViewById(R.id.signin_text);
        signupText = findViewById(R.id.signup_text);
        signupEmail = findViewById(R.id.signup_email);
        signupName = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirm_password);
        signinName = findViewById(R.id.signin_username);
        signinPassword = findViewById(R.id.signin_password);
        signinButton = findViewById(R.id.signin_button);
        signupButton = findViewById(R.id.signup_button);
    }

    void initializeSpinner() {
        //setting up a Spinner widget
        //(arraylist of text to be displayed on spinner,  predefined layout resource )
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.login_signup_spinner, android.R.layout.simple_spinner_item);
        //layout is used for displaying the dropdown list
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter for the Spinner named signinSignupSpinner
        signinSignupSpinner.setAdapter(adapter);

        signinSignupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedSpinnerItem = signinSignupSpinner.getSelectedItem().toString();
                if (selectedSpinnerItem.equals("Signin")) {
                    signinLinearLayout.setVisibility(View.VISIBLE);
                    signupLinearLayout.setVisibility(View.GONE);
                } else if (selectedSpinnerItem.equals("Signup")) {
                    signinLinearLayout.setVisibility(View.GONE);
                    signupLinearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                signinLinearLayout.setVisibility(View.VISIBLE);
                signupLinearLayout.setVisibility(View.GONE);
            }
        });
    }

    public void clickListeners() {
        signinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinSignupSpinner.setSelection(0);
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinSignupSpinner.setSelection(1);
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    username = "Imaan";
                    password="1234";
//                username = signinName.getText().toString();
//                password = signinPassword.getText().toString();
                //data stored to data members

                if (username == null || username.equals("")) {
                    //set error to field
                    signinName.setError("Invalid Username");
                } else if (password.equals("")) {
                    //set error to field
                    signinPassword.setError("There must be a password");
                } else {
//                    User currentUser = validateUser(username, password);
                    User currentUser = userViewModel.getUser(email, username, password);
                    if (currentUser != null) {
                        User.setLoggedInUser(currentUser);
                        //Log.d(TAG, "Your debug message here");
                        //TAG is a string constant that identifies the source or category of the log message. It's often used to group related log messages together.
                        // For example, you might use a TAG like "MainActivity" or "NetworkRequest" to indicate where the log message is coming from
                        //The second argument is the actual message you want to log. It's a string that contains information or details about the current state
                        // or operation of your application.


                        Log.d("Sigin", currentUser.getUsername() + " " + password);

                        //show A notification
                        Toast.makeText(Signup.this, "Hey " + username + "! You are good to go", Toast.LENGTH_SHORT).show();

                        //You need to send data to next activity
                        //Bundle is a data structure used for passing data between different components of an Android application, such as between activities, fragments,
                        //Bundle is to store key-value pairs of data, where keys are strings, and values can be various data types
                        Bundle bundle = new Bundle();
                        bundle.putString("name", username);

                        //intent allows communication between activity and fragments
                        Intent intent = new Intent(Signup.this, Welcome.class);

                        //sending data to Welcome
                        intent.putExtras(bundle);

                        //startnext activity
                        startActivity(intent);
                        finish();
                    } else {
                        signinName.setError("Incorrect Username or Password");
                        signinPassword.setError("Incorrect Username or Password");
                    }
                }
            }

        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = signupName.getText().toString();
                email = signupEmail.getText().toString();
                password = signupPassword.getText().toString();
                confirmPass = confirmPassword.getText().toString();

                if (email == null || email.equals("")) {
                    //set error to field
                    signupEmail.setError("Invalid Email");
                } else if (username == null || username.equals("")) {
                    //set error to field
                    signupName.setError("Invalid Username");
                } else if (password.equals("")) {
                    //set error to field
                    signupPassword.setError("There must be a password");
                } else if (confirmPass.equals("")) {
                    //set error to field
                    confirmPassword.setError("There must be a password");
                } else if (!password.equals(confirmPass)) {
                    signupPassword.setError("Both passwords must be same");
                    confirmPassword.setError("Both passwords must be same");
                } else {
                    addUser(email, username, password);
                    Toast.makeText(Signup.this, "Sign up completed! Please Signin now", Toast.LENGTH_SHORT).show();
                    signinSignupSpinner.setSelection(0); // go back to sign in screen
                }
            }
        });
    }

    //if user signs up check if this exists
    User validateUser(String username, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)) {
                return users.get(i);
            }
        }

        return null;
    }

    void addUser(String email, String username, String password) {
        User user = new User(email, username, password);
        users.add(user);
    }

}