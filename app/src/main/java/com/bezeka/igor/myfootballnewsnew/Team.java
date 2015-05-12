package com.bezeka.igor.myfootballnewsnew;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Igor on 12.02.2015.
 */
public class Team implements Parcelable{

    String pos;
    String name;
    String logoLink;
    String games;
    String score;

    Team(String name, String logoLink,String games,String score,String pos){
        this.logoLink = logoLink;
        this.name = name;
        this.games = games;
        this.score = score;
        this.pos = pos;
    }

    public Team(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        public Team[] newArray(int size) {

            return new Team[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(logoLink);
        parcel.writeString(games);
        parcel.writeString(score);
        parcel.writeString(pos);
    }

    private void readFromParcel(Parcel in) {
        name = in.readString();
        logoLink = in.readString();
        games = in.readString();
        score = in.readString();
        pos = in.readString();
    }
}
