package cs4330.cs.utep.scheduleapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends ArrayAdapter<User> {
    private final Context context;
    protected List<User> users;

    public ListAdapter(Context context,List<User> users){
        super(context,-1,users);
        this.users = users;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.employee_list_view,parent,false);
            TextView name  = rowView.findViewById(R.id.name);
            name.setText(users.get(position).first + " " + users.get(position).last);
            return rowView;
    }


}
