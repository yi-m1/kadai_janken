package jp.co.sfrontier.ss3.janken_game.service.login;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.sfrontier.ss3.janken_game.component.UserComponent;
import jp.co.sfrontier.ss3.janken_game.model.UserInfo;

/**
 * ユーザの新規登録処理を行うサービスクラス
 */
public class RegisterService {

	private static final Logger logger = LogManager.getLogger(RegisterService.class);

	/**
	 * 新規ユーザーを登録するメソッド
	 * @param mailAddress
	 * @param userName
	 * @return userInfo
	 * @throws SQLException データベース処理でエラーが発生した場合
	 */
	public UserInfo registerUser(String mailAddress, String userName) throws SQLException {
		return (new UserComponent().register(mailAddress, userName));
	}
}