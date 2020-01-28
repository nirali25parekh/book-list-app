package com.example.booklistapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book>  {


    public BookAdapter(Activity context, ArrayList<Book> Books) {
        /*
         Here, we initialize the ArrayAdapter's internal storage for the context and the list.
         the second argument is used when the ArrayAdapter is populating a single TextView.
         Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
         going to use this second argument, so it can be any value. Here, we used 0.
        */
        super(context, 0, Books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the current position of Book
        final Book currentBook = getItem(position);

        // Find the TextView in the list_item.xml (mapping)
        TextView titleBookTextView = (TextView) listItemView.findViewById(R.id.book_title);
        TextView authorBookTextView = (TextView) listItemView.findViewById(R.id.author);
        ImageView coverImageView = (ImageView) listItemView.findViewById(R.id.cover_image);
        TextView priceBookTextView = (TextView) listItemView.findViewById(R.id.book_price);
        TextView languageCode = (TextView) listItemView.findViewById(R.id.country_code);
        TextView currencyCode = (TextView) listItemView.findViewById(R.id.currency_code);

        // Set proper value in each fields
        titleBookTextView.setText(currentBook.getTitle());
        authorBookTextView.setText(currentBook.getAuthor());
//        //Picasso.get()
//                .load("http://i.imgur.com/DvpvklR.png")
//                .resize(50, 50)
//                .centerCrop()
//                .into(coverImageView);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(coverImageView);
        Picasso.get().load(currentBook.getImageUrl()).into(coverImageView);
        priceBookTextView.setText(String.valueOf(formatPrice(currentBook.getPrice())));
        languageCode.setText(currentBook.getLanguage());
        currencyCode.setText(currentBook.getCurrency());

        return listItemView;

    }

    // Format with two decimal places for price value
    private String formatPrice(double price) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.00");
        return magnitudeFormat.format(price);
    }

}
