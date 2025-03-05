package jp.co.sfrontier.ss3.janken_game.controller.login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.sfrontier.ss3.janken_game.component.UserComponent;
import jp.co.sfrontier.ss3.janken_game.component.UserComponent.UserCheckResult;
import jp.co.sfrontier.ss3.janken_game.controller.util.ServletUtils;
import jp.co.sfrontier.ss3.janken_game.model.UserInfo;
import jp.co.sfrontier.ss3.janken_game.service.login.RegisterService;
import jp.co.sfrontier.ss3.janken_game.service.login.ValidatorService;

/**
 * 新規ユーザー登録処理を行うコントローラークラス
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(RegisterServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//新規ユーザ登録画面を表示する
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String mailAddress = request.getParameter("mailAddress");
		String userName = request.getParameter("userName");

		//メールアドレスとユーザ名のバリデーションチェックを行う
		ValidatorService validator = new ValidatorService();
		String mailAddressError = validator.validateMailAddress(mailAddress);
		String userNameError = validator.validateUserName(userName);

		if (mailAddressError != null || userNameError != null) {
			handleError(request, response, "mailAddressError", mailAddressError, "userNameError", userNameError, mailAddress, userName);
			return;
		}

		//ユーザ情報が既に使用されていないか確認する
		try {
			UserComponent.UserCheckResult userCheckResult = validator.validateDuplicateUser(mailAddress, userName);

			if (userCheckResult == UserCheckResult.CONTAIN_MAIL_ADDRESS) {
				handleError(request, response, "mailAddressError", "メールアドレスが既に使われています。", null, null, mailAddress, userName);
				return;
			}
			if (userCheckResult == UserCheckResult.CONTAIN_USERNAME) {
				handleError(request, response, null, null, "userNameError", "ユーザ名が既に使われています。", mailAddress, userName);
				return;
			}

		} catch (SQLException e) {
			logger.error("データベース接続時にエラーが発生しました。", e);

			// ユーザーに表示するエラーメッセージをリクエストにセット
			handleError(request, response, "registerError", "問題が発生しユーザ登録に失敗しました。再度お試しください。", null, null, null, null);
			throw new IOException("ユーザー情報の確認中に問題が発生しました。", e);
		}

		//エラーがなければ新規登録処理を行う
		RegisterService registerService = new RegisterService();
		try {
			UserInfo userInfo = registerService.registerUser(mailAddress, userName);

			// セッションにユーザ情報を保存する
			ServletUtils.setUserInfo(request, userInfo);

		} catch (SQLException e) {
			logger.error("ユーザ登録中にエラーが発生しました。", e);
			//登録でエラーが発生した場合、エラーメッセージを表示する。
			handleError(request, response, "registerError", "問題が発生しユーザ登録に失敗しました。再度お試しください。", null, null, null, null);
			return;
		}

		//じゃんけん画面に遷移する
		response.sendRedirect(request.getContextPath() + "/game/Play");
	}

	/**
	 * エラー処理を共通化したメソッド
	 * @param request
	 * @param response
	 * @param errorKey1
	 * @param errorMessage1
	 * @param errorKey2
	 * @param errorMessage2
	 * @param mailAddress
	 * @param userName
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleError(HttpServletRequest request, HttpServletResponse response,
			String errorKey1, String errorMessage1, String errorKey2, String errorMessage2,
			String mailAddress, String userName)
			throws ServletException, IOException {

		// エラーメッセージをリクエストにセットする
		if (errorKey1 != null) {
			request.setAttribute(errorKey1, errorMessage1);
		}
		if (errorKey2 != null) {
			request.setAttribute(errorKey2, errorMessage2);
		}
		// 登録画面にフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
		dispatcher.forward(request, response);
	}
}