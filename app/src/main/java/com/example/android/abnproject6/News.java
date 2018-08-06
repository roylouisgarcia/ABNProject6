package com.example.android.abnproject6;

public class News {
    private String mWebTitle;
    private String mSectionName;
    private String mAuthor;
    private String mUrl;
    private String mPublishDate;

    public News(String webTitle, String sectionName, String author, String url, String publishDate) {
        this.mWebTitle = webTitle;
        this.mSectionName = sectionName;
        this.mAuthor = author;
        this.mUrl = url;
        this.mPublishDate = publishDate;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getPublishDate() {
        return mPublishDate;
    }
}
