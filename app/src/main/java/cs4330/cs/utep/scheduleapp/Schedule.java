package cs4330.cs.utep.scheduleapp;

import java.io.Serializable;

public class Schedule implements Serializable {

    protected String start;
    protected String end;
    protected String track;
    protected String day;
    protected String name;

    public Schedule(String start,String end,String day,String track,String name ){
        this.name = name;
        this.track = track;
        this.start = start;
        this.end = end;
        this.day = day;
    }
    public Schedule(){

    }
}
