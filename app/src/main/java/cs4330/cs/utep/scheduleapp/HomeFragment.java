package cs4330.cs.utep.scheduleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    private User user;
    private String currentDate;
    private String currentDay;
    TextView nameLabel;
    TextView nextShift;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<>();


    public static HomeFragment newInstance(User user) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_home,null);
        user = (User) getArguments().getSerializable("user");

        getCurrentDate();
        getCurrentDay();
        getNextShift();

        nameLabel = rootView.findViewById(R.id.nameLabel);
        nameLabel.setText(user.first + " " + user.last);
        nextShift = rootView.findViewById(R.id.nextShift);

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        new CurrentlyWorking(getActivity(), list, adapter).execute(currentDay, currentDate);
        listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

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
        new HomeScreen(getActivity(),nextShift).execute(user.username, currentDay, currentDate);
    }

}
