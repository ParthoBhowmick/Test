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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Order extends Activity {

    TextView tv0,tv1,tv2,tv3;
    Spinner spn1,spn2;
    CheckBox cb1,cb2;
    Button btn1;
    String ps,ns,value;
    
    Intent intent;

    private static String[] PS  = {"Food","Traditional Cloths","Handicrafts"};
    private static String[] NS = {"Products for Summer","Products for Rainy","Products for Winter"};

    ArrayAdapter<String> adapter1,adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        
        Intent intent = getIntent();
        value = intent.getStringExtra("username");
        
        intializeAll();
        
        

        adapter1=new ArrayAdapter<String>(Order.this, android.R.layout.simple_spinner_item, PS);

        spn1.setAdapter(adapter1);

        spn1.setOnItemSelectedListener(listener1);

        adapter2=new ArrayAdapter<String>(Order.this, android.R.layout.simple_spinner_item, NS);

        spn2.setAdapter(adapter2);

        spn2.setOnItemSelectedListener(listener2);

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

    OnItemSelectedListener listener2= new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            ns=NS[arg2];
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            ns=NS[0];
        }
    };

    public void intializeAll() {
        tv0= (TextView) findViewById(R.id.odr_tv0);
        tv1=(TextView) findViewById(R.id.odr_tv1);
        tv2= (TextView) findViewById(R.id.odr_tv2);
        tv3=(TextView) findViewById(R.id.odr_tv3);

        spn1 = (Spinner) findViewById(R.id.odr_spn1);
        spn2 = (Spinner) findViewById(R.id.odr_spn2);

        cb1 = (CheckBox) findViewById(R.id.odr_cb1);
        cb2=(CheckBox) findViewById(R.id.odr_cb2);
    }

    public void goOrder(View v) {
        if((cb1.isChecked()==true && cb2.isChecked()==false) || (cb1.isChecked()==false && cb2.isChecked()==true) ) {
            if(cb1.isChecked()) {
                if(ps=="Food") {
                    //Intent intent = new Intent(this, PersonalInterest.class);
                	intent = new Intent(this, Kroy.class);
                	intent.putExtra("username", value);
                     new BackgroundTaskItemJson().execute(ps);
                    Toast.makeText(this, ps , Toast.LENGTH_LONG).show();
                }
                if(ps=="Traditional Cloths") {
                    //Intent intent = new Intent(this, PersonalInterest.class);
                	intent = new Intent(this, Kroy.class);
                	intent.putExtra("username", value);
                     new BackgroundTaskItemJson().execute("Clothes");
                    Toast.makeText(this, ps , Toast.LENGTH_LONG).show();
                }
                if(ps=="Handicrafts") {
                    //Intent intent = new Intent(this, PersonalInterest.class);
                	intent = new Intent(this, Kroy.class);
                	intent.putExtra("username", value);
                     new BackgroundTaskItemJson().execute("Handicraft");
                    Toast.makeText(this, ps , Toast.LENGTH_LONG).show();
                }
            }
            else {
                if(ns=="Products for Summer") {
                    //Intent intent1 = new Intent(this, Summer.class);
                	Intent intent = new Intent(this, Kroy.class);
                    intent.putExtra("username", value);
                    new BackgroundTaskItemJson().execute("Summer");
                    Toast.makeText(this, ns , Toast.LENGTH_LONG).show();
                }
                if(ns=="Products for Rainy") {
                    //Intent intent2 = new Intent(this, Rainy.class);
                    //startActivity(intent2);
                    Toast.makeText(this, ns , Toast.LENGTH_LONG).show();
                }
                if(ns=="Products for Winter") {
                    //Intent intent3 = new Intent(this, Win.class);
                    //startActivity(intent3);
                    Toast.makeText(this, ns , Toast.LENGTH_LONG).show();
                }
            }
        }


        else {
            Toast.makeText(this, "Select only & atleast ONE !!" , Toast.LENGTH_LONG).show();
        }
        
    }
    
    class BackgroundTaskItemJson extends AsyncTask<String, Void, String> {
    	
    	String json_url,json_string="",item;
    	
    	@Override
    	protected void onPreExecute() {
    		json_url = "http://accsectiondemo.site11.com/ItemInformation.php";
    	}
    	
    	@Override
    	protected String doInBackground(String... arg0) {
    		try {
    			item = arg0[0];
    			URL url = new URL(json_url);
    			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    			httpURLConnection.setRequestMethod("POST");
    			httpURLConnection.setDoInput(true);
    			httpURLConnection.setDoOutput(true);
    			OutputStream outputStream = httpURLConnection.getOutputStream();
    			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
    			String data = URLEncoder.encode("ITEM", "UTF-8") + "=" + URLEncoder.encode(item, "UTF-8");
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
    		intent.putExtra("JSON", result);
            startActivity(intent);
    	}
    }
}
