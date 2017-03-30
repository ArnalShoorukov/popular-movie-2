package com.example.arnal.movies.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Review implements Parcelable {
    private String content;
    private String id;
    private String author;
    private String url;

    protected Review(Parcel in) {
        content = in.readString();
        id = in.readString();
        author = in.readString();
        url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(url);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPojo [content = " + content + ", id = " + id + ", author = " + author + ", url = " + url + "]";
    }


}
