package com.bezeka.igor.myfootballnewsnew;

/**
 * Created by Igor on 17.02.2015.
 */
public class Goal {
    String name;
    String time;
    Type type;

    enum Type {
        Left, Right
    }
    Goal(String name, String time,Type type){
        this.type = type;
        this.name = name;
        this.time = time;
    }
}
