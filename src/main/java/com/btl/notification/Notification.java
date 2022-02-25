package com.btl.notification;

public class Notification {
	private String icon;
	private String content;
	private String type;
	
	public Notification(String icon, String content, String type) {
		this.icon = icon;
		this.content = content;
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
