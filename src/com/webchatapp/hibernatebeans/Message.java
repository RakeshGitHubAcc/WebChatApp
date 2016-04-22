package com.webchatapp.hibernatebeans;

import com.webchatapp.utils.ChatDate;

public class Message {
	private Integer id;
	private String senderName;
	private String receiverName;
	private String message;
	private ChatDate time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ChatDate getTime() {
		return time;
	}
	public void setTime(ChatDate time) {
		this.time = time;
	}
	
}
