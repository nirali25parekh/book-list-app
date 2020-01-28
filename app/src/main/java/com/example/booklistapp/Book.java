package com.example.booklistapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Book /*implements Parcelable*/ {

    private final String title;
    private final String author;
    private final String imageUrl;
    private final Double price;
    private final String currency;
    private final String language;
    private String urlBook;

    public Book(String bookTitle, String authorName, String urlImageCover, Double bookPrice, String currency, String languageCode, String buyLink) {
        this.title = bookTitle;
        this.author = authorName;
        this.imageUrl = urlImageCover;
        this.price = bookPrice;
        this.currency = currency;
        this.language = languageCode;
        this.urlBook = buyLink;

    }

//    protected Book(Parcel in) {
//        this.title = in.readString();
//        this.author = in.readString();
//        this.imageUrl = in.readString();
//        this.price = (Double) in.readValue(Double.class.getClassLoader());
//        this.currency = in.readString();
//        this.urlBook = in.readString();
//        this.language = in.readString();
//    }


    // Getters
    public String getTitle()  {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public Double getPrice() {
        return price;
    }
    public String getCurrency() {
        return currency;
    }
    public String getUrlBook() {
        return urlBook;
    }
    public String getLanguage() {
        return language;
    }

//    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
//
//        @Override
//        public Book createFromParcel(Parcel source) {
//            return new Book(source);
//        }
//
//        @Override
//        public Book[] newArray(int size) {
//            return new Book[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.title);
//        dest.writeString(this.author);
//        dest.writeString(this.imageUrl);
//        dest.writeValue(this.price);
//        dest.writeString(this.currency);
//        dest.writeString(this.urlBook);
//        dest.writeString(this.language);
//    }

}
