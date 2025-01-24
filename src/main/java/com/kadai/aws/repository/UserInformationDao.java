package com.kadai.aws.repository;

import java.sql.SQLException;

import com.kadai.aws.model.UserInfo;

/**
 *
 */
public interface UserInformationDao {
	
	//メールアドレスとユーザIDからユーザを探すインターフェース
	UserInfo findByMailAdressAndUserId(String mailAdress, String userId)throws SQLException;
	
	//新規ユーザを追加するインターフェース
	void addUser(String mailAdress, String userId) throws SQLException;
}