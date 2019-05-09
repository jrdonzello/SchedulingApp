package cs4330.cs.utep.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SwitchFragment extends Fragment {
    ListAdapter userAdapter;
    ArrayList<User> users = new ArrayList<>();
    ListView listView;
    User currentUser;

    public static SwitchFragment newInstance(User user) {
        SwitchFragment fragment = new SwitchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentUser",user);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_switch,null);
        currentUser = (User) getArguments().getSerializable("currentUser");

        userAdapter = new ListAdapter(getActivity(),users);
        new EmployeeList(users,userAdapter).execute();
        listView = rootView.findViewById(R.id.switchListView);
        listView.setAdapter(userAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = (User) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(),ChooseShiftActivity.class);
                intent.putExtra("currentUser",currentUser);
                intent.putExtra("selectedUser",selectedUser);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
