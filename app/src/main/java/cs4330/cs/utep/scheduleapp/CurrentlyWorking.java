package cs4330.cs.utep.scheduleapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

public class CurrentlyWorking extends AsyncTask<String, Void, ArrayList<String>> {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Context context;

    public CurrentlyWorking(Context context, ArrayList<String> list, ArrayAdapter<String> adapter) {
        this.context = context;
        this.list = list;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(String... info){
        try{
            String day = info[0];
            String time = info[1];

            URL url = new URL("http://helper.at.utep.edu/scheduling_app/currentlyWorking.php");
            JSONObject postDataParams = new JSONObject();

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
                JSONArray jsonArray = new JSONArray(sb.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(jsonObject.getString("first") + " " + jsonObject.getString("last"));
                }
                Log.d("LIST:" , list.toString());
                return list;
            }else{
                return null;
            }
        } catch(Exception e){
            Log.d("Exception HomeScreen: " , e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        this.adapter.notifyDataSetChanged();
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
