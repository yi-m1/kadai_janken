package com.kadai.aws.repository;

import java.sql.SQLException;

import com.kadai.aws.model.UserInfo;

/**
 *
 */
public interface UserInformationDao {
	
	//メールアドレスでユーザを探すインターフェース
	UserInfo findByMailAddress(String mailAddress)throws SQLException;
	
	//新規ユーザを追加するインターフェース
	void addUser(String mailAddress, String userName) throws SQLException;

	//メールアドレスがDBに登録されているか確認するインターフェース
	boolean isMailAddressExists(String mailAddress) throws SQLException;
	
	//ユーザ名がDBに登録されているか確認するインターフェース
	boolean isUserNameExists(String userName) throws SQLException;
}