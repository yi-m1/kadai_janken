package jp.co.sfrontier.ss3.janken_game.service.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.sfrontier.ss3.janken_game.model.ResultHistory;
import jp.co.sfrontier.ss3.janken_game.repository.DbUtil;
import jp.co.sfrontier.ss3.janken_game.repository.ResultHistoryDao;

/**
 * 履歴についてのサービスを提供する。<br>
 * <br>
 * @author sf0537
 * @version 1.0.0
 */
public class HistoryService {

	private static final Logger logger
			= LogManager.getLogger(HistoryService.class);

	/**
	 * 対戦履歴を取得する。<br>
	 * <br>
	 * 該当ユーザーの全履歴を取り出す。<br>
	 * <br>
	 * @param userId このユーザーの履歴を取得する
	 * @return 履歴一覧を降順で返す。該当データが存在しない場合空のリストを返す。
	 * @throws SQLException DBへの接続エラー等が発生した場合。
	 */
	public List<ResultHistory> getHistory(int userId) throws SQLException {

		// DBから履歴情報を取得する。
		// 1. DBに繋ぐ
		// 2. DBからデータを検索してくる。
		// 3. DBから切断する。
		Connection conn = DbUtil.getConnection();
		ResultHistoryDao resultHistoryDao = new ResultHistoryDao(conn);
		try {
			List<ResultHistory> list
					= resultHistoryDao.getResultHistoryInfo(userId);
			DbUtil.commit(conn);

			return list;
		} catch (SQLException e) {
			logger.error("履歴の検索に失敗しました。[userId={}]", userId, e);
			rollback(conn);
			throw e;
		} finally {
			DbUtil.close(conn);
		}
	}

	/**
	 * ロールバックする際の例外をスローさせないために作った
	 * @param conn
	 */
	private void rollback(Connection conn) {
		try {
			DbUtil.rollback(conn);
		} catch (SQLException e) {
			logger.error("ロールバックに失敗しました", e);
		}
	}

}
