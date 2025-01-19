package com.kadai.aws.sevice.login;

import java.sql.Connection;
import java.sql.SQLException;

import com.kadai.aws.dao.DbUtil;
import com.kadai.aws.dao.UserInformationDao;
import com.kadai.aws.dao.UserInformationDaoImpl;
import com.kadai.aws.dao.entity.UserInformation;
import com.kadai.aws.entity.UserInfo;

public class LoginService {

	public UserInfo auth(String userId, String password) {
		UserInfo result = null;
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			UserInformationDao dao = new UserInformationDaoImpl(conn);
			UserInformation info = dao.findByUserIdAndPassword(userId, password);
			//infoがnullじゃなかったらUserInfoに変換して返す
			DbUtil.commit(conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DbUtil.close(conn);
		}
		return result;
	}
}