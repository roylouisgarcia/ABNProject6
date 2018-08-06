package com.example.android.abnproject6;

import android.content.Context;

import android.support.annotation.Nullable;

import android.content.AsyncTaskLoader;


import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /** establish the log tag */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** variable for the JSON query url */
    private String mJSONUrl;

    /**
     * {@Link NewsLoader} constructor
     * @param context
     * @param jsonURL
     */
    public NewsLoader(Context context, String jsonURL) {
        super(context);
        mJSONUrl = jsonURL;
    }

    /**
     * Load data in a background thread
     *
     */
    protected void onStartLoading() { forceLoad();}

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if (mJSONUrl == null){
            return null;
        }

        /**
         * Calls the {@link Utils} fetchNewsData that sends network request, parse the response and extract the data needed
         */
        List<News> news = Utils.fetchNewsData(mJSONUrl);
        return news;
    }


}
