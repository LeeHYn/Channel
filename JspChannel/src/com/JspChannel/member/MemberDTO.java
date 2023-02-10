package com.JspChannel.member;

import java.sql.Date;
import java.sql.Timestamp;

public class MemberDTO {
	
	private String id;
	private String pw;
	private String name;
	private String email;
	private int type; //관리자0 일반1

	
	//getters and setters;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
