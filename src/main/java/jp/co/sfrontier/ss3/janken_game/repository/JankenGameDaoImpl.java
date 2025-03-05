/**
 * JankenGameDaoImpl.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package jp.co.sfrontier.ss3.janken_game.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.sfrontier.ss3.janken_game.model.GameResult;

/**
 * JankenGameDaoImplクラス
 *
 * @author FLM
 * @version 1.0.0
 */
public class JankenGameDaoImpl implements JankenGameDao {

	private Connection connection;

	// コンストラクタ（JankenGameDaoImplを初期化）
	public JankenGameDaoImpl(Connection connection) {
		this.connection = connection;
	}

	private Connection getConnection() {
		if (connection == null) {
			throw new IllegalStateException("Connection is not initialized");
		}
		return connection;
	}

	/**
	 * ゲームの結果をデータベースに記録します
	 *
	 * @param gameResult ゲームの結果
	 * @throws SQLException SQL例外が発生した場合
	 */
	@Override
	public void recordGameResult(GameResult gameResult) throws SQLException {
		String sql
				= "INSERT INTO result_history_tbl (user_id, execute_datetime, opponent, result, user_choice, create_datetime, update_datetime, version) "
						+
						"VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1)";

		try (PreparedStatement preparedStatement
				= getConnection().prepareStatement(sql)) {
			// SQLにパラメータをセット
			preparedStatement.setInt(1, gameResult.getUserId()); // user_id
			preparedStatement.setInt(2, gameResult.getUserId());
			preparedStatement.setString(3, gameResult.getResultMessage()); // result
			preparedStatement.setString(4, gameResult.getUserChoice()); // user_choice

			// SQL文を実行
			int rowsAffected = preparedStatement.executeUpdate();

			// もし何も挿入されなかった場合に警告ログを出す（任意）
			if (rowsAffected == 0) {
				System.err.println("No rows inserted for the game result.");
			}
		} catch (SQLException e) {
			// 詳細なエラーメッセージを追加
			throw new SQLException(
					"Error while recording game result for user ID: "
							+ gameResult.getUserId(),
					e);
		}
	}
}
