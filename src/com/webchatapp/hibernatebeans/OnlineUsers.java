package com.webchatapp.hibernatebeans;

public class OnlineUsers {
	private Integer id;
	public Integer userId;
	public int liveStatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getLiveStatus() {
		return liveStatus;
	}
	public void setLiveStatus(int liveStatus) {
		this.liveStatus = liveStatus;
	}
	
}
