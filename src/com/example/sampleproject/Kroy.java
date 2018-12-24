package com.example.sampleproject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sampleproject.PersonalInterest.BackgroundTask;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Type;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Kroy extends Activity {

	String value,info;
	
	EditText et;
	TextView tv1,tv2;
	ImageView img;
	Button btn1,btn2;
	
	int initial=0,temp=0,count=0,length,local=0,buy=0;
	
	JSONObject jj;
	JSONArray ja;
	
	ArrayList<Bitmap> pic_bitmap = new ArrayList<Bitmap>();
	ArrayList<String> pic_cap=new ArrayList<String>();
	ArrayList<Integer> price_int=new ArrayList<Integer>();
	ArrayList<String> pic_cap2=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kroy);
		Intent intent = getIntent();
        value = intent.getStringExtra("username");
        
        info = intent.getStringExtra("JSON");
        intializeAll();     
try {
			
			jj = new JSONObject(info);
			ja = jj.getJSONArray("server_response");
			length=ja.length();
			
		while (count < ja.length()) {
			JSONObject jo = ja.getJSONObject(count);
			
			pic_cap.add(jo.getString("item"));
			tv1.setText(pic_cap.get(count));
			price_int.add(jo.getInt("price"));
			pic_cap2.add(jo.getInt("price")+" tk per unit");
			tv2.setText(pic_cap2.get(count));
			
			new DownloadImageLoad(jo.getString("img").toString()).execute();
			
		count++;
		}
		
} catch (JSONException e) {
	e.printStackTrace();
}
	}
	
	public void intializeAll() {
		et = (EditText) findViewById(R.id.editText11);
		tv1 = (TextView) findViewById(R.id.textSwitcher11);
		tv2 = (TextView) findViewById(R.id.textSwitcher22);
		img = (ImageView) findViewById(R.id.imageSwitcher11);
		btn1 = (Button) findViewById(R.id.button11);
		btn2 = (Button) findViewById(R.id.button22);
	}
	
	public void next(View v) {
		img.setImageBitmap(pic_bitmap.get(initial));
		tv1.setText(pic_cap.get(initial));
		tv2.setText(pic_cap2.get(initial));
		initial++;
		if (initial==pic_cap.size()) {
			initial=0;
		}
	}
	
	private class DownloadImageLoad extends AsyncTask<Void, Void, Bitmap> {
		
		String name;
		
		public DownloadImageLoad(String name) {
			this.name=name;
		}
		
		@Override
		protected Bitmap doInBackground(Void... params) {
			String url = "http://accsectiondemo.site11.com/" + "pictures/" + name;
			
			try {
				URLConnection connection  = new URL(url).openConnection();
				connection.setConnectTimeout(1000*30);
				connection.setReadTimeout(1000*30);
				return BitmapFactory.decodeStream((InputStream) connection.getContent(),null,null);
			} catch (Exception e) {
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if(local==length-1) {
			img.setImageBitmap(bitmap);
			}
			local++;
			pic_bitmap.add(bitmap);
		}		
	}
	
	
    public void buy(View v) {
    	AlertDialog.Builder builder1 = new AlertDialog.Builder(Kroy.this);
    	builder1.setMessage("Sure Want to Buy?");
    	builder1.setCancelable(true);

    	builder1.setPositiveButton(
    	    "Yes",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	        	buy++;
    	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	        	String user = value;
    	        	String currentDateandTime = sdf.format(new Date());
    	        	String quantity = et.getText().toString();
    	        	String product = pic_cap.get(initial-1);
    	        	int quant = Integer.parseInt(quantity);
    	        	int amount = price_int.get(initial-1);
    	        	int price = amount*quant;
    	        	
    	        	BackgroundTaskHAHA backgroundTask = new BackgroundTaskHAHA(getApplicationContext());
					backgroundTask.execute(user,product,quantity,price+"",currentDateandTime);
    	        }
    	    });

    	builder1.setNegativeButton(
    	    "No",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	            dialog.cancel();
    	        }
    	    });

    	AlertDialog alert11 = builder1.create();
    	alert11.show();
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	
    	if(temp>0) {
        	Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        	startActivity(intent);}
    	
    	else if (temp==0 && buy==0) {
    		AlertDialog.Builder builder12 = new AlertDialog.Builder(Kroy.this);
        	builder12.setMessage("Don't Want to Buy?");
        	builder12.setCancelable(true);
        	
        	builder12.setPositiveButton(
            	    "Yes",
            	    new DialogInterface.OnClickListener() {
            	        public void onClick(DialogInterface dialog, int id) {
            	        	dialog.cancel();
            	        }
            	    });

            	builder12.setNegativeButton(
            	    "No",
            	    new DialogInterface.OnClickListener() {
            	        public void onClick(DialogInterface dialog, int id) {
            	        	Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            	        	startActivity(intent);
            	        }
            	    });
            	AlertDialog alert12 = builder12.create();
            	alert12.show();
    	}
    	
    	else {
    	AlertDialog.Builder builder1 = new AlertDialog.Builder(Kroy.this);
    	builder1.setMessage("Want to Buy More?");
    	builder1.setCancelable(true);
    	
    	
    	
    	builder1.setPositiveButton(
    	    "Yes",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	        	Intent intent = new Intent(getApplicationContext(), Order.class);
    	        	intent.putExtra("username", value);
    	        	startActivity(intent);
    	        }
    	    });

    	builder1.setNegativeButton(
    	    "No",
    	    new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int id) {
    	        	
    	        	AlertDialog alertDialog = new AlertDialog.Builder(Kroy.this).create();
    	        	alertDialog.setTitle("Thank You!");
    	        	alertDialog.setMessage("You will be Notified Soon!");
    	        	alertDialog.show();
    	        	temp++;
    	        }
    	    });

    	AlertDialog alert11 = builder1.create();
    	alert11.show();
    	}
    }
    
    public class BackgroundTaskHAHA extends AsyncTask<String, Void, Void> {
    	
    	Context ctx;
    	String prod,quan;
    	//AlertDialog alertDialog;
    	
    	public BackgroundTaskHAHA(Context context) {
    		this.ctx = context;
    	}
    	/*
    	@Override
    	protected void onPreExecute() {
    		alertDialog = new AlertDialog.Builder(ctx).create();
    		alertDialog.setTitle("Product Purchase");
    	}
    	*/
    	
    	@Override
    	protected Void doInBackground(String... arg0) {
    		// TODO Auto-generated method stub
    		String insert_post_url = "http://accsectiondemo.site11.com/insertRecord.php";
    		
    			String username = arg0[0];
    			String productName = arg0[1];
    			String productQuantity = arg0[2];
    			String productPrice = arg0[3];
    			String time = arg0[4];
    			prod=productName;
    			quan=productQuantity;
    			
    			try {
    				URL url = new URL(insert_post_url);
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
    				String data = URLEncoder.encode("id", "UTF-8") + "="+ URLEncoder.encode(username,"UTF-8")  + "&" + 
    						URLEncoder.encode("product", "UTF-8") + "="+ URLEncoder.encode(productName,"UTF-8") + "&" +
    						URLEncoder.encode("price", "UTF-8") + "="+ URLEncoder.encode(productPrice,"UTF-8") + "&" +
    						URLEncoder.encode("quantity", "UTF-8") + "="+ URLEncoder.encode(productQuantity,"UTF-8")+ "&" +
    	    						URLEncoder.encode("time", "UTF-8") + "="+ URLEncoder.encode(time,"UTF-8");
    				 bufferedWriter.write(data);
    				 bufferedWriter.flush();
    				 bufferedWriter.close();
    				 OS.close();
    				 //get response from server
    				 InputStream is = httpURLConnection.getInputStream();
    				 is.close();
    				 
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
    	protected void onPostExecute(Void result) {
			AlertDialog.Builder builder1 = new AlertDialog.Builder(Kroy.this);
	    	builder1.setMessage(quan + "unit of " + prod + "is Purchased!");
	    	AlertDialog alert11 = builder1.create();
	    	alert11.show();
    	}
    	
    }
	
}
