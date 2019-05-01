package cs4330.cs.utep.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    Intent i = new Intent(MainActivity.this, HomeScreenActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                    return true;
                case R.id.navigation_viewSchedule:
                    //mTextMessage.setText(R.string.title_viewSchedule);
                    //new DisplaySchedule().execute();
                    Intent displaySched = new Intent(MainActivity.this,ScheduleActivity.class);
                    displaySched.putExtra("name",name); // User //
                    startActivity(displaySched);
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
        setContentView(R.layout.activity_main);

        user = (User)getIntent().getSerializableExtra("user");

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
