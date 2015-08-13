package net.oschina.app.bean;

public class WelcomeBean {
	private int status;
	private String error;
	private WelcomeData data;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public WelcomeData getData() {
		return data;
	}
	public void setDate(WelcomeData data) {
		this.data = data;
	}
	
}
