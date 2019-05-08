package cs4330.cs.utep.scheduleapp;

import android.os.AsyncTask;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

class EmployeeList extends AsyncTask<Void,Void, List<User>> {
    private ListAdapter listAdapter;
    private List<User> users;

    public EmployeeList(List<User> users ,ListAdapter listAdapter){
        this.listAdapter = listAdapter;
        this.users = users;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        Log.d("ANDREW","Downloading Employees...");
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL url = new URL("http://helper.at.utep.edu/scheduling_app/Employees.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = "";

                while( (line = in.readLine()) != null){
                    sb.append(line);
                }

                in.close();
                JSONArray json = new JSONArray(sb.toString());
                for(int i = 0; i < json.length();i++){
                    JSONObject obj = json.getJSONObject(i);
                    this.users.add(new User(obj.getString("username"),obj.getString("first"),obj.getString("last"),obj.getBoolean("admin")));
                }
                return users;
            }else{
                Log.d("ANDREW","False: " + responseCode);
            }

        }catch(Exception e){
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);
        listAdapter.notifyDataSetChanged();
        Log.d("ANDREW","Users Downloaded.");
    }
}

