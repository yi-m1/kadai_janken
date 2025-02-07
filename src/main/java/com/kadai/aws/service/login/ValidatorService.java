package com.kadai.aws.service.login;

import java.sql.SQLException;

import com.kadai.aws.component.UserComponent;
import com.kadai.aws.component.UserComponent.UserCheckResult;

/**
 * バリデーションチェックを行うサービスクラス
 */
public class ValidatorService {

	/**
	 * メールアドレスのバリデーションチェックを行うメソッド
	 * @param mailAddress
	 * @return バリデーション成功時はnull、バリデーション失敗時はエラーメッセージ
	 */
	public String validateMailAddress(String mailAddress) {
		if (mailAddress == null || mailAddress.isEmpty()) {
			return "メールアドレスを入力してください。";
		}
		String mailAddressRegex = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		if (!mailAddress.matches(mailAddressRegex)) {
			return "メールアドレスの形式が正しくありません。";
		}
		if (mailAddress.length() > 255) {
			return "メールアドレスは255文字以内で入力してください。";
		}
		return null;
	}

	/**
	 * ユーザIDのバリデーションチェックを行うメソッド
	 * @param userName
	 * @return バリデーション成功時はnull、バリデーション失敗時はエラーメッセージ
	 */
	public String validateUserName(String userName) {
		if (userName == null || userName.isEmpty()) {
			return "ユーザ名を入力してください。";
		}
		String userNameRegex = "^[A-Za-z0-9_-]{1,100}$";
		if (!userName.matches(userNameRegex)) {
			return "ユーザ名は半角英数字、アンダースコア(_)、ハイフン(-)のみを使用し、\n最大100文字以内で入力してください。";
		}
		return null;
	}

	/**
	 * UserComponentクラスのexistUserメソッドを呼び出し、メールアドレスとユーザー名の重複をチェックするメソッド
	 * @param mailAddress
	 * @param userName
	 * @return UserCheckResult
	 * @throws SQLException
	 */
	public UserCheckResult validateDuplicateUser(String mailAddress, String userName) throws SQLException {
		return (new UserComponent().existUser(mailAddress, userName));
	}
}
