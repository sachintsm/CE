package com.example.ce;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class DBHelper extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    DBHelper (Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        //String login_url = "http://idexserver.tk/sachinCE/user/login.php";
        String register_url = "http://idexserver.tk/sachinCE/user/add.php";
       // String postAdd_url =  "http://idexserver.tk/sachinCE/adds/postadd.php";
       // String profile_url = "http://idexserver.tk/sachinCE/user/profile.php";

//        if(type.equals("login")){
//            try{
//                String username = params[1];
//                String password = params[2];
//                URL url = new URL(login_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("GET");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//                String result = "";
//                String line = "";
//                while ((line = bufferedReader.readLine()) != null){
//                    result += line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return result;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        else if(type.equals("postadd")){
//            try{
//                String username = params[1];
//                String item = params[2];
//                String photo = params[3];
//                String description = params[4];
//                String district = params[5];
//                String city = params[6];
//                String number = params[7];
//                String email = params[8];
//                String date = params[9];
//                URL url = new URL(postAdd_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
//                                    +URLEncoder.encode("item","UTF-8")+"="+URLEncoder.encode(item,"UTF-8")+"&"
//                                    +URLEncoder.encode("photo","UTF-8")+"="+URLEncoder.encode(photo,"UTF-8")+"&"
//                                    +URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(description,"UTF-8")+"&"
//                                    +URLEncoder.encode("district","UTF-8")+"="+URLEncoder.encode(district,"UTF-8")+"&"
//                                    +URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(city,"UTF-8")+"&"
//                                    +URLEncoder.encode("number","UTF-8")+"="+URLEncoder.encode(number,"UTF-8")+"&"
//                                    +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
//                                    +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//                String result = "";
//                String line = "";
//                while ((line = bufferedReader.readLine()) != null){
//                    result += line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return result;
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        if(type.equals("register")){
            try{
                String name = params[1];
                String username = params[2];
                String email = params[3];
                String password = params[4];
                String number = params[5];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("number","UTF-8")+"="+URLEncoder.encode(number,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Registered !");
        alertDialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
