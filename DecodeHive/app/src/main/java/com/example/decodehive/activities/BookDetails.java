package com.example.decodehive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

                //diaglogue box
                CustomDialogueBox(item);
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

    void CustomDialogueBox(Product item){

        // custom dialogue box
        // create xml with any type of designing
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.xml_dialog_id);

        //sticking to screen
        dialog.setCancelable(true);

        Window window = dialog.getWindow();

        // Set fixed width for the dialog window
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.height = 500;
        window.setAttributes(layoutParams);

        // Find views in the dialog layout
        TextView titleTextView = dialog.findViewById(R.id.titleTextView);
        Button yesButton = dialog.findViewById(R.id.yesButton);
        Button noButton = dialog.findViewById(R.id.noButton);

        // Set the text for the title
        if (titleTextView != null) {
            titleTextView.setText("Are you sure you want to delete this product?");
        }

        // Set click listeners for Yes and No buttons
        if (yesButton != null && noButton != null) {
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle 'Yes' button click
                    productViewModel.deleteProduct(item);
                    notification("Delete Product", "The Product has been updated successfully", R.drawable.delete);

                    finish();

                    dialog.dismiss(); // Dismiss the dialog
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle 'No' button click
                    dialog.dismiss(); // Dismiss the dialog
                }
            });
        }

        // Show the dialog
        dialog.show();
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


    private static final String CHANNEL_ID = "my_channel";
    private final int NOTIFICATION_ID = 100;
    void notification(String title, String message, int img) {

        // converting from int to drawable
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.book, null);

        // converting to bitmap
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        // getting access via manager
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            //before Oreo
            //// main title will be app name, subtext will be custom title
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(img)
                    .setContentText(message)
                    .setSubText(title)
                    .setChannelId(CHANNEL_ID)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));

        } else {
            // after oreo
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(img)
                    .setContentText(message)
                    .setSubText(title)
                    .build();
        }

        nm.notify(NOTIFICATION_ID, notification);
    }
}
