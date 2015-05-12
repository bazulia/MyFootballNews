package com.bezeka.igor.myfootballnewsnew;

/**
 * Created by Igor on 17.02.2015.
 */
public class Warning {
    String name;
    String time;
    Type type;
    Card card;
    enum Type {
        Left, Right
    }
    enum Card {
        Yellow, Red
    }
    Warning(String name, String time,Type type,Card card){
        this.card = card;
        this.type = type;
        this.name = name;
        this.time = time;
    }
}
