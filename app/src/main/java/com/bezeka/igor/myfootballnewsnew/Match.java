package com.bezeka.igor.myfootballnewsnew;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Igor on 15.02.2015.
 */
public class Match implements Parcelable {
    String leftTeam;
    String rightTeam;
    String score;
    String time;
    String link;
    String otherInfo;
    int state;

    Match(String leftTeam,  String rightTeam,   String score,
          String time,      String link,        int state,
          String otherInfo){
        this.leftTeam = leftTeam;
        this.rightTeam = rightTeam;
        this.score = score;
        this.time = time;
        this.link = link;
        this.otherInfo = otherInfo;
        this.state = state;
    }

    public Match(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        public Match[] newArray(int size) {

            return new Match[size];
        }

    };

    private void readFromParcel(Parcel in) {
        leftTeam = in.readString();
        rightTeam = in.readString();
        score = in.readString();
        time = in.readString();
        link = in.readString();
        otherInfo = in.readString();
        state = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(leftTeam);
        dest.writeString(rightTeam);
        dest.writeString(score);
        dest.writeString(time);
        dest.writeString(link);
        dest.writeString(otherInfo);
        dest.writeInt(state);
    }
}
