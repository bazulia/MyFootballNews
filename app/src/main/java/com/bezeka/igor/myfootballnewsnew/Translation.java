package com.bezeka.igor.myfootballnewsnew;

/**
 * Created by Igor on 17.02.2015.
 */
public class Translation {
    String text;
    String time;
    Event event;

    enum Event {
        goal, penalty, change, yellow, red, start_match, end_match, none, pause;
    }

    Translation(String text, String time, Event event) {
        this.event = event;
        this.text = text;
        this.time = time;
    }
}