package com.kadai.aws.service.login;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kadai.aws.model.UserInfo;
import com.kadai.aws.repository.DbUtil;
import com.kadai.aws.repository.UserInformationDao;
import com.kadai.aws.repository.UserInformationDaoImpl;

public class LoginService {

	private static final Logger logger = LogManager.getLogger(LoginService.class);

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