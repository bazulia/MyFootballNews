package com.bezeka.igor.myfootballnewsnew;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Igor on 21.04.2015.
 */
public class Article implements Parcelable {

    String title;
    String text;
    String srcImage;
    String link;

    /**
     * @param title
     * @param text
     * @param srcImage
     */
    Article(String title,String text,String srcImage, String link){
        this.title = title;
        this.text = text;
        this.srcImage = srcImage;
        this.link = link;
    }

    public Article(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {

            return new Article[size];
        }

    };

    private void readFromParcel(Parcel in) {
        title = in.readString();
        text = in.readString();
        srcImage = in.readString();
        link = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(srcImage);
        dest.writeString(link);
    }
}
