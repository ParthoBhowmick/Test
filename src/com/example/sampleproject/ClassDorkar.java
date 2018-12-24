package com.example.sampleproject;

import android.graphics.Bitmap;

public class ClassDorkar {
	Bitmap pro_pic,post_pic;
	String username,time,status,pro_pic_name,post_pic_name,postID;
	int like,comment,share,privacy,noimg;
	public ClassDorkar(String PostID,String pro_pic_name,String post_pic_name,Bitmap pro_pic, Bitmap post_pic,String username, String time, String status, int like, int comment, int share,
			int privacy,int noimg) {
		super();
		this.postID = PostID;
		this.pro_pic_name = pro_pic_name;
		this.post_pic_name = post_pic_name;
		this.pro_pic = pro_pic;
		this.post_pic=post_pic;
		this.username = username;
		this.time = time;
		this.status = status;
		this.like = like;
		this.comment = comment;
		this.share = share;
		this.privacy = privacy;
		this.noimg=noimg;
	}
	public String getPostID() {
		return postID;
	}
	public void setPostID(String postID) {
		this.postID = postID;
	}
	public String getPro_pic_name() {
		return pro_pic_name;
	}
	public void setPro_pic_name(String pro_pic_name) {
		this.pro_pic_name = pro_pic_name;
	}
	public String getPost_pic_name() {
		return post_pic_name;
	}
	public void setPost_pic_name(String post_pic_name) {
		this.post_pic_name = post_pic_name;
	}
	public int getNoimg() {
		return noimg;
	}
	public void setNoimg(int noimg) {
		this.noimg = noimg;
	}
	public Bitmap getPost_pic() {
		return post_pic;
	}
	public void setPost_pic(Bitmap post_pic) {
		this.post_pic = post_pic;
	}
	public Bitmap getPro_pic() {
		return pro_pic;
	}
	public void setPro_pic(Bitmap pro_pic) {
		this.pro_pic = pro_pic;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public int getShare() {
		return share;
	}
	public void setShare(int share) {
		this.share = share;
	}
	public int getPrivacy() {
		return privacy;
	}
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}	
}
