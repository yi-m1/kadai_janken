package com.kadai.aws.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kadai.aws.model.ResultHistory;

/**
 * 履歴周りのデータベース処理をまとめたクラス
 */
public class ResultHistoryDao {

	private Connection connection;

	public ResultHistoryDao(Connection connection) {
		this.connection = connection;
	}

	private Connection getConnection() {
		if (connection == null) {
			throw new IllegalStateException("Connection is not initialized");
		}
		return connection;
	}

	/**
	 * 対戦履歴情報を取得
	 * @param userId ユーザID
	 * @return DBにある対戦履歴をリストに格納したもの または 空のList
	 * @throws SQLException
	 */
	public List<ResultHistory> getResultHistoryInfo(int userId) throws SQLException {

		String sql;
		PreparedStatement pstmt;

		// 取得した対戦履歴をリストに格納するため用意
		List<ResultHistory> resuhisList = new ArrayList<ResultHistory>();

		// コネクション取得
		Connection conn = getConnection();

		// ステートメント作成
		if (Optional.ofNullable(Integer.valueOf(userId)).isPresent()) {
			if (userId == 2) {
				sql = "SELECT user_id, execute_datetime, opponent, result FROM result_history_tbl";
				pstmt = conn.prepareStatement(sql);
			} else {
				sql = "SELECT user_id, execute_datetime, opponent, result FROM result_history_tbl WHERE user_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, userId);
			}
		} else {
			sql = "SELECT user_id, execute_datetime, opponent, result FROM result_history_tbl";
			pstmt = conn.prepareStatement(sql);
		}

		// ステートメント実行
		ResultSet rset = pstmt.executeQuery();

		while (rset.next()) {
			// オブジェクトにデータを一時格納
			ResultHistory resuhisInfo = new ResultHistory();
			resuhisInfo.setUserId(rset.getInt("user_id"));
			resuhisInfo.setExecuteDatetime(rset.getDate("execute_datetime"));
			resuhisInfo.setOpponent(rset.getInt("opponent"));
			resuhisInfo.setResult(rset.getString("result"));
			// 一時格納したデータをリストに追加
			resuhisList.add(resuhisInfo);
		}
		pstmt.close();
		return resuhisList;
	}
}
