package com.kadai.aws.model;

import java.io.Serializable;
/**
 *ログイン後のユーザ情報をここに格納する
 */
public class UserInfo implements Serializable {
	private String mailAddress;
    private String userId;
    
    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
