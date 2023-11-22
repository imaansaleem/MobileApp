package com.example.booksellingmobileapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksellingmobileapp.Databases.Entities.Product;
import com.example.booksellingmobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
    private Context context;
    private List<Product> listData;

    public RVAdapter(Context context) {
        this.context = context;
        this.listData = new ArrayList<>();
    }

    public void setProductList(List<Product> productList) {
        this.listData = productList;
        notifyDataSetChanged(); // Notify the adapter that data set has changed
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.product_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //position tells place at this position in recycler view
        //setting data in recycler view
        holder.name.setText(listData.get(position).getBookName());
        //setting Text to bold
        holder.name.setTypeface(null, Typeface.BOLD);
        holder.price.setText(HtmlCompat.fromHtml("<b>Price: </b>$" + listData.get(position).getPrice(), HtmlCompat.FROM_HTML_MODE_LEGACY, null, null));
        holder.isbn.setText(HtmlCompat.fromHtml("<b>ISBN: </b>" + listData.get(position).getISBN(), HtmlCompat.FROM_HTML_MODE_LEGACY, null, null));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, isbn;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bookName);
            price = itemView.findViewById(R.id.bookPrice);
            isbn = itemView.findViewById(R.id.bookISBN);
            layout = itemView.findViewById(R.id.product_item_linear);
        }
    }
}
