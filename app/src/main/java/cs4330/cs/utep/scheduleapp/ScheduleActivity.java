package cs4330.cs.utep.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;

//This Activity is for when the user clicks on View Schedule //
public class ScheduleActivity extends AppCompatActivity {
    SchedAdapter schedAdapter;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    ListView listView;
    String name; //User//
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        name = i.getStringExtra("name");

        schedAdapter = new SchedAdapter(this,schedules);
        new DisplaySchedule(name,schedules,schedAdapter).execute();
        listView = findViewById(R.id.listView);
        listView.setAdapter(schedAdapter);
    }
}
