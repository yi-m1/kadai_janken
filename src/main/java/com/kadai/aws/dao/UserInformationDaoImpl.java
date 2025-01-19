/**
 * 
 */
package com.kadai.aws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kadai.aws.dao.entity.UserInformation;

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
			//例外を投げる
		}
		return connection;
	}

	@Override
	public UserInformation findByUserIdAndPassword(String userId, String password) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		//コネクションの取得
		Connection conn = getConnection();
		//ステートメントの作成
		String sql = "";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(0, userId);
		pstmt.setString(1, password);
		//ステートメントの実行
		ResultSet rset = pstmt.executeQuery();
		//結果の取り出し
		
		pstmt.close();

		return null;
	}

}
