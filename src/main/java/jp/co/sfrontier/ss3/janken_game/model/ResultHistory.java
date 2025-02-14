package jp.co.sfrontier.ss3.janken_game.model;

public class ResultHistory {

	private String userName;
	private String executeDatetime;
	private String opponent;
	private String result;

	public String getUserName() {
		return userName;
	}

	public String getExecuteDatetime() {
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

	public void setExecuteDatetime(String executeDatetime) {
		this.executeDatetime = executeDatetime;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
