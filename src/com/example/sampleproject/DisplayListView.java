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
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayListView extends Activity {
	
	String json_string;

	static String value,req_name,req_pic;
	
	JSONObject jj,jj1;
	JSONArray ja,ja1;
	
	ViewAdapter va;
	ListView lv;
	TextView tv;
	ImageView img;
	
	Button btn;
	
	
	
	//copy part start
		Bitmap todisplay;
		
		ProgressDialog pd;
		
		String pp;
		
		ArrayList<ClassDorkar> object = new ArrayList<ClassDorkar>(); 
		ArrayList<String> pp_name = new ArrayList<String>();
		ArrayList<Bitmap> pro_pic_bitmap = new ArrayList<Bitmap>();
		
		ArrayList<String> user_name = new ArrayList<String>();
		ArrayList<String> timestamp = new ArrayList<String>(); 
		ArrayList<String> privacy2 = new ArrayList<String>();
		
		ArrayList<String> post_pic_name =new ArrayList<String>();
		ArrayList<Bitmap> post_pic_bitmap = new ArrayList<Bitmap>();
		
		ArrayList<String> post_text = new ArrayList<String>();
		ArrayList<String> post_ID = new ArrayList<String>();
		
		ArrayList<Integer> like_counter= new ArrayList<Integer>();
		ArrayList<Integer> share_counter= new ArrayList<Integer>();
		ArrayList<Integer> comment_counter= new ArrayList<Integer>();
		ArrayList<Integer> privacy_pic= new ArrayList<Integer>();
		ArrayList<Integer> no_img= new ArrayList<Integer>();
		
		int noimg=1;
		int count=0;
		int length;
		
		JSONObject jsonObject;
		JSONArray jsonArray;
		//copy part end
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_list_view);
		
		Intent intent = getIntent();
		value = intent.getStringExtra("username");
		
		BackgroundTaskRetName backgroundTask = new BackgroundTaskRetName(getApplicationContext());
		backgroundTask.execute(value);
		
		tv = (TextView) findViewById(R.id.tvtesting);
		img=(ImageView) findViewById(R.id.img2);
		btn = (Button) findViewById(R.id.disbtn);
		
		json_string=getIntent().getExtras().getString("json_data");
		
		pd = new ProgressDialog(this);
		pd.setTitle("TourBook!");
		pd.setMessage("Loading....");
		pd.setProgress(0);
		pd.setMax(100);
		Thread t = new Thread((new Runnable() {
			
			@Override
			public void run() {
				int progress=0;
				// TODO Auto-generated method stub
				while (progress<=100) {
					try {
						pd.setProgress(progress);
						progress++;
						Thread.sleep(380);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				pd.dismiss();
			}
		}));
		t.start();
		pd.show();
		
		lv = (ListView) findViewById(R.id.lv1);
		va = new ViewAdapter(this, R.layout.testlayout2);
		lv.setAdapter(va);
		
		
		try {
		
			jj = new JSONObject(json_string);
			ja = jj.getJSONArray("server_response");
			
			length = ja.length();
			  
			
		while (count < ja.length()) {
			todisplay=null;
			JSONObject jo = ja.getJSONObject(count);
			
			post_ID.add(jo.getString("postID"));
			
			user_name.add(jo.getString("name"));
			
			pp=jo.getString("pro_pic");
			pp_name.add(pp);
			
			new DownloadImage(pp.toString(),1).execute();
			
			String post_pic_name_st= jo.getString("pic");
			post_pic_name.add(post_pic_name_st);
			
			if(post_pic_name_st.equals("aaaa")) {
				new DownloadImage("long.jpg",2).execute();
				no_img.add(1);
			}
			else  {
				new DownloadImage(post_pic_name_st.toString(),2).execute();
				no_img.add(0);
			}
			
			
			
			post_text.add(jo.getString("status"));
			String privacy;
			privacy=jo.getString("privacy");
			if(privacy.equals("friend")) {
				privacy_pic.add(R.drawable.frnd);
			}
			else if (privacy.equals("public")) {
				privacy_pic.add(R.drawable.pub);
			}
			else if(privacy.equals("private")) {
				privacy_pic.add(R.drawable.pvt);
			}
			timestamp.add(jo.getString("time"));
			like_counter.add(jo.getInt("Like"));
			comment_counter.add(jo.getInt("Comment"));
			share_counter.add(jo.getInt("Share"));
			count=count+1;
			
			}
				
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void letsgo(View v) {
		for(int i=0;i<length;i++) {
			
			ClassDorkar cd = new ClassDorkar(post_ID.get(i),pp_name.get(i),post_pic_name.get(i),pro_pic_bitmap.get(i),post_pic_bitmap.get(i),user_name.get(i),timestamp.get(i),post_text.get(i),like_counter.get(i),comment_counter.get(i),share_counter.get(i),privacy_pic.get(i),no_img.get(i));
			va.add(cd);
			
		}
	}
	
private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
		
		String name;
		int count;
		
		public DownloadImage(String name, int con) {
			this.name=name;
			this.count=con;
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
			if(bitmap != null ) {
				todisplay=bitmap;
				if (count==1) {
					pro_pic_bitmap.add(bitmap);
				}
				
				else if (count==2) {
					post_pic_bitmap.add(bitmap);
				}
				
				img.setImageBitmap(todisplay);
			}
			
		}
		
	}


class BackgroundTaskRetName extends AsyncTask<String, Void, String> {
	
	String json_string="",id;
	Context ctx;
	
	public BackgroundTaskRetName(Context context) {
		this.ctx = context;
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String post_url = "http://accsectiondemo.site11.com/NameNpic.php";
		id = arg0[0];
		try{
			URL url = new URL(post_url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			OutputStream outputStream = httpURLConnection.getOutputStream();
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
			String data = URLEncoder.encode("pid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
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
	
	protected void onPostExecute(String result) {
		try {
			
			jj1 = new JSONObject(result);
			ja1 = jj1.getJSONArray("server_response");
			JSONObject jo1 = ja1.getJSONObject(0);
			
			req_name=jo1.getString("name");
			req_pic=jo1.getString("picname");
			
			tv.setText(req_name + req_pic);
			
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
	
}