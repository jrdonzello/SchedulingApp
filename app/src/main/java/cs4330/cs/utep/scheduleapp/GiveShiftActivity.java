package cs4330.cs.utep.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class GiveShiftActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    SchedAdapter schedAdapter;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    ListView listView;
    User currentUser;
    User selectedUser;
    Schedule shift;
    Schedule swapShift; //current users shift //
    String response = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shifts);

        Intent i = getIntent();
        currentUser = (User) i.getSerializableExtra("currentUser");
        selectedUser = (User) i.getSerializableExtra("selectedUser");
        shift = (Schedule) i.getSerializableExtra("shift");

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(HomeFragment.newInstance(currentUser));

        schedAdapter = new SchedAdapter(this, schedules);
        new DisplaySchedule(currentUser.username, schedules, schedAdapter).execute();
        listView = findViewById(R.id.list);
        listView.setAdapter(schedAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Switch Shift here //

                swapShift = (Schedule) adapterView.getItemAtPosition(i);
                new SwitchShift(GiveShiftActivity.this, currentUser, selectedUser, swapShift, shift, response,schedAdapter).execute();
                schedules.clear();
                schedAdapter.notifyDataSetChanged();

            }
        });
    }


    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                Log.d("ANDREW","HERE");
                fragment = HomeFragment.newInstance(currentUser);
                break;
            case R.id.navigation_viewSchedule:
                fragment = ScheduleFragment.newInstance(currentUser);
                break;
            case R.id.navigation_switchShifts:
                fragment = SwitchFragment.newInstance(currentUser);
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.commit();
        return true;
    }
}
