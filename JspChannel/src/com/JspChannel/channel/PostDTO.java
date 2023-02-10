package com.JspChannel.channel;

import java.sql.Timestamp;

public class PostDTO {
    // 멤버 변수 선언
    private int ch_num;
    private int post_num;
    private int vcount;
    private String user_id;
    private String post_title;
    private String post_contents;
    private String post_file;
    private String ch_image;
	private Timestamp first_day;
	
	
	
    public Timestamp getFirst_day() {
		return first_day;
	}
	public void setFirst_day(Timestamp first_day) {
		this.first_day = first_day;
	}

    public int getCs() {
		return Cs;
	}
	public int getPost_num() {
		return post_num;
	}
	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}
	public int getVcount() {
		return vcount;
	}
	public void setVcount(int vcount) {
		this.vcount = vcount;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_contents() {
		return post_contents;
	}
	public void setPost_contents(String post_contents) {
		this.post_contents = post_contents;
	}
	public String getPost_file() {
		return post_file;
	}
	public void setPost_file(String post_file) {
		this.post_file = post_file;
	}
	public void setCs(int cs) {
		Cs = cs;
	}
	private int Cs;

	public int getCh_num() {
		return ch_num;
	}
	public void setCh_num(int ch_num) {
		this.ch_num = ch_num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCh_image() {
		return ch_image;
	}
	public void setCh_image(String ch_image) {
		this.ch_image = ch_image;
	}


    
 
}