package com.example.sampleproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class BackgroundTask extends AsyncTask<String, Void, String> {
	AlertDialog alertDialog;
	Context ctx;
	public BackgroundTask(Context context) {
		this.ctx = context;
	}
	@Override
	protected void onPreExecute() {
		alertDialog = new AlertDialog.Builder(ctx).create();
		alertDialog.setTitle("Welcome!");
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		String reg_url = "http://accsectiondemo.site11.com/register.php";
			String name = arg0[1];
			String user_name = arg0[2];
			String user_pass = arg0[3];
			String user_contact = arg0[4];
			String user_email = arg0[5];
			try {
				URL url = new URL(reg_url);
				//connect to URL
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				//sending data using POST method
				httpURLConnection.setRequestMethod("POST");
				//for sending some data to URL 
				httpURLConnection.setDoOutput(true);
				//to write information in buffered writer an output stream object need to be initialized
				OutputStream OS = httpURLConnection.getOutputStream();
				//to write information to URL
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
				String data = URLEncoder.encode("user", "UTF-8") + "="+ URLEncoder.encode(name,"UTF-8") + "&" + 
						URLEncoder.encode("username", "UTF-8") + "="+ URLEncoder.encode(user_name,"UTF-8") + "&" + 
						URLEncoder.encode("userpasss", "UTF-8") + "="+ URLEncoder.encode(user_pass,"UTF-8") + "&" +
						URLEncoder.encode("usercontact", "UTF-8") + "="+ URLEncoder.encode(user_contact,"UTF-8") + "&" + 
						URLEncoder.encode("useremail", "UTF-8") + "="+ URLEncoder.encode(user_email,"UTF-8");
				 bufferedWriter.write(data);
				 bufferedWriter.flush();
				 bufferedWriter.close();
				 OS.close();
				 //get response from server
				 InputStream is = httpURLConnection.getInputStream();
				 is.close();
				 return "Registration Success!";
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}	
	@Override
	protected void onPostExecute(String result) {
		if(result.equals("Registration Success!")) {
			alertDialog.setMessage(result);
			alertDialog.show();
		}
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
}