package jp.co.sfrontier.ss3.janken_game.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jp.co.sfrontier.ss3.janken_game.model.ResultHistory;

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
				sql = "SELECT ui1.user_name AS user_name, TO_CHAR(rh.execute_datetime, 'YYYY/MM/DD HH24:MI:SS') AS execute_datetime, ui2.user_name AS opponent_name, rh.result FROM result_history_tbl rh INNER JOIN user_information_tbl ui1 ON rh.user_id = ui1.user_id INNER JOIN user_information_tbl ui2 ON rh.opponent = ui2.user_id ORDER BY rh.execute_datetime DESC LIMIT 100";
				pstmt = conn.prepareStatement(sql);
			} else {
				sql = "SELECT ui1.user_name AS user_name, TO_CHAR(rh.execute_datetime, 'YYYY/MM/DD HH24:MI:SS') AS execute_datetime, ui2.user_name AS opponent_name, rh.result FROM result_history_tbl rh INNER JOIN user_information_tbl ui1 ON rh.user_id = ui1.user_id INNER JOIN user_information_tbl ui2 ON rh.opponent = ui2.user_id WHERE user_id = ? ORDER BY rh.execute_datetime DESC LIMIT 100";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, userId);
			}
		} else {
			sql = "SELECT ui1.user_name AS user_name, TO_CHAR(rh.execute_datetime, 'YYYY/MM/DD HH24:MI:SS') AS execute_datetime, ui2.user_name AS opponent_name, rh.result FROM result_history_tbl rh INNER JOIN user_information_tbl ui1 ON rh.user_id = ui1.user_id INNER JOIN user_information_tbl ui2 ON rh.opponent = ui2.user_id ORDER BY rh.execute_datetime DESC LIMIT 100";
			pstmt = conn.prepareStatement(sql);
		}

		// ステートメント実行
		ResultSet rset = pstmt.executeQuery();

		while (rset.next()) {
			// オブジェクトにデータを一時格納
			ResultHistory resuhisInfo = new ResultHistory();
			resuhisInfo.setUserName(rset.getString("user_name"));
			resuhisInfo.setExecuteDatetime(rset.getString("execute_datetime"));
			resuhisInfo.setOpponent(rset.getString("opponent_name"));
			resuhisInfo.setResult(rset.getString("result"));
			// 一時格納したデータをリストに追加
			resuhisList.add(resuhisInfo);
		}
		pstmt.close();
		return resuhisList;
	}
}
