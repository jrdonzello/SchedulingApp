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
import java.util.List;

class SwitchShift extends AsyncTask<Void,Void,Void> {
    User user;
    String start;
    String end;
    String day;

    public SwitchShift(User u){
        this.user = u;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("ANDREW","Downloading...");
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL url = new URL("http://helper.at.utep.edu/scheduling_app/SwitchShifts.php");
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("username", user.username);
            postDataParams.put("employeeFirst",user.first);
            postDataParams.put("employeeLast",user.last);
            postDataParams.put("start",start);
            postDataParams.put("end",end);
            postDataParams.put("day",day);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
//            writer.write(getPostDataString(postDataParams));
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
//                this.schedules = mapper.readValue(sb.toString(),typeFactory.constructCollectionType(List.class,Schedule.class));
//                return schedules;
            }else{
                Log.d("ANDREW","False: " + responseCode);
            }

        }catch(Exception e){
            return null;
        }
        return null;
    }
}
