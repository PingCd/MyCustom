package com.yangyu.mycustomtab02.data;

public class Users {//用户类
	
	private int useerid;//用户id
	private String username;//用户名
	private String userpasw;//密码

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpasw() {
		return userpasw;
	}

	public void setUserpasw(String userpasw) {
		this.userpasw = userpasw;
	}

	public int getUseerid() {
		return useerid;
	}

	public void setUseerid(int useerid) {
		this.useerid = useerid;
	}
}
