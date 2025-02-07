package com.kadai.aws.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

	public enum AutoCommitMode {
		ON, OFF,
		;
	}
	
	private static final String URL = "jdbc:hsqldb:hsql://localhost/";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

	/**
	 * オートコミットモードをオフにしてコネクションを取り出す。
	 * 
	 * @return コネクション
	 * @throws SQLException コネクションの取得に失敗した場合
	 */
	public static Connection getConnection() throws SQLException {
		return getConnection(AutoCommitMode.OFF);
	}

	public static Connection getConnection(AutoCommitMode autoCommitMode) throws SQLException {
		// HSQLDB JDBCドライバの手動ロード
	    try {
	        Class.forName("org.hsqldb.jdbc.JDBCDriver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        throw new SQLException("HSQLDB JDBC driver not found", e);
	    }
	    
	    //コネクションの取得
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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
				e.printStackTrace();
			}
		}
	}
}