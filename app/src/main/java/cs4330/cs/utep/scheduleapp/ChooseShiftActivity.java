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
    User currentUser;
    User selectedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_shift);

        Intent i = getIntent();
        currentUser = (User) i.getSerializableExtra("currentUser");
        selectedUser = (User) i.getSerializableExtra("selectedUser");


        schedAdapter = new SchedAdapter(this,schedules);
        new DisplaySchedule(selectedUser.username,schedules,schedAdapter).execute();
        listView = findViewById(R.id.shiftListView);
        listView.setAdapter(schedAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Schedule shift = (Schedule) adapterView.getItemAtPosition(i);
                Intent intent1 = new Intent(ChooseShiftActivity.this,GiveShiftActivity.class);
                intent1.putExtra("currentUser",currentUser);
                intent1.putExtra("selectedUser",selectedUser);
                intent1.putExtra("shift",shift);
                startActivity(intent1);

            }
        });
    }
}
