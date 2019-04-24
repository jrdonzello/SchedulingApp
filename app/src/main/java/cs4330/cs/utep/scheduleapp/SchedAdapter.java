package cs4330.cs.utep.scheduleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SchedAdapter extends ArrayAdapter<Schedule> {
    private final Context context;
    protected List<Schedule> schedules;

    public SchedAdapter(Context context, List<Schedule> schedules){
        super(context,-1,schedules);
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.display_schedule_view, parent, false);
        TextView time = (TextView) rowView.findViewById(R.id.timeView);
        TextView day = (TextView) rowView.findViewById(R.id.dayView);
        TextView track = (TextView) rowView.findViewById(R.id.trackView);

        time.setText(schedules.get(position).start + " - " + schedules.get(position).end);
        day.setText(schedules.get(position).day);
        String formatTrack = schedules.get(position).track;
        if (formatTrack.contains("GAIA")) {
            schedules.get(position).track = "GAIA";
        }
        if(formatTrack.contains("Wrkshp")){
            schedules.get(position).track = "Workshop/3D PrinterOS";
        }

        track.setText(schedules.get(position).track);

        return rowView;

    }
}
