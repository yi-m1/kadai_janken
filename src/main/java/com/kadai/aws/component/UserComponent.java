package com.kadai.aws.component;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kadai.aws.model.UserInfo;
import com.kadai.aws.repository.DbUtil;
import com.kadai.aws.repository.UserInformationDao;
import com.kadai.aws.repository.UserInformationDaoImpl;

/**
 * ユーザ情報に関連する処理を担当するクラス
 */
public class UserComponent {
	private static final Logger logger = LogManager.getLogger(UserComponent.class);

	public enum UserCheckResult {
		NOT_FOUND, CONTAIN_MAIL_ADDRESS, CONTAIN_USERNAME;
	}

	/**
	 * メールアドレスとユーザー名が既にデータベースに存在するかどうかをチェックするメソッド
	 * @param mailAddress
	 * @param userName
	 * @return UserCheckResult
	 * @throws SQLException
	 */
	public UserCheckResult existUser(String mailAddress, String userName) throws SQLException {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
			UserInformationDao dao = new UserInformationDaoImpl(conn);
			if (dao.isMailAddressExists(mailAddress)) {
				return UserCheckResult.CONTAIN_MAIL_ADDRESS;
			}
			if (dao.isUserNameExists(userName)) {
				return UserCheckResult.CONTAIN_USERNAME;
			}
			return UserCheckResult.NOT_FOUND;

		} catch (SQLException e) {
			logger.error("メールアドレスの重複チェックでエラーが発生しました。", e);
			throw e;

		} finally {
			DbUtil.close(conn);
		}
	}

	/**
	 * 新規ユーザをDBに登録するメソッド
	 * @param mailAddress
	 * @param userName
	 * @return userInfo
	 * @throws SQLException
	 */
	public UserInfo register(String mailAddress, String userName) throws SQLException {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
			UserInformationDao dao = new UserInformationDaoImpl(conn);

			//ユーザ登録をする
			dao.addUser(mailAddress, userName);

			//登録したユーザの情報を取り出す
			UserInfo userInfo = dao.findByMailAddress(mailAddress);

			DbUtil.commit(conn);
			return userInfo;

		} catch (SQLException e) {
			logger.error("ユーザ登録に失敗しました。", e);
			//エラーが発生したらロールバックを行う
			DbUtil.rollback(conn);
			throw e;

		} finally {
			DbUtil.close(conn);
		}
	}
}
