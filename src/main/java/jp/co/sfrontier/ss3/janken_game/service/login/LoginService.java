package jp.co.sfrontier.ss3.janken_game.service.login;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.sfrontier.ss3.janken_game.model.UserInfo;
import jp.co.sfrontier.ss3.janken_game.repository.DbUtil;
import jp.co.sfrontier.ss3.janken_game.repository.UserInformationDao;
import jp.co.sfrontier.ss3.janken_game.repository.UserInformationDaoImpl;

/**
 * ユーザ認証に関連するサービスクラス
 */
public class LoginService {

	private static final Logger logger = LogManager.getLogger(LoginService.class);

	/**
	 * メールアドレスを使ってユーザ情報をデータベースから検索し、認証を行うメソッド
	 * @param mailAddress
	 * @return ユーザ情報が見つかればuserInfo、見つからなければnull
	 * @throws SQLException データベース接続や検索時にエラーが発生した場合
	 */
	public UserInfo auth(String mailAddress) throws SQLException {
		UserInfo userInfo = null;
		Connection conn = null;
		try {
			//コネクションを取得する
			conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
			UserInformationDao dao = new UserInformationDaoImpl(conn);

			//既存のユーザを探す
			userInfo = dao.findByMailAddress(mailAddress);
			//既存のユーザが見つからない場合
			if (userInfo == null) {
				return userInfo;
			}
			DbUtil.commit(conn);

		} catch (SQLException e) {
			logger.error("DB接続に失敗しました。", e);
			throw e;

		} finally {
			DbUtil.close(conn);
		}
		return userInfo;
	}

}