package com.kadai.aws.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DbUtil {

	public enum AutoCommitMode {
		ON, OFF,
		;
	}

	/**
	 * オートコミットモードをオフにしてコネクションを取り出す。
	 * 
	 * @return
	 * @throws SQLException コネクションの取得に失敗した場合
	 */
	public static Connection getConnection() throws SQLException {
		return getConnection(AutoCommitMode.OFF);
	}

	public static Connection getConnection(AutoCommitMode autoCommitMode) throws SQLException {
		Connection conn = null;
		if (autoCommitMode == AutoCommitMode.ON) {
			conn.setAutoCommit(true);
		} else {
			conn.setAutoCommit(false);
		}
		return conn;
	}

	public static void commit(Connection connection) throws SQLException {
		if (connection != null) {
			connection.commit();
		}
	}

	public static void rollback(Connection connection) throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.rollback();
		}
	}

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO エラーログを出す
				e.printStackTrace();
			}
		}
	}
}