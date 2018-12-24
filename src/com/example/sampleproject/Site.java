package com.example.sampleproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Site extends Activity {
	
	private static final int RESULT_LOAD_IMAGE = 1;
	TextView tv;
	EditText et;
	Button btn;
	ImageView img;
	Spinner spn;
	String pri,value;
	
	ProgressDialog pd;
	
	Intent newintent;
	
	private static String[] privacy  = {"Who can see my post?","public","friend","only me"};
	ArrayAdapter<String> adapter1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_site);
		
		 Intent intent = getIntent();
	     value = intent.getStringExtra("username");
		
		intializeAll();
		
		adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, privacy);

        spn.setAdapter(adapter1);

        spn.setOnItemSelectedListener(listener1);  
        
	}
	
	OnItemSelectedListener listener1= new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            pri=privacy[arg2];
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            pri=privacy[2];
        }
    };
	
	public void intializeAll() {
		spn = (Spinner) findViewById(R.id.spnprivacy);
		tv=(TextView) findViewById(R.id.tvTitle);
		et = (EditText) findViewById(R.id.fet);
		img = (ImageView)findViewById(R.id.fvimg);
		btn = (Button) findViewById(R.id.fbtn);
	}
	
	public void next(View v) {
		newintent = new Intent(this,DisplayListView.class);
		new BackgroundTaskJSON().execute();
	}
	
	public void sharePost(View v) {
		Bitmap image = ((BitmapDrawable)img.getDrawable()).getBitmap();
		String postText = et.getText().toString();
		String name = postText.substring(0, 3);
		String namejpg = name+".jpg";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateandTime = sdf.format(new Date());
		BackgroundTask backgroundTask = new BackgroundTask(this);
		backgroundTask.execute(value,postText,namejpg,currentDateandTime,pri);
		new uploadImage(image, name).execute();
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
						Thread.sleep(250);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				pd.dismiss();
			}
		}));
		t.start();
		pd.show();
	}
	
	public void search(View v) {
		Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
		//new DownloadImage(et.getText().toString()).execute();
	}
	
	public class uploadImage extends AsyncTask<Void, Void,Void> {
		
		Bitmap image;
		String name;
		
		public uploadImage(Bitmap img, String nme) {
			// TODO Auto-generated constructor stub
			this.image = img;
			this.name = nme;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
			String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
			
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			dataToSend.add(new BasicNameValuePair("image", encodedImage));
			dataToSend.add(new BasicNameValuePair("name", name));
			
			HttpParams httpRequestParams = getHttpRequestParams();
			
			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost("http://accsectiondemo.site11.com/SavePicture.php");
			
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				client.execute(post);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "Post Uploaded!", Toast.LENGTH_LONG).show();
		}
	}
	
	private HttpParams getHttpRequestParams() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 1000*30);
		HttpConnectionParams.setSoTimeout(httpParams, 1000*30);
		return httpParams;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null) {
			Uri selectedImage = data.getData();
			img.setImageURI(selectedImage);
		}
	}
	
public class BackgroundTask extends AsyncTask<String, Void, Void> {
	
	Context ctx;
	public BackgroundTask(Context context) {
		this.ctx = context;
	}
	
	@Override
	protected Void doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String insert_post_url = "http://accsectiondemo.site11.com/insertPost.php";
		
			String id = arg0[0];
			String post = arg0[1];
			String picname = arg0[2];
			String time = arg0[3];
			String privacy = arg0[4];
			
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
				String data = URLEncoder.encode("id", "UTF-8") + "="+ URLEncoder.encode(id,"UTF-8") + "&" + 
						URLEncoder.encode("post", "UTF-8") + "="+ URLEncoder.encode(post,"UTF-8") + "&" + 
						URLEncoder.encode("picname", "UTF-8") + "="+ URLEncoder.encode(picname,"UTF-8") + "&" +
						URLEncoder.encode("privacy", "UTF-8") + "="+ URLEncoder.encode(privacy,"UTF-8") + "&" +
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
}

	
class BackgroundTaskJSON extends AsyncTask<Void, Void, String> {
	
	String json_url,json_string="";
	
	@Override
	protected void onPreExecute() {
		json_url = "http://accsectiondemo.site11.com/json.php";
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		try {
			URL url = new URL(json_url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
		newintent.putExtra("json_data", result);
		newintent.putExtra("username" , value);
		startActivity(newintent);
	}
	
	
}


}
