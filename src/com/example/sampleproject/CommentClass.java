package com.example.sampleproject;

import android.graphics.Bitmap;

public class CommentClass {
	String postID,userName,timePost,pro_pic_name,commentPost;
	Bitmap pro_pic_bitmap;
	
	public CommentClass(String postID, String userName, String timePost, String pro_pic_name, String commentPost,
			Bitmap pro_pic_bitmap) {
		super();
		this.postID = postID;
		this.userName = userName;
		this.timePost = timePost;
		this.pro_pic_name = pro_pic_name;
		this.commentPost = commentPost;
		this.pro_pic_bitmap = pro_pic_bitmap;
	}
	
	public String getPostID() {
		return postID;
	}
	public void setPostID(String postID) {
		this.postID = postID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTimePost() {
		return timePost;
	}
	public void setTimePost(String timePost) {
		this.timePost = timePost;
	}
	public String getPro_pic_name() {
		return pro_pic_name;
	}
	public void setPro_pic_name(String pro_pic_name) {
		this.pro_pic_name = pro_pic_name;
	}
	public String getCommentPost() {
		return commentPost;
	}
	public void setCommentPost(String commentPost) {
		this.commentPost = commentPost;
	}
	public Bitmap getPro_pic_bitmap() {
		return pro_pic_bitmap;
	}
	public void setPro_pic_bitmap(Bitmap pro_pic_bitmap) {
		this.pro_pic_bitmap = pro_pic_bitmap;
	}
	
	
}