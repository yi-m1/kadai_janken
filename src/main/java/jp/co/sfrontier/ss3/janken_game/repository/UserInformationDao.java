package jp.co.sfrontier.ss3.janken_game.repository;

import java.sql.SQLException;

import jp.co.sfrontier.ss3.janken_game.model.UserInfo;

/**
 *ユーザー情報に関連するデータベース操作を規定するためのインターフェース
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