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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText et_name,et_pass;
	String login_name, login_pass;
	
	Intent intent;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_name = (EditText) findViewById(R.id.loginUserNameEt);
		et_pass = (EditText) findViewById(R.id.loginUserPassEt);
	}

	public void userReg(View view) {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo!=null && networkInfo.isConnected()) {
			startActivity(new Intent(this, Register.class));
		}
		else {
			Toast.makeText(this, "Turn On Internet Connection" , Toast.LENGTH_LONG).show();
		}
	}
	
	public void userLogin(View v) {
		login_name = et_name.getText().toString();
		login_pass = et_pass.getText().toString();
		String method = "login";
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		
		if(networkInfo!=null && networkInfo.isConnected()) {
			if (login_name.isEmpty() && login_pass.isEmpty()) {
				Toast.makeText(this, "Please fill up the required field", Toast.LENGTH_LONG).show();
			}
			else {
				BackgroundTaskLogin backgroundTask = new BackgroundTaskLogin(this);
				backgroundTask.execute(login_name,login_pass);
				intent = new Intent(this,MainActivity2.class);
				
			}
		}
		else {
			Toast.makeText(this, "Turn On Internet Connection" , Toast.LENGTH_LONG).show();
		}
	}
	
	public class BackgroundTaskLogin extends AsyncTask<String, Void, String> {
		AlertDialog alertDialog;
		Context ctx;
		public BackgroundTaskLogin(Context context) {
			this.ctx = context;
		}
		
		@Override
		protected void onPreExecute() {
			alertDialog = new AlertDialog.Builder(ctx).create();
			alertDialog.setTitle("Login Information");
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String login_url = "http://accsectiondemo.site11.com/json2.php";
				String login_name = arg0[0];
				String login_pass = arg0[1];
				try {
					URL url = new URL(login_url);
					HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.setRequestMethod("POST");
					httpURLConnection.setDoInput(true);
					httpURLConnection.setDoOutput(true);
					OutputStream outputStream = httpURLConnection.getOutputStream();
					BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
					String data = URLEncoder.encode("loginName", "UTF-8") + "=" + URLEncoder.encode(login_name, "UTF-8") + '&' +
							URLEncoder.encode("loginPass", "UTF-8") + "=" + URLEncoder.encode(login_pass, "UTF-8");
					bufferedWriter.write(data);
					bufferedWriter.close();
					outputStream.close();
					
					InputStream inputStream = httpURLConnection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
					String response = "";
					String line = "";
					while ((line = bufferedReader.readLine())!=null) {
						response+= line; 
					}
					bufferedReader.close();
					inputStream.close();
					httpURLConnection.disconnect();
					return response;
					
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
			if(result.equals("Invalid Username and Password")) {
				alertDialog.setMessage(result);
				alertDialog.show();
			}
			else {
				intent.putExtra("username",login_name);
				startActivity(intent);
			}
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
	}
}