package com.example.android.abnproject6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class NewsAdapter extends ArrayAdapter<News> {
    /**
     * used for date formatting
     */
    private static final String DATE_SEPARATOR = "T";

    public NewsAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // creating a local View
        View localView = convertView;

        //check the existing view is reused or else inflate the view
        if (localView == null){
            localView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent,false);
        }

        News newsItem = getItem(position);

        //connect to the layout and prepare to set values via Object attributes
        TextView newsTitle = (TextView) localView.findViewById(R.id.webtitle);
        TextView newsAuthor = (TextView) localView.findViewById(R.id.author);
        TextView newsUrl = (TextView) localView.findViewById(R.id.url);
        TextView newsPublishedDate = (TextView) localView.findViewById(R.id.published_date);
        TextView newsDescription = (TextView) localView.findViewById(R.id.sectionname);


        // setting values that will be returned to locationView from getting items from Location object in position
        newsTitle.setText(newsItem.getWebTitle());
        newsAuthor.setText(newsItem.getAuthor());
        newsUrl.setText(newsItem.getUrl());
        newsUrl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_newspaper, 0,0,0);
        newsUrl.setCompoundDrawablePadding(20);
        newsDescription.setText(newsItem.getSectionName());

        // javascript DateTime format is processed to readable Date format
        String rawDate = newsItem.getPublishDate();
        String processedDate = null;
        // the time element is stripped from DateTime
        if(rawDate.contains(DATE_SEPARATOR)) {
            String[] parts = rawDate.split(DATE_SEPARATOR);
            processedDate = parts[0];
        }

        // given the datetime yyyy-MM-dd format, MMM dd, yyyy is shown to the TextView
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newDate = null;
        try {
            newDate = sdf.parse(processedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        processedDate = sdf.format(newDate);

        newsPublishedDate.setText(processedDate);

        return localView;
    }


}
