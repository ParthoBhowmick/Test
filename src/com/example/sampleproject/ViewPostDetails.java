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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPostDetails extends Activity {
	
	JSONObject jj;
	JSONArray ja;
	ArrayList<String> comment = new ArrayList<String>(); 
	ArrayList<String> pid = new ArrayList<String>();
	ArrayList<Bitmap> pro_pic_bitmap = new ArrayList<Bitmap>();
	ArrayList<String> pro_pic_name = new ArrayList<String>();
	
	ArrayList<String> user_name_Arr = new ArrayList<String>();
	ArrayList<String> timestamp_Arr = new ArrayList<String>(); 

	TextView user_nameTv2, timestamTv2,statusTv2,likeTv2,commentTv2,shareTv2,tvcomlist,text;
	ImageView pro_picImg2, post_picImg2,privacy_picImg2,like_picImg2,comment_picImg2,share_picImg2,sudoImg;
	ListView lvcom;
	Button btnpost,sudo;
	EditText etcom;
	String json_string,value,comentname,comentpic;
	Bitmap todisplay;
	ProgressDialog pd;
	int length,comment_count;
	ViewCommentAdapter vw;
	int lastTry=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_post_details);
		
		intializeAll();
		
		vw = new ViewCommentAdapter(this, R.layout.commentitem);
		lvcom.setAdapter(vw);
	
		int like_count = getIntent().getExtras().getInt("like");
		value = getIntent().getExtras().getString("user");
		comentname = getIntent().getExtras().getString("commentator");
		comentpic = getIntent().getExtras().getString("commentator_pic");
		
		comment_count = getIntent().getExtras().getInt("comment");
		int share_count = getIntent().getExtras().getInt("share");
		
		byte[] byteArray22 = getIntent().getByteArrayExtra("image_pro");
		Bitmap bmp = BitmapFactory.decodeByteArray(byteArray22, 0, byteArray22.length);
		
		byte[] byteArray222 = getIntent().getByteArrayExtra("image_post");
		Bitmap bmp2 = BitmapFactory.decodeByteArray(byteArray222, 0, byteArray222.length);
		
		int liked = getIntent().getExtras().getInt("Liked");
		
		final String postID = getIntent().getExtras().getString("postID");
		String user_name = getIntent().getExtras().getString("username");
		String post_time = getIntent().getExtras().getString("time");
		int privacy_pic = getIntent().getExtras().getInt("privacy_pic");
		
		
		
		String status = getIntent().getExtras().getString("status");
		
		user_nameTv2.setText(user_name);
		timestamTv2.setText(post_time);
		statusTv2.setText(status);
		privacy_picImg2.setImageResource(privacy_pic);
		likeTv2.setText(like_count+"");
		commentTv2.setText(comment_count+"");
		shareTv2.setText(share_count+"");
		
		pro_picImg2.setImageBitmap(bmp);
		post_picImg2.setImageBitmap(bmp2);
		
		if(liked==1) {
			BackgroundTaskLike backgroundTaskLike = new BackgroundTaskLike(this);
			backgroundTaskLike.execute(like_count+"",user_name,post_time);
			like_picImg2.setImageResource(R.drawable.yellow);
		}
		
		new BackgroundTaskJson().execute(postID);
		
		pd = new ProgressDialog(this);
		pd.setTitle("TourBook!");
		pd.setMessage("Loading....");
		pd.setProgress(0);
		pd.setMax(100);
		Thread t = new Thread((new Runnable() {
			
			@Override
			public void run() {
				int progress=0;
				
				while (progress<=100) {
					try {
						pd.setProgress(progress);
						progress++;
						Thread.sleep(150);
					} catch (Exception e) {
						
					}
				}
				pd.dismiss();
			}
		}));
		t.start();
		pd.show();
		

		btnpost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				comment_count = comment_count + 1;
				commentTv2.setText(comment_count+"");
				String cmpost = etcom.getText().toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentDateandTime = sdf.format(new Date());
				user_name_Arr.add("Partho Pritom");
				timestamp_Arr.add(currentDateandTime);
				comment.add(cmpost);
				length=length+1;
				BackgroundTaskPost backgroundTaskPost = new BackgroundTaskPost(getApplicationContext());
				backgroundTaskPost.execute(postID,comentname,comentpic,currentDateandTime,cmpost);
				Toast.makeText(getApplicationContext(), "Comment is Posted", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
	public void intializeAll() {
		pro_picImg2 = (ImageView)findViewById(R.id.image22);
		user_nameTv2 = (TextView)findViewById(R.id.name22);
		timestamTv2 = (TextView)findViewById(R.id.time22);
		statusTv2=(TextView)findViewById(R.id.status22);
		post_picImg2 = (ImageView)findViewById(R.id.postimg22);
		privacy_picImg2 = (ImageView)findViewById(R.id.privacyimg22);
		like_picImg2 = (ImageView) findViewById(R.id.likeimg22);
		comment_picImg2 = (ImageView) findViewById(R.id.commentimg22);
		share_picImg2 = (ImageView)findViewById(R.id.shareimg22);
		likeTv2=(TextView) findViewById(R.id.like22);
		commentTv2=(TextView) findViewById(R.id.comment22);
		shareTv2=(TextView) findViewById(R.id.share22);
		etcom = (EditText) findViewById(R.id.etcomment);
		tvcomlist = (TextView) findViewById(R.id.tvcommentlist);
		lvcom = (ListView) findViewById(R.id.lvcomment);
		btnpost = (Button) findViewById(R.id.btnpost);
		text=(TextView) findViewById(R.id.tvlol);
		sudo = (Button)findViewById(R.id.sudobtn);
		sudoImg = (ImageView) findViewById(R.id.sudoimg);
	}
	
	public class BackgroundTaskLike extends AsyncTask<String, Void, Void> {
		
		Context ctx;
		
		public BackgroundTaskLike(Context context) {
			this.ctx = context;
		}

		@Override
		protected Void doInBackground(String... arg0) {
			
			String insert_post_url = "http://accsectiondemo.site11.com/updateLikeCount.php";
			
				String Like = arg0[0];
				String username = arg0[1];
				String time = arg0[2];
				
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
					String data = URLEncoder.encode("like", "UTF-8") + "="+ URLEncoder.encode(Like,"UTF-8") + "&" + 
							URLEncoder.encode("username", "UTF-8") + "="+ URLEncoder.encode(username,"UTF-8") + "&" +
							URLEncoder.encode("time", "UTF-8") + "="+ URLEncoder.encode(time,"UTF-8");
					 bufferedWriter.write(data);
					 bufferedWriter.flush();
					 bufferedWriter.close();
					 OS.close();
					 //get response from server
					 InputStream is = httpURLConnection.getInputStream();
					 is.close();
					 
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				return null;
			}
	}
	
	class BackgroundTaskJson extends AsyncTask<String, Void, String> {
		
		String json_url;
		
		@Override
		protected void onPreExecute() {
			json_url = "http://accsectiondemo.site11.com/json2.php";
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			try {
				URL url = new URL(json_url);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setDoInput(true);
				httpURLConnection.setDoOutput(true);
				OutputStream outputStream = httpURLConnection.getOutputStream();
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
				String data = URLEncoder.encode("pid", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
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
			int count=0;
			tvcomlist.setVisibility(View.GONE);
				try {
					jj = new JSONObject(result);
					ja = jj.getJSONArray("server_response");
					
					length = ja.length();
					
				while (count < ja.length()) {
					todisplay=null;
					JSONObject jo = ja.getJSONObject(count);
					
					pid.add(jo.getString("pid"));
					
					user_name_Arr.add(jo.getString("name"));
					
					String pp=jo.getString("propic");
					pro_pic_name.add(pp);
					
					comment.add(jo.getString("comment"));
					
					timestamp_Arr.add(jo.getString("time"));
					
					new DownloadImage4(pp.toString()).execute();
					
					
					count=count+1;
					}
					

				} catch (JSONException e) {
					e.printStackTrace();
				}
			
		}
		
		
	}
	
	
private class DownloadImage4 extends AsyncTask<Void, Void, Bitmap> {
		
		String name;
		
		public DownloadImage4(String name) {
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
			if(bitmap != null ) {
					todisplay = bitmap;
					pro_pic_bitmap.add(todisplay);
					CommentClass cd = new CommentClass(pid.get(lastTry), user_name_Arr.get(lastTry), timestamp_Arr.get(lastTry), pro_pic_name.get(lastTry), comment.get(lastTry), pro_pic_bitmap.get(lastTry));
					vw.add(cd);
					vw.notifyDataSetChanged();
					lastTry++;
				}
			
		}		
	}

public class BackgroundTaskPost extends AsyncTask<String, Void, Void> {
	
	Context ctx;
	String id,username,userPic,time,Comment;
	Bitmap bitmap;
	public BackgroundTaskPost(Context context) {
		this.ctx = context;
	}
	
	@Override
	protected Void doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String insert_post_url = "http://accsectiondemo.site11.com/insertComment.php";
		String load_img_url = "http://accsectiondemo.site11.com/" + "pictures/" + arg0[2];
		
			id = arg0[0];
			username = arg0[1];
			userPic = arg0[2];
			time = arg0[3];
			Comment = arg0[4];
			
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
						URLEncoder.encode("username", "UTF-8") + "="+ URLEncoder.encode(username,"UTF-8") + "&" + 
						URLEncoder.encode("userPic", "UTF-8") + "="+ URLEncoder.encode(userPic,"UTF-8") + "&" +
						URLEncoder.encode("Comment", "UTF-8") + "="+ URLEncoder.encode(Comment,"UTF-8") + "&" +
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
			try {
				URLConnection connection  = new URL(load_img_url).openConnection();
				connection.setConnectTimeout(1000*30);
				connection.setReadTimeout(1000*30);
				bitmap =  BitmapFactory.decodeStream((InputStream) connection.getContent(),null,null);
			} catch (Exception e) {
				
			}
			return null;
		}
	
	@Override
	protected void onPostExecute(Void result) {
		CommentClass cd = new CommentClass(id, username, time, userPic, Comment, bitmap);
		vw.add(cd);
		vw.notifyDataSetChanged();
	}
}

}