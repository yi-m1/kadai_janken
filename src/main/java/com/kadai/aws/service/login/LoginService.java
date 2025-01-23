package com.kadai.aws.service.login;

import java.sql.Connection;
import java.sql.SQLException;

import com.kadai.aws.model.UserInfo;
import com.kadai.aws.repository.DbUtil;
import com.kadai.aws.repository.UserInformationDao;
import com.kadai.aws.repository.UserInformationDaoImpl;

public class LoginService {

	public UserInfo auth(String mailAdress, String userId) {
		UserInfo result = null;
		Connection conn = null;
		try {
			//コネクションの取得
			conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
			UserInformationDao dao = new UserInformationDaoImpl(conn);
			
			//既存のユーザを探す
			result = dao.findByMailAdressAndUserId(mailAdress, userId);
			
			if(result == null) {
				//ユーザが存在しない場合、新規ユーザを追加
				dao.addUser(mailAdress, userId);
				//新規ユーザオブジェクトを作成
				result = new UserInfo();
				result.setMailAddress(mailAdress);
                result.setUserId(userId);
			}
			//コミット
			DbUtil.commit(conn);

		} catch (SQLException e1) {
			e1.printStackTrace();
			//ロールバック
			try {
				if(conn != null) {
					DbUtil.rollback(conn);
				}
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			DbUtil.close(conn);
		}
		return result;
	}
}