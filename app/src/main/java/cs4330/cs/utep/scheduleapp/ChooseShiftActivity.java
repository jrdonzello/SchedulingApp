package cs4330.cs.utep.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseShiftActivity extends AppCompatActivity {
    SchedAdapter schedAdapter;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    ListView listView;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_shift);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("user");

        schedAdapter = new SchedAdapter(this,schedules);
        new DisplaySchedule(user.username,schedules,schedAdapter).execute();
        listView = findViewById(R.id.shiftListView);
        listView.setAdapter(schedAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Switch Shift Here //
                User user = (User) adapterView.getItemAtPosition(i);


            }
        });
    }
}
