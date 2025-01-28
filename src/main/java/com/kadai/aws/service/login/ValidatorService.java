package com.kadai.aws.service.login;

/**
 * ログイン時にメールアドレスとユーザIDのバリデーションチェックを行うクラス
 */
public class ValidatorService {

	/**
	 * メールアドレスのバリデーションチェックを行う
	 * @param mailAdress
	 * @return バリデーション成功時はnull、バリデーション失敗時はエラーメッセージ
	 */
	public String validateMailAdress(String mailAdress) {
		if (mailAdress == null || mailAdress.isEmpty()) {
			return "メールアドレスを入力してください。";
		}
		String mailAdressRegex = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		if (!mailAdress.matches(mailAdressRegex)) {
			return "メールアドレスの形式が正しくありません。";
		}
		return null;
	}

	/**
	 * ユーザIDのバリデーションチェックを行う
	 * @param userId
	 * @return バリデーション成功時はnull、バリデーション失敗時はエラーメッセージ
	 */
	public String validateUserId(String userId) {
		if (userId == null || userId.isEmpty()) {
			return "ユーザIDを入力してください。";
		}
		String userIdRegex = "^[A-Za-z0-9]{1,20}$";
		if (!userId.matches(userIdRegex)) {
			return "ユーザIDは半角英数字で最大20文字までです。";
		}
		return null;
	}
}
