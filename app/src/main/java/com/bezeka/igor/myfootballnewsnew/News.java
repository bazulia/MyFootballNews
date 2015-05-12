package com.bezeka.igor.myfootballnewsnew;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Igor on 14.02.2015.
 */
public class News implements Parcelable {

    String date;
    String title;
    String href;

    News(String date,String title, String href){
        this.date = date;
        this.title = title;
        this.href = href;
    }

    public News(Parcel in) {
        super();
        readFromParcel(in);
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {

            return new News[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(date);
        parcel.writeString(title);
        parcel.writeString(href);
    }

    private void readFromParcel(Parcel in) {
        date = in.readString();
        title = in.readString();
        href = in.readString();
    }
}
