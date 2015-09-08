package net.oschina.app.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 更新实体类
 * @author Gupeng
 * @version 创建时间：2015年9月8日
 * 
 */
@SuppressWarnings("serial")
public class UpdateBean implements Serializable {
	private int status;
	private String error;
	private UpdateData data;

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

	public UpdateData getData() {
		return data;
	}

	public void setData(UpdateData data) {
		this.data = data;
	}
}
