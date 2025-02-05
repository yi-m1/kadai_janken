package com.kadai.aws.service.login;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kadai.aws.repository.DbUtil;
import com.kadai.aws.repository.UserInformationDao;
import com.kadai.aws.repository.UserInformationDaoImpl;

public class RegisterService {

	private static final Logger logger = LogManager.getLogger(RegisterService.class);

	public void registerUser(String mailAddress, String userName) throws SQLException {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
			UserInformationDao dao = new UserInformationDaoImpl(conn);
			
			//ユーザ登録をする
			dao.addUser(mailAddress, userName);
			DbUtil.commit(conn);
		} catch (SQLException e) {
			logger.error("ユーザ登録に失敗しました。", e);
			//エラーが発生したらロールバックを行う
			DbUtil.rollback(conn);
			throw e;
		}
	}
}
