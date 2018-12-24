package com.example.sampleproject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAdapter extends ArrayAdapter {
	
	private Context context;
	
	List list = new ArrayList();
	
	ImageView like_picImg2;
	
	public static final int NO_IMAGE=1;
	public static final int HAS_IMAGE =0;
	
	public ViewAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	

	public void add(ClassDorkar ob) {
		// TODO Auto-generated method stub
		super.add(ob);
		list.add(ob);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}
	
	@Override
	public int getItemViewType(int position) {
		ClassDorkar FR = (ClassDorkar) getItem(position);
		if(FR.getNoimg()==1) {
			return NO_IMAGE;
		}
		else {
			return HAS_IMAGE;
		}
	}
	
	/*
	static class holder {
		ImageView pro_pic,privacy,likeimg,commentimg,shareimg;
		TextView name,time,like,comment,share,status;
	}
	
	static class holder2 {
		ImageView pro_pic,privacy,likeimg,commentimg,shareimg,post_pic;
		TextView name,time,like,comment,share,status;
	}
	*/
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row;
		final View intentView;
		
		row = convertView;
		intentView = row;
	//	holder hold;
	//	holder2 hold2;
		
		if(row==null) {
			LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		//	if(getItemViewType(position)==HAS_IMAGE) {
				row = layoutInflater.inflate(R.layout.testlayout2,parent,false);
		//}
			/*
			else {
				row = layoutInflater.inflate(R.layout.testlayout, parent,false);
			}
			
			*/
			
		}
		
		//else {
			//if (getItemViewType(position)==HAS_IMAGE) {
				ImageView pro_picImg2 = (ImageView) row.findViewById(R.id.image2);
				TextView user_nameTv2 = (TextView) row.findViewById(R.id.name2);
				TextView timestamTv2 = (TextView) row.findViewById(R.id.time2);
				TextView statusTv2=(TextView) row.findViewById(R.id.status2);
				ImageView post_picImg2 = (ImageView) row.findViewById(R.id.postimg2);
				ImageView privacy_picImg2 = (ImageView) row.findViewById(R.id.privacyimg2);
				like_picImg2 = (ImageView) row.findViewById(R.id.likeimg2);
				ImageView comment_picImg2 = (ImageView) row.findViewById(R.id.commentimg2);
				ImageView share_picImg2 = (ImageView) row.findViewById(R.id.shareimg2);
				final TextView likeTv2=(TextView) row.findViewById(R.id.like2);
				TextView commentTv2=(TextView) row.findViewById(R.id.comment2);
				TextView shareTv2=(TextView) row.findViewById(R.id.share2);
				
				final ClassDorkar cd = (ClassDorkar) getItem(position);
				
				pro_picImg2.setImageBitmap(cd.getPro_pic());
				user_nameTv2.setText(cd.getUsername());
				timestamTv2.setText(cd.getTime());
				statusTv2.setText(cd.getStatus());
				post_picImg2.setImageBitmap(cd.getPost_pic());
				
				privacy_picImg2.setImageResource(cd.getPrivacy());
				
				String s = cd.getLike() + "";
				likeTv2.setText(s);
				 s = cd.getComment() + "";
				commentTv2.setText(s);
				s = cd.getShare() + "";
				shareTv2.setText(s);
		//	}
		
		/*	
		else  {
			ImageView pro_picImg = (ImageView) row.findViewById(R.id.image1);
			TextView user_nameTv = (TextView) row.findViewById(R.id.name1);
			TextView timestamTv = (TextView) row.findViewById(R.id.time1);
			TextView statusTv=(TextView) row.findViewById(R.id.status1);
			ImageView post_picImg2 = (ImageView) row.findViewById(R.id.postimg1);
			ImageView privacy_picImg = (ImageView) row.findViewById(R.id.privacyimg1);
			ImageView like_picImg = (ImageView) row.findViewById(R.id.likeimg1);
			ImageView comment_picImg = (ImageView) row.findViewById(R.id.commentimg1);
			ImageView share_picImg = (ImageView) row.findViewById(R.id.shareimg1);
			TextView likeTv=(TextView) row.findViewById(R.id.like1);
			TextView commentTv=(TextView) row.findViewById(R.id.comment1);
			TextView shareTv=(TextView) row.findViewById(R.id.share1);
			
			ClassDorkar cd = (ClassDorkar) getItem(position);
			
			pro_picImg.setImageBitmap(cd.getPro_pic());
			user_nameTv.setText(cd.getUsername());
			timestamTv.setText(cd.getTime());
			privacy_picImg.setImageResource(cd.getPrivacy());
			statusTv.setText(cd.getStatus());
			String s = cd.getLike() + "";
			likeTv.setText(s);
			 s = cd.getComment() + "";
			commentTv.setText(s);
			s = cd.getShare() + "";
			shareTv.setText(s);
		}
		*/
		//}
				
				
				
			like_picImg2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int like = cd.getLike();
					String user_name = cd.getUsername();
					String post_time = cd.getTime();
					like++;
					cd.setLike(like);
					String s = like + "";
					like_picImg2.setImageResource(R.drawable.yellow);
					likeTv2.setText(s);
					
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					cd.getPro_pic().compress(Bitmap.CompressFormat.JPEG, 100, stream);
					byte[] byteArray = stream.toByteArray();
					
					ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
					cd.getPost_pic().compress(Bitmap.CompressFormat.JPEG, 100, stream2);
					byte[] byteArray2 = stream2.toByteArray();
					
					Intent intent = new Intent(intentView.getContext(), ViewPostDetails.class);
					
					intent.putExtra("postID", cd.getPostID());
					intent.putExtra("image_pro",byteArray);
					intent.putExtra("image_post", byteArray2);
					
					intent.putExtra("like", like);
					intent.putExtra("comment", cd.getComment());
					intent.putExtra("share", cd.getShare());
					intent.putExtra("username", user_name);
					intent.putExtra("time", post_time);
					intent.putExtra("privacy_pic",cd.getPrivacy());
					//intent.putExtra("post_pic", cd.getPost_pic());
					intent.putExtra("status", cd.getStatus());
					intent.putExtra("Liked", 1);
					intent.putExtra("user", DisplayListView.value);
					intent.putExtra("commentator", DisplayListView.req_name);
					intent.putExtra("commentator_pic", DisplayListView.req_pic);
					context.startActivity(intent);
				}
			});
			
			comment_picImg2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int like = cd.getLike();
					String user_name = cd.getUsername();
					String post_time = cd.getTime();
					
					
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					cd.getPro_pic().compress(Bitmap.CompressFormat.JPEG, 100, stream);
					byte[] byteArray = stream.toByteArray();
					
					ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
					cd.getPost_pic().compress(Bitmap.CompressFormat.JPEG, 100, stream2);
					byte[] byteArray2 = stream2.toByteArray();
					
					Intent intent = new Intent(intentView.getContext(), ViewPostDetails.class);
					
					intent.putExtra("postID", cd.getPostID());
					intent.putExtra("image_pro",byteArray);
					intent.putExtra("image_post", byteArray2);
					intent.putExtra("Liked", 0);
					intent.putExtra("like", like);
					intent.putExtra("comment", cd.getComment());
					intent.putExtra("share", cd.getShare());
					intent.putExtra("username", user_name);
					intent.putExtra("time", post_time);
					intent.putExtra("privacy_pic",cd.getPrivacy());
					intent.putExtra("status", cd.getStatus());
					intent.putExtra("user", DisplayListView.value);
					intent.putExtra("commentator", DisplayListView.req_name);
					intent.putExtra("commentator_pic", DisplayListView.req_pic);
					
					context.startActivity(intent);
					
				}
			});
				
			return row;
	}
}
