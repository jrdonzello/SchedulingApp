package cs4330.cs.utep.scheduleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
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

public class HomeScreen extends AsyncTask<String, Void, Schedule> {

    Context context;
    TextView nextShift;

    public HomeScreen(Context context, TextView nextShift){
        this.context = context;
        this.nextShift = nextShift;
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected Schedule doInBackground(String... info) {
        try{
            String username = info[0];
            String day = info[1];
            String time = info[2];

            URL url = new URL("http://helper.at.utep.edu/scheduling_app/NextShift.php");
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("username", username);
            postDataParams.put("day", day);
            postDataParams.put("time", time);

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
                String line = "";
                StringBuilder sb = new StringBuilder();
                while( (line = in.readLine()) != null){
                    sb.append(line);
                }
                in.close();
                JSONArray jsonArray = new JSONArray((sb.toString()));
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Schedule nextShift = new Schedule(jsonObject.getString("start"), jsonObject.getString("end"), jsonObject.getString("day"), jsonObject.getString("track"), jsonObject.getString("username"));

                return  nextShift;
            }else{
                return null;
            }
        } catch(Exception e){
            Log.d("Exception HomeScreen: " , e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Schedule result) {
        if (result == null) {
            Log.d("HomeScreen", "Empty result");
        }
        else {
//            Log.d("ANDREW","Result " + result.start);
//            this.nextShift.setText(result.day +" " +result.start+"-"+result.end+" "+result.track);
        }
    }


    private String getPostDataString(JSONObject params)throws Exception{
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


}
