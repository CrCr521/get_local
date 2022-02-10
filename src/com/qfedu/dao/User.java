package com.qfedu.dao;

public class User {
	private int stuNo;
	private String file_size;
	private String file_odd_name;
	private String creat_time;
	private String file_path;
	private String UUID;
	private String type;
	
	
	public int getStuNo() {
		return stuNo;
	}
	public void setStuNo(int stuNo) {
		this.stuNo = stuNo;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getFile_odd_name() {
		return file_odd_name;
	}
	public void setFile_odd_name(String file_odd_name) {
		this.file_odd_name = file_odd_name;
	}
	public String getCreat_time() {
		return creat_time;
	}
	public void setCreat_time(String creat_time) {
		this.creat_time = creat_time;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int stuNo,String file_size, String file_odd_name, String creat_time, String file_path, String uUID, String type) {
		super();
		this.stuNo = stuNo;
		this.file_size = file_size;
		this.file_odd_name = file_odd_name;
		this.creat_time = creat_time;
		this.file_path = file_path;
		UUID = uUID;
		this.type = type;
	}
	@Override
	public String toString() {
		return "User [stuNo=" + stuNo + ", file_size=" + file_size + ", file_odd_name=" + file_odd_name
				+ ", creat_time=" + creat_time + ", file_path=" + file_path + ", UUID=" + UUID + ", type=" + type + "]";
	}
	
	
	

}
