package cs4330.cs.utep.scheduleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeScreenActivity extends AppCompatActivity {

    private User user;
    private String currentDate;
    private String currentDay;

    TextView nameLabel;
    TextView nextShift;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    ArrayList<String> list;

    protected void getCurrentDate() {
        DateFormat df = new SimpleDateFormat("HH:mm:ssa");
        Date date = new Date();
        currentDate = df.format(date);
    }

    protected void getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if(day == 1) {
            currentDay = "Sunday";
        } if(day == 2) {
            currentDay = "Monday";
        } if(day == 3) {
            currentDay = "Tuesday";
        } if(day == 4) {
            currentDay = "Wednesday";
        } if(day == 5) {
            currentDay = "Thursday";
        } if(day == 6) {
            currentDay = "Friday";
        }if(day == 7) {
            currentDay = "Saturday";
        }
    }

    protected void getNextShift(){
        new HomeScreen(this, nextShift ).execute(user.username, currentDay, currentDate);
    }

    protected void getCurrentlyWorking() {
        list = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        new CurrentlyWorking(this, list, adapter).execute(currentDay, currentDate);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getCurrentDate();
        getCurrentDay();

        user = (User) getIntent().getSerializableExtra("user");

        nameLabel = findViewById(R.id.nameLabel);
        nameLabel.setText(user.first + " " + user.last);

        nextShift = findViewById(R.id.nextShift);
        getNextShift();

        getCurrentlyWorking();

    }

}
