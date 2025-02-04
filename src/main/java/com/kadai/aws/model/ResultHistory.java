package com.kadai.aws.model;

import java.util.Date;

public class ResultHistory {

	private int userId;
	private Date executeDatetime;
	private int opponent;
	private String result;

	public int getUserId() {
		return userId;
	}

	public Date getExecuteDatetime() {
		return executeDatetime;
	}

	public int getOpponent() {
		return opponent;
	}

	public String getResult() {
		return result;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setExecuteDatetime(Date executeDatetime) {
		this.executeDatetime = executeDatetime;
	}

	public void setOpponent(int opponent) {
		this.opponent = opponent;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
