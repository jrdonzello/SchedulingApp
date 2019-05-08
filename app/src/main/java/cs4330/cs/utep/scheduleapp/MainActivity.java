package cs4330.cs.utep.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private User user;

    TextView nameLabel;
    TextView nextShift;

    String currentDate;
    String currentDay;

    ListView listView;
    ArrayAdapter<String> adapter;
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

    protected void HomeScreen() {
        setContentView(R.layout.activity_home);

        getCurrentDate();
        getCurrentDay();

        nameLabel = findViewById(R.id.nameLabel);
        nameLabel.setText(user.first + " " + user.last);

        nextShift = findViewById(R.id.nextShift);

        getNextShift();

        getCurrentlyWorking();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeScreen();
                    return true;
                case R.id.navigation_viewSchedule:
                    //new DisplaySchedule().execute();
                    //Intent displaySched = new Intent(MainActivity.this,ScheduleActivity.class);
                    //displaySched.putExtra("user",user); // User //
                    //startActivity(displaySched);
                    //setContentView();
                    return true;
                case R.id.navigation_switchShifts:
                    //mTextMessage.setText(R.string.title_switchShifts);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = (User)getIntent().getSerializableExtra("user");
        HomeScreen();
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
