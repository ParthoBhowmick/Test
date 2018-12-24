package com.example.sampleproject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewPlace extends Activity {
	
	TextView tv,tv1,tv3,tv4,tv5,tv6,tv7,tv2,tv8,tv9;
	ImageView img1,img2,img3,img4;
	ListView lv1,lv2;
	Button btn;
	String place_name,json;
	RatingBar rt;
	int count=0;
	
	ArrayAdapter<String> adapter2;
	ArrayList<String> listItems=new ArrayList<String>();
	ArrayList<Bitmap> pic_bitmap = new ArrayList<Bitmap>();
	ArrayList<String> pic_cap=new ArrayList<String>();
	
	String monimg1,monimg2,monimg3,resource,description,hotelname1,hotelname2,hotelname3,hoteladd1,hoteladd2,hoteladd3;
	
	JSONObject jj;
	JSONArray ja;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_place);
		intializeAll();
		Intent intent = getIntent();
		place_name = intent.getStringExtra("Place");
		json = intent.getStringExtra("JSON");
		tv.setText(place_name);
		
		try {
			
			jj = new JSONObject(json);
			ja = jj.getJSONArray("server_response");
			
			int count=0;
			
		while (count < ja.length()) {
			JSONObject jo = ja.getJSONObject(count);
			float ratingPoint = (float) jo.getDouble("rating");
			 rt.setRating(ratingPoint);
			 monimg1 = jo.getString("vistime1");
			 monimg2 = jo.getString("vistime2");
			 monimg3 = jo.getString("vistime3");
			 
			for (int i = 1; i < 5; i++) {
				String x = "picname"+i;
				String y = "piccap" + i;
				if (jo.getString(x)!="") {
					pic_cap.add(jo.getString(y));
					new DownloadImageLoad(jo.getString(x).toString()).execute();
					tv4.setText(jo.getString(y));
				}
			}
			 
			 new DownloadImage8(monimg1,1).execute();
			 
			 new DownloadImage8(monimg2,2).execute();
			 
			 if(monimg3!=null) {
				 new DownloadImage8(monimg3,3).execute();
			 }
			 
			 description = jo.getString("pdescription");
			 tv7.setText(description);
			 
			 adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems);
			 
			 listItems.add(jo.getString("police"));
			 listItems.add(jo.getString("hospital"));
			 
			 lv2.setAdapter(adapter2);
			 
			 hotelname1 = jo.getString("hotelname1");
			 hotelname2 = jo.getString("hotelname2");
			 hotelname3 = jo.getString("hotelname3");
			 
			 hoteladd1 = jo.getString("hoteladd1");
			 hoteladd2 = jo.getString("hoteladd2");
			 hoteladd3 = jo.getString("hoteladd3");
			 
			 HashMap<String, String> nameAddresses = new HashMap<String, String>();
			 
		     nameAddresses.put(hotelname1, hoteladd1);
		     
		     if (hotelname2!=null) {
		    	 nameAddresses.put(hotelname2, hoteladd2);
		     }
		     
		     if (hotelname3!=null) {
		    	 nameAddresses.put(hotelname3, hoteladd3);
		     }
		     
		     List<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
		        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.hotelitem,
		                new String[]{"First Line", "Second Line"},
		                new int[]{R.id.hoteltv1, R.id.hoteltv2});


		        Iterator it = nameAddresses.entrySet().iterator();
		        while (it.hasNext())
		        {
		            HashMap<String, String> resultsMap = new HashMap<String, String>();
		            Map.Entry pair = (Map.Entry)it.next();
		            resultsMap.put("First Line", pair.getKey().toString());
		            resultsMap.put("Second Line", pair.getValue().toString());
		            listItems.add(resultsMap);
		        }

		        lv1.setAdapter(adapter);
			 
			//listItems.add(jo.getString("Place_Name"));
			count++;
		}
		//lv1.setAdapter(adapter);		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void next(View v) {
		img4.setImageBitmap(pic_bitmap.get(count));
		tv4.setText(pic_cap.get(count));
		count++;
		if (count==pic_cap.size()) {
			count=0;
		}
	}
	
	public void intializeAll() {
		tv = (TextView) findViewById(R.id.viewPlTv1); 
		tv1 = (TextView) findViewById(R.id.VPtextView1);
		tv2= (TextView) findViewById(R.id.VPtextView2);
		tv3=(TextView) findViewById(R.id.VPtextView3);
		tv4 = (TextView) findViewById(R.id.VPtextView4);
		tv5= (TextView) findViewById(R.id.VPtextView5);
		tv6=(TextView) findViewById(R.id.VPtextView6);
		tv7 = (TextView) findViewById(R.id.VPtextView7);
		tv8=(TextView) findViewById(R.id.VPtextView8);
		tv9 = (TextView) findViewById(R.id.VPtextView9);
		
		img1 = (ImageView) findViewById(R.id.VPimageView1);
		img2 = (ImageView) findViewById(R.id.VPimageView2);
		img3 = (ImageView) findViewById(R.id.VPimageView3);
		img4 = (ImageView) findViewById(R.id.VPimageView4);
		
		rt = (RatingBar) findViewById(R.id.VPratingBar1);
		
		lv1 = (ListView) findViewById(R.id.VPlistView1);
		lv2 = (ListView) findViewById(R.id.VPlistView2);
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
		img4.setImageBitmap(bitmap);
		pic_bitmap.add(bitmap);
	}		
}

private class DownloadImage8 extends AsyncTask<Void, Void, Bitmap> {
	
	String name;
	int id;
	
	public DownloadImage8(String name,int id) {
		this.name=name;
		this.id = id;
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
		if(id==1) {
			img1.setImageBitmap(bitmap);
		}
		else if (id==2) {
			img2.setImageBitmap(bitmap);
		}
		else {
			img3.setImageBitmap(bitmap);
		}
	}		
}

}
