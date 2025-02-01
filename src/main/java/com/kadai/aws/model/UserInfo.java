package com.kadai.aws.model;

import java.io.Serializable;
/**
 *ユーザ情報を格納するエンティティクラス
 */
public class UserInfo implements Serializable {
	private int userId;
	private String mailAddress;
    private String userName;
    
    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
