package cs4330.cs.utep.scheduleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Toast;

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

class SwitchShift extends AsyncTask<Void,Void,Void> {
    User currentUser;
    User selectedUser;
    Schedule swapShift;
    Schedule shift;
    String response;
    AlertDialog dialog;
    Context context;
    SchedAdapter schedAdapter;

    public SwitchShift(Context context, User currentUser, User selectedUser, Schedule swapShift, Schedule shift, String response,SchedAdapter schedAdapter){
        this.currentUser = currentUser;
        this.selectedUser = selectedUser;
        this.swapShift = swapShift;
        this.shift = shift;
        this.response = response;
        this.context = context;
        this.schedAdapter = schedAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("ANDREW","Switching shifts...");
        try {
            URL url = new URL("http://helper.at.utep.edu/scheduling_app/SwitchShifts-Android.php");
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("current", currentUser.username);
            postDataParams.put("currentTrack",shift.track);
            postDataParams.put("selectedTrack",swapShift.track);
            postDataParams.put("selected",selectedUser.username);
            postDataParams.put("swapStart",swapShift.start);
            postDataParams.put("swapEnd",swapShift.end);
            postDataParams.put("swapDay",swapShift.day);
            postDataParams.put("start",shift.start);
            postDataParams.put("end", shift.end);
            postDataParams.put("day",shift.day);

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
                response = sb.toString();
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
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("ANDREW","Response: " + response);
        if(response.contains("1")){
            Intent i = new Intent(context,MainActivity.class);
            context.startActivity(i);

            Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show();
            schedAdapter.schedules.clear();
            schedAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(context,"Error, Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
