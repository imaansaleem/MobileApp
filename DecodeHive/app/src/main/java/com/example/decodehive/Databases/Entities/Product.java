package com.example.decodehive.Databases.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id=0;
    String bookName;
    String ISBN;
    String description;
    double price;

    @Ignore
    public Product(Parcel in) {
        id = in.readInt();
        bookName = in.readString();
        ISBN = in.readString();
        description = in.readString();
        price = in.readDouble();
    }

    public Product() {}

    public Product(int id, String bookName, String ISBN, String description, double price) {
        this.id = id;
        this.bookName = bookName;
        this.ISBN = ISBN;
        this.description = description;
        this.price = price;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(bookName);
        dest.writeString(ISBN);
        dest.writeString(description);
        dest.writeDouble(price);
    }
}