package cs4330.cs.utep.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SwitchActivity extends AppCompatActivity {
    ListAdapter userAdapter;
    ArrayList<User> users = new ArrayList<>();
    ListView listView;

    protected void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.activity_switch);

        userAdapter = new ListAdapter(this,users);
        new EmployeeList(users,userAdapter).execute();

        listView = findViewById(R.id.switchListView);
        listView.setAdapter(userAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = (User) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SwitchActivity.this,ChooseShiftActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
}
