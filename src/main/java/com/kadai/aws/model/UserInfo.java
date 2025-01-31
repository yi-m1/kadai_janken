package com.kadai.aws.model;

import java.io.Serializable;
/**
 *ユーザ情報を格納するエンティティクラス
 */
public class UserInfo implements Serializable {
	private String mailAddress;
    private String userName;
    
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
