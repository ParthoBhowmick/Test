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
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewDiv extends Activity {
	
	TextView tv1;
	Spinner spn1;
	EditText et1;
	Button btn;
	ListView lv1;
	
	private static String[] PS  = {"Most Popular","Order by Cost","Sea Beach","Hill","Heritage Site","Waterfall"};
	String ps;
	String div,send;
	
	ArrayAdapter<String> adapter1,adapter;
	
	JSONObject jj;
	JSONArray ja;
	
	ArrayList<String> listItems=new ArrayList<String>();
	Intent intent2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_div);
		Intent intent = getIntent();
		div = intent.getStringExtra("Div");

		intializeAll();
		tv1.setText(div + " Division");
		
		adapter1=new ArrayAdapter<String>(ViewDiv.this, android.R.layout.simple_spinner_item, PS);

	        spn1.setAdapter(adapter1);

	        spn1.setOnItemSelectedListener(listener1);
	        
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems);
	        
	        BackgroundTaskPost backgroundTaskPost = new BackgroundTaskPost(getApplicationContext());
			backgroundTaskPost.execute(div);
			
			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					 intent2 = new Intent(getApplicationContext(),ViewPlace.class);
					send = listItems.get(arg2);
					new BackgroundTask().execute(send);
				}
			});
	    }

	    OnItemSelectedListener listener1= new OnItemSelectedListener() {

	        @Override
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	            ps=PS[arg2];
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            ps=PS[0];
	        }
	    };
		
	public void yo(View v) {
		String srch = et1.getText().toString();
		//if (srch!="") {
			BackgroundTaskPost1 backgroundTaskPost1 = new BackgroundTaskPost1(getApplicationContext());
			backgroundTaskPost1.execute(ps,div);
//		}
//		else {
//			
//		}
	}
	
	public void intializeAll() {
		tv1 = (TextView) findViewById(R.id.viewDivTv1);
		spn1=(Spinner) findViewById(R.id.viewDivSpn1);
		lv1 = (ListView) findViewById(R.id.viewDivLv1);
		btn = (Button) findViewById(R.id.viewDivBtn1);
		et1=(EditText) findViewById(R.id.viewDivEt1);
	}
	
	
	public class BackgroundTaskPost extends AsyncTask<String, Void, String> {
		
		Context ctx;
		String DIV,json_string="";
		
		public BackgroundTaskPost(Context context) {
			this.ctx = context;
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String post_url = "http://accsectiondemo.site11.com/viewPlaceDefault.php";
			DIV = arg0[0];
			try{
				URL url = new URL(post_url);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setDoInput(true);
				httpURLConnection.setDoOutput(true);
				OutputStream outputStream = httpURLConnection.getOutputStream();
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
				String data = URLEncoder.encode("DIV", "UTF-8") + "=" + URLEncoder.encode(DIV, "UTF-8");
				bufferedWriter.write(data);
				bufferedWriter.close();
				outputStream.close();
				InputStream inputStream = httpURLConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				
				StringBuilder stringBuilder = new StringBuilder();
				
				while((json_string=bufferedReader.readLine())!=null) {
					stringBuilder.append(json_string+"\n");
				}
				bufferedReader.close();
				inputStream.close();
				httpURLConnection.disconnect();
				return stringBuilder.toString().trim();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {	
			
			try {
				jj = new JSONObject(result);
				ja = jj.getJSONArray("server_response");
				
				int count=0;
				
			while (count < ja.length()) {
				JSONObject jo = ja.getJSONObject(count);
				listItems.add(jo.getString("Place_Name"));
				count++;
			}
			lv1.setAdapter(adapter);		
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	
public class BackgroundTaskPost1 extends AsyncTask<String, Void, String> {
		
		Context ctx;
		String MOD,DIV,json_string="";
		
		
		public BackgroundTaskPost1(Context context) {
			this.ctx = context;
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			listItems.clear();
			
			// TODO Auto-generated method stub
			String post_url = "http://accsectiondemo.site11.com/viewPlaceDefaultPart2.php";
				MOD= arg0[0];
				DIV = arg0[1];
			try{
				URL url = new URL(post_url);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setDoInput(true);
				httpURLConnection.setDoOutput(true);
				OutputStream outputStream = httpURLConnection.getOutputStream();
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
				String data = URLEncoder.encode("DIV", "UTF-8") + "=" + URLEncoder.encode(DIV, "UTF-8") + "&" + URLEncoder.encode("MOD", "UTF-8") + "=" + URLEncoder.encode(MOD, "UTF-8");
				
				bufferedWriter.write(data);
				bufferedWriter.close();
				outputStream.close();
				InputStream inputStream = httpURLConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				
				StringBuilder stringBuilder = new StringBuilder();
				
				while((json_string=bufferedReader.readLine())!=null) {
					stringBuilder.append(json_string+"\n");
				}
				bufferedReader.close();
				inputStream.close();
				httpURLConnection.disconnect();
				return stringBuilder.toString().trim();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {	
			
			try {
				
				jj = new JSONObject(result);
				ja = jj.getJSONArray("server_response");
				
				int count=0;
				
			while (count < ja.length()) {
				JSONObject jo = ja.getJSONObject(count);
				listItems.add(jo.getString("Place_Name"));
				count++;
			}
			lv1.setAdapter(adapter);		
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

class BackgroundTask extends AsyncTask<String, Void, String> {
	
	String json_url,json_string="",place;
	
	@Override
	protected void onPreExecute() {
		json_url = "http://accsectiondemo.site11.com/placeInformation.php";
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		try {
			place = arg0[0];
			URL url = new URL(json_url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			OutputStream outputStream = httpURLConnection.getOutputStream();
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
			String data = URLEncoder.encode("PLACE", "UTF-8") + "=" + URLEncoder.encode(place, "UTF-8");
			bufferedWriter.write(data);
			bufferedWriter.close();
			outputStream.close();
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			StringBuilder stringBuilder = new StringBuilder();
			
			while((json_string=bufferedReader.readLine())!=null) {
				stringBuilder.append(json_string+"\n");
			}
			bufferedReader.close();
			inputStream.close();
			httpURLConnection.disconnect();
			return stringBuilder.toString().trim();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		intent2.putExtra("Place", place);
		intent2.putExtra("JSON", result);
        startActivity(intent2);
	}
}

}
