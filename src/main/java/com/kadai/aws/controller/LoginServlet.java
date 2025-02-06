package com.kadai.aws.controller;

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

import com.kadai.aws.model.UserInfo;
import com.kadai.aws.service.login.LoginService;
import com.kadai.aws.service.login.ValidatorService;

/**
 * ユーザーのログイン処理を担当するコントローラークラス
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(LoginServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログイン画面を表示する
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String mailAddress = request.getParameter("mailAddress");

		//メールアドレスのバリデーションチェックを行う
		ValidatorService validator = new ValidatorService();
		String mailAddressError = validator.validateMailAddress(mailAddress);
		if (mailAddressError != null) {
			handleError(request, response, "mailAddressError", mailAddressError);
            return;
		} 
		else {
			//エラーがなければ ログイン処理を行う
			LoginService loginService = new LoginService();
			UserInfo userInfo;
			try {
				userInfo = loginService.auth(mailAddress);
			} catch (SQLException e) {
				logger.error("ログイン処理中にエラーが発生しました。", e);
				handleError(request, response, "loginError", "問題が発生しログインに失敗しました。再度お試しください。");
                return;
			}

			// ログイン成功時、セッションにユーザー情報を保存する
			if (userInfo != null) {
				ServletUtils.setUserInfo(request, userInfo);

				//じゃんけん画面に遷移する
				response.sendRedirect(request.getContextPath() + "/game/Play");
			}
			//認証に失敗した場合は再度ログイン画面を出す
			else {
				handleError(request, response, "loginError", "ログインできませんでした。メールアドレスをご確認ください。");
			}
		}
	}
	
	/**
	 * エラー処理を共通化したメソッド
	 * @param request
	 * @param response
	 * @param errorKey
	 * @param errorMessage
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleError(HttpServletRequest request, HttpServletResponse response, String errorKey, String errorMessage)
            throws ServletException, IOException {
        // エラーメッセージをリクエストにセットする
        request.setAttribute(errorKey, errorMessage);
        // ログイン画面にフォワードする
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(request, response);
    }
}
