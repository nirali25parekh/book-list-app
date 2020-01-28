package com.example.booklistapp;

import android.content.Context;
import android.util.Log;
import java.util.List;
import android.content.AsyncTaskLoader;


public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i("On start loading", ": Force loaded!");
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of books.
        List<Book> books = Utils.fetchBookData(mUrl);
        return books;

    }

}
