package jp.co.sfrontier.ss3.janken_game.model;

import java.util.Date;

public class ResultHistory {

	private String userName;
	private Date executeDatetime;
	private String opponent;
	private String result;

	public String getUserName() {
		return userName;
	}

	public Date getExecuteDatetime() {
		return executeDatetime;
	}

	public String getOpponent() {
		return opponent;
	}

	public String getResult() {
		return result;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setExecuteDatetime(Date executeDatetime) {
		this.executeDatetime = executeDatetime;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
