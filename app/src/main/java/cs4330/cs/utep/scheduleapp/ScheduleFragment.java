package cs4330.cs.utep.scheduleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    SchedAdapter schedAdapter;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    ListView listView;
    User user;

    public static ScheduleFragment newInstance(User user){
        ScheduleFragment fragment = new ScheduleFragment();
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

        user = (User) getArguments().getSerializable("user");
        View rootView = inflater.inflate(R.layout.activity_main,null);
        schedAdapter = new SchedAdapter(getActivity(),schedules);
        new DisplaySchedule(user.username,schedules,schedAdapter).execute();
        listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(schedAdapter);
        return rootView;
    }
}
