package com.example.android.abnproject6;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>>{

    /** Log tag for debugging*/
    private static  final String LOG_TAG = MainActivity.class.getName();
    /** news loader id */
    private static final int NEWS_LOADER_ID = 1;
     /** TextView to be displayed when the list of news is empty */
    private TextView mEmptyList;
    /** Progress bar displayed when loader is loading*/
    private ProgressBar mProgressBar;

    /** URL for the news data from https://newsapi.org */
    private static final String NEWSAPI_REQUEST_URL = "https://content.guardianapis.com/search?q=%22Radiohead%22%20AND%20NOT%20Lana%20del%20Rey&show-tags=contributor&api-key=";

    private String MY_API_KEY = "";

    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** This links the listview news_item.xml listview */
        ListView newsListView = (ListView) findViewById(R.id.list);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(mAdapter);


        /** this makes each news item clickable to open a browser that handles the Intent */
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });


        // Aside from the logo image, an empty list is given when loading
        mEmptyList = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyList);

        // Calls method that handles network connectivity.
        checkNetwork();
    }

    /**
     * Method that handles the network connectivity and loader processing
     */
    private void checkNetwork() {
        // Get a reference to the ConnectivityManager and checks for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get the info for network connectivity
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // If there is connection, we let the loaders start fetching for data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Assign a LoaderManager to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // when connected and data pupulate UI, hide the progress bar
            mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
            mProgressBar.setVisibility(View.GONE);

            //  "No internet connection." prompt in case connection is unavailable
            mEmptyList.setText(R.string.no_internet);
        }
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, NEWSAPI_REQUEST_URL);
    }

    /**
     * Called when {@link NewsLoader} is finished fetching data.
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> News) {

        // setVisibility to View.GONE when data is retrieved and will be shown to the UL
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        // If not news item retrieved, display "No News found."
        mEmptyList.setText(R.string.no_news_found);

        // garbage collection
        mAdapter.clear();

        // When data is retrieved, add them to the NewsAdapter
        if (News != null && !News.isEmpty()) {
            mAdapter.addAll(News);
        }
    }

    //When focus is taken away from the app and returned, onLoaderReset is called
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.i(LOG_TAG, "onLoaderReset() called");
        mAdapter.clear();
    }
}