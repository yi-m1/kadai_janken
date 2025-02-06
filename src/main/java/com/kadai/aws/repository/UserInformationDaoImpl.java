package com.kadai.aws.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kadai.aws.model.UserInfo;

/**
 * データベースにアクセスしてユーザー情報に関連する操作を行うDAOクラス
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
	public UserInfo findByMailAddress(String mailAddress) throws SQLException {
		//コネクションの取得
		Connection conn = getConnection();

		//ステートメントの作成
		String sql = "SELECT user_id, mail_address, user_name FROM user_information_tbl WHERE mail_address = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, mailAddress);

		//ステートメントの実行
		ResultSet rset = pstmt.executeQuery();

		//結果の取り出し
		UserInfo userInfo = null;
		if (rset.next()) {
			userInfo = new UserInfo();
			userInfo.setUserId(rset.getInt("user_id"));
			userInfo.setMailAddress(rset.getString("mail_address"));
			userInfo.setUserName(rset.getString("user_name"));
		}
		pstmt.close();

		//ユーザが存在するときはユーザ情報を返し、存在しない場合nullを返す
		return userInfo;
	}

	/**
	 * 新規ユーザ情報をDBに登録するメソッド
	 */
	@Override
	public void addUser(String mailAddress, String userName) throws SQLException {
		String sql = "INSERT INTO user_information_tbl (user_name, mail_address) VALUES (?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, userName);
			pstmt.setString(2, mailAddress);
			pstmt.executeUpdate();
		}
	}
	
	/**
	 * メールアドレスがDBに登録されているか確認するメソッド
	 * @param mailAddress
	 * @return isExist
	 * @throws SQLException
	 */
	@Override
	public boolean isMailAddressExists(String mailAddress) throws SQLException {
		boolean isExist = false;

		//コネクションの取得
		Connection conn = getConnection();

		//ステートメントの作成
		String sql = "SELECT COUNT(*) FROM user_information_tbl WHERE mail_address = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, mailAddress);

		//ステートメントの実行
		ResultSet rset = pstmt.executeQuery();

		//DBに同じユーザ名が存在したらtrue、存在しなければfalseを返す
		if (rset.next()) {
			int count = rset.getInt(1);
			if (count > 0) {
				isExist = true;
			}
		}
		return isExist;
	}
	
	/**
	 * ユーザ名がDBに登録されているか確認するメソッド
	 * @param userName
	 * @return isExist
	 * @throws SQLException
	 */
	@Override
	public boolean isUserNameExists(String userName) throws SQLException {
		boolean isExist = false;

		//コネクションの取得
		Connection conn = getConnection();

		//ステートメントの作成
		String sql = "SELECT COUNT(*) FROM user_information_tbl WHERE user_name = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userName);

		//ステートメントの実行
		ResultSet rset = pstmt.executeQuery();

		//DBに同じユーザ名が存在したらtrue、存在しなければfalseを返す
		if (rset.next()) {
			int count = rset.getInt(1);
			if (count > 0) {
				isExist = true;
			}
		}
		return isExist;
	}
}
