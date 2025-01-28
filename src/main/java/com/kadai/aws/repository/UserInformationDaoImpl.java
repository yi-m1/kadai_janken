/**
 * 
 */
package com.kadai.aws.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kadai.aws.model.UserInfo;

/**
 * 
 */
public class UserInformationDaoImpl implements UserInformationDao {

	private Connection connection;

	public UserInformationDaoImpl(Connection connection) {
		this.connection = connection;
	}

	private Connection getConnection() {
		if (connection == null) {
			throw new IllegalStateException("Connection is not initialized");
		}
		return connection;
	}

	/**
	 * DBからユーザ情報を取得するメソッド
	 */
	@Override
	public UserInfo findByMailAdressAndUserId(String mailAdress, String userId) throws SQLException {
		//コネクションの取得
		Connection conn = getConnection();

		//ステートメントの作成
		String sql = "SELECT mail_address, user_id FROM user_information_tbl WHERE mail_address = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, mailAdress);
		pstmt.setString(2, userId);

		//ステートメントの実行
		ResultSet rset = pstmt.executeQuery();

		//結果の取り出し
		UserInfo userInfo = null;
		if (rset.next()) {
			userInfo = new UserInfo();
			userInfo.setMailAddress(rset.getString("mail_address"));
			userInfo.setUserId(rset.getString("user_id"));
		}

		pstmt.close();

		//ユーザが存在するときはユーザ情報を返し、存在しない場合nullを返す
		return userInfo;
	}

	/**
	 * 新規ユーザ情報をDBに登録するメソッド
	 */
	@Override
	public void addUser(String mailAdress, String userId) throws SQLException {
		String sql = "INSERT INTO user_information_tbl (mail_address, user_id) VALUES (?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, mailAdress);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
		}
	}

	/**
	 * ユーザIDの重複をチェックするメソッド
	 * @param userId
	 * @return isExist
	 * @throws SQLException
	 */
	@Override
	public boolean isUserIdExists(String userId) throws SQLException {
		boolean isExist = false;

		//コネクションの取得
		Connection conn = getConnection();

		//ステートメントの作成
		String sql = "SELECT COUNT(*) FROM user_information_tbl WHERE user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);

		//ステートメントの実行
		ResultSet rset = pstmt.executeQuery();

		//DBに同じユーザIDが存在したらtrue、存在しなければfalseを返す
		if (rset.next()) {
			int count = rset.getInt(1);
			if (count > 0) {
				isExist = true;
			}
		}
		return isExist;
	}
}
