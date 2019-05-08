package cs4330.cs.utep.scheduleapp;

import android.os.AsyncTask;

import java.io.Serializable;
import java.util.List;

class User implements Serializable {
    protected String username;
    protected String first;
    protected String last;
    protected boolean admin;

    public User(String username, String first,String last,boolean admin){
        this.username = username;
        this.first = first;
        this.last = last;
        this.admin = admin;
    }
}
