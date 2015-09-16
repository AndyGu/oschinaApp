package net.oschina.app.bean;

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
	private UpdateData date;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UpdateData getDate() {
		return date;
	}

	public void setDate(UpdateData data) {
		this.date = data;
	}
}
