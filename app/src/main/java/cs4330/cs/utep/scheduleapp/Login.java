package cs4330.cs.utep.scheduleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.PrecomputedText;
import android.util.Log;

import org.json.*;


import java.io.*;
import java.net.*;
import java.util.Iterator;

public class Login extends AsyncTask<String, Void, User> {

    User user;
    Context context;

    public Login(User user, Context context){
        this.user = user;
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected User doInBackground(String... info) {
            try{
                String username = info[0];
                String password = info[1];

                URL url = new URL("http://helper.at.utep.edu/scheduling_app/Verify2.php");
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user", username);
                postDataParams.put("pass", password);

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
                    JSONObject jsonObject = new JSONObject(sb.toString());
                    user = new User(jsonObject.getString("username"), jsonObject.getString("first"), jsonObject.getString("last"), jsonObject.getBoolean("admin"));

                    return user;
                }else{
                    return null;
                }
            } catch(Exception e){
                Log.d("Exception: " , e.getMessage());
                return null;
            }
    }

    @Override
    protected void onPostExecute(User result) {
        if (result == null) {
            Log.d("Login", "empty");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Login attempt failed. Try again!");

            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            user = result;
            Intent i = new Intent(context , MainActivity.class);
            i.putExtra("user", user);
            context.startActivity(i);
        }
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


}
