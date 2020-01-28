package com.example.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncTaskLoader;
import java.net.URL;
import java.util.List;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOK_LOADER_ID = 1;
    ListView bookListView; //the list of books
    boolean isConnected = true; //is internet connected or not
    private String mUrlRequestGoogleBooks = ""; //url string
    private TextView mEmptyStateTextView; //if no books
    private View circleProgressBar; //progress bar
    private BookAdapter mAdapter;
    private SearchView mSearchViewField;
    private Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* connect xml ids to java objects */
        bookListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        circleProgressBar = findViewById(R.id.loading_spinner);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mSearchViewField = (SearchView) findViewById(R.id.search_view_field);

        circleProgressBar.setVisibility(GONE);
        mEmptyStateTextView.setVisibility(View.GONE);
        /*
         * At the beginning check the connection with internet and save result to (boolean) variable isConnected
         * Checking if network is available
         * If TRUE - work with LoaderManager
         * If FALSE - hide loading spinner and show emptyStateTextView
         */
        final ConnectivityManager connMgr =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        checkConnection(connMgr);

        /* Create a new adapter that takes an empty list of books as input and set the adapter on
         * the list view*/
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);
        bookListView.setEmptyView(mEmptyStateTextView);

//        mSearchViewField.onActionViewExpanded();
//        mSearchViewField.setIconified(true);

        if (isConnected) {
            /* Get a reference to the LoaderManager, in order to interact with loaders.
             * Initialize the loader. */
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            /* Set empty state text to display "No internet connection." */
            circleProgressBar.setVisibility(GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("No internet Connection");
        }

        /*
         Set an item click listener on the Search Button, which sends a request to
         Google Books API based on value from Search View
         */
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Check connection status */
                checkConnection(connMgr);

                if (isConnected) {
                    /* Update URL and restart loader to displaying new result of searching */
                    updateQueryUrl(mSearchViewField.getQuery().toString());
                    restartLoader();
                } else {
                    /* Clear the adapter of previous book data
                     * Set mEmptyStateTextView visible  */
                    mAdapter.clear();
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText("No internet Connection");
                }
            }
        });

        /*
         Set an item click listener on the ListView, which sends an intent to a web browser
         to open a website with more information about the selected book.
        */
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /* Find the current book that was clicked on */
                Book currentBook = mAdapter.getItem(position);

                /* Convert the String URL into a URI object (to pass into the Intent constructor) */
                /** assert currentBook != null; */
                Uri buyBookUri = Uri.parse(currentBook.getUrlBook());

                /* Create a new intent to view buy the book URI
                * and send the intent */
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, buyBookUri);
                startActivity(websiteIntent);
            }
        });

    }//onCreate over

    /* @param searchValue - user data from SearchView
     * @return improved String URL for making HTTP request */
    private String updateQueryUrl(String searchValue) {

        if (searchValue.contains(" ")) {
            searchValue = searchValue.replace(" ", "+");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.googleapis.com/books/v1/volumes?q=").append(searchValue).append("&filter=paid-ebooks&maxResults=40");
        mUrlRequestGoogleBooks = stringBuilder.toString();
        return mUrlRequestGoogleBooks;
    }

        /** check if updateQueryUrl function call necessary or not */
        @Override
        public Loader<List<Book>> onCreateLoader ( int i, Bundle bundle){
        /* Extract the url from search box and
        Create a new loader for the given URL */
            updateQueryUrl(mSearchViewField.getQuery().toString());
            return new BookLoader(this, mUrlRequestGoogleBooks);
        }

        @Override
        public void onLoadFinished (Loader <List< Book >> loader, List < Book > books){
            /* remove progress bar
             * set empty state text to "No Books Found"
             * Clear the adapter of previous book data*/

            circleProgressBar.setVisibility(GONE);
            //mEmptyStateTextView.setText("No Books Found");
            mAdapter.clear();

        /*
         If there is a valid list of Books, then add them to the adapter's
         data set. This will trigger the ListView to update.
        */
            if (books != null && !books.isEmpty()) {
                mAdapter.addAll(books);
            }

        }

    public void restartLoader() {
        mEmptyStateTextView.setVisibility(GONE);
        circleProgressBar.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        /* Loader reset, so we can clear out our existing data. */
        mAdapter.clear();
    }

    public void checkConnection(ConnectivityManager connectivityManager) {
        // Status of internet connection
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isConnected = true;
        } else {
            isConnected = false;
        }
    }
}
