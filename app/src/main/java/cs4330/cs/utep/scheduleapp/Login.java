package cs4330.cs.utep.scheduleapp;

import android.content.Context;
import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.util.Log;

import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class Login extends AsyncTask<String, Void, String> {

    Context context;
    String username;
    String password;
    @Override
    protected void onPreExecute(){
    }
    @Override
    protected String doInBackground(String... info) {
            try{
                String username = info[0];
                String password = info[1];

                String link="http://helper.at.utep.edu/scheduling_app/Verify2.php";
                //String link = "/Users/justendonzello/Documents/php/Verify2.php";
                String data  = URLEncoder.encode("user", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
                System.setProperty("http.agent","");
                URL url = new URL(link);URLConnection conn = url.openConnection();
                conn.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
                conn.connect();
                conn.getOutputStream().write(data.getBytes("UTF-8"));
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
    }

    protected void onPostExecute(String... result){
        if (result.equals("1")) {
            Log.d("HELLO:", result[0]);
        }
    }


}
