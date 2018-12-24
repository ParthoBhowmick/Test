package com.example.sampleproject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	EditText et_name,et_user_pass,et_user_name,et_user_contact,et_user_email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		intializeAll();
	}
	
	private void intializeAll() {
		// TODO Auto-generated method stub
		et_name = (EditText) findViewById(R.id.signupNameEt);
		et_user_name = (EditText) findViewById(R.id.signupUserNameEt);
		et_user_pass = (EditText) findViewById(R.id.signupUserPassEt);
		et_user_contact = (EditText) findViewById(R.id.signupUserContactEt);
		et_user_email = (EditText) findViewById(R.id.signupUserEmailEt);
	}
	
	public void usersign(View view) {
		String name,user_name,user_pass,user_contact,user_email;
		name = et_name.getText().toString();
		user_name = et_user_name.getText().toString();
		user_pass=et_user_pass.getText().toString();
		user_contact=et_user_contact.getText().toString();
		user_email = et_user_email.getText().toString();
		if(name!=null && user_name!=null && user_pass!=null && user_email!=null && user_contact!=null) {
			BackgroundTaskReg backgroundTask = new BackgroundTaskReg(this);
			backgroundTask.execute(name,user_name,user_pass,user_contact,user_email);
		}
		else {
			Toast.makeText(this, "Please fill up the required field", Toast.LENGTH_LONG).show();
		}
	}
	
	public class BackgroundTaskReg extends AsyncTask<String, Void, String> {
		AlertDialog alertDialog;
		Context ctx;
		public BackgroundTaskReg(Context context) {
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
				String name = arg0[0];
				String user_name = arg0[1];
				String user_pass = arg0[2];
				String user_contact = arg0[3];
				String user_email = arg0[4];
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
			finish();
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		
	}
	
}
