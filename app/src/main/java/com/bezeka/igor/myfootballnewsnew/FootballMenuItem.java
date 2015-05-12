package com.bezeka.igor.myfootballnewsnew;

/**
 * Created by Igor on 12.02.2015.
 */
public class FootballMenuItem {

    String title;
    String link;

    FootballMenuItem(){ }

    FootballMenuItem(String title,String link){
        this.title=title;
        this.link=link;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
