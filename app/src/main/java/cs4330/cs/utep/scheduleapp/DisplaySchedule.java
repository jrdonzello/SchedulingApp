package cs4330.cs.utep.scheduleapp;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

//Gets the schedule for given name and updates the given adapter //
public class DisplaySchedule extends AsyncTask<String,Void,List<Schedule>> {
    protected List<Schedule> schedules;
    protected SchedAdapter schedAdapter;
    protected String name;

    public DisplaySchedule(String name, List<Schedule> schedules,SchedAdapter schedAdapter){
        this.name = name;
        this.schedules = schedules;
        this.schedAdapter = schedAdapter;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Schedule> doInBackground(String... params) {
        Log.d("ANDREW","Downloading...");
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL url = new URL("http://helper.at.utep.edu/scheduling_app/EmployeeSchedule.php");
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("name", name);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = "";

                while( (line = in.readLine()) != null){
                    sb.append(line);
                }
                in.close();
                TypeFactory typeFactory = mapper.getTypeFactory();
                this.schedules = mapper.readValue(sb.toString(),typeFactory.constructCollectionType(List.class,Schedule.class));
                return schedules;
            }else{
               Log.d("ANDREW","False: " + responseCode);
            }

        }catch(Exception e){
            return null;
        }
        return null;
    }

    public String getPostDataString(JSONObject params)throws Exception{
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key = itr.next();
            Object value = params.get(key);
            if(first){
                first = false;
            }else{
                result.append("&");
            }
            result.append(URLEncoder.encode(key,"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(),"UTF-8"));
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(List<Schedule> result) {
       if(result == null){
           Log.d("ANDREW", "Null ref");
       }
        schedules = result;
        schedAdapter.schedules.addAll(schedules);
        schedAdapter.notifyDataSetChanged();
    }
}

