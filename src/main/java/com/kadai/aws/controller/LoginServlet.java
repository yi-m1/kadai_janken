package com.kadai.aws.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kadai.aws.model.UserInfo;
import com.kadai.aws.service.login.LoginService;
import com.kadai.aws.service.login.ValidatorService;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String mailAddress = request.getParameter("mailAddress");

		//メールアドレスのバリデーションチェックを行う
		ValidatorService validator = new ValidatorService();
		String mailAddressError = validator.validateMailAddress(mailAddress);
		if (mailAddressError != null) {
			request.setAttribute("mailAddressError", mailAddressError);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			dispatcher.forward(request, response);
			return;
		} else {
			//エラーがなければ ログイン処理を行う
			LoginService loginService = new LoginService();
			UserInfo userInfo;
			try {
				userInfo = loginService.auth(mailAddress);
			} catch (SQLException e) {
				logger.error("ログイン処理中にエラーが発生しました。", e);
				request.setAttribute("loginError", "問題が発生しログインに失敗しました。再度お試しください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// ログイン成功時、セッションにユーザー情報を保存する
			if (userInfo != null) {
				HttpSession session = request.getSession();
				session.setAttribute("userInfo", userInfo);

				//じゃんけん画面に遷移する
				response.sendRedirect(request.getContextPath() + "/game/Play");
				//下記はテストページ表示用
//				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/logout.jsp");
//				dispatcher.forward(request, response);

			}
			//認証に失敗した場合は再度ログイン画面を出す
			else {
				request.setAttribute("loginError", "ログインできませんでした。メールアドレスをご確認ください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
