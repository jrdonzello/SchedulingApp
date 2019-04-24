package cs4330.cs.utep.scheduleapp;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    String first;
    String last;
    Boolean admin;

    public User(String username, String first, String last, Boolean admin){
        this.username = username;
        this.first = first;
        this.last = last;
        this.admin = admin;
    }


}
