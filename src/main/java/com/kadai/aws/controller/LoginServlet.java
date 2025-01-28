package com.kadai.aws.controller;

import java.io.IOException;
import java.sql.Connection;
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
import com.kadai.aws.repository.DbUtil;
import com.kadai.aws.repository.UserInformationDao;
import com.kadai.aws.repository.UserInformationDaoImpl;
import com.kadai.aws.service.login.LoginService;
import com.kadai.aws.service.login.ValidatorService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(LoginServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// ログイン画面のフォームを表示する
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// ログイン画面のJSPからメールアドレスとユーザIDの値を受け取る
		String mailAdress = request.getParameter("mailAdress");
		String userId = request.getParameter("userId");

		//メールアドレスとユーザIDのバリデーションチェックを行う
		ValidatorService validator = new ValidatorService();
		String mailAdressError = validator.validateMailAdress(mailAdress);
		String userIdError = validator.validateUserId(userId);
		
		//ユーザIDに重複があればエラーメッセージを出す
		if(userIdError == null) {
			Connection conn = null;
			try {
				//コネクションの取得
				conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
				UserInformationDao dao = new UserInformationDaoImpl(conn);
				if (dao.isUserIdExists(userId)) {
					userIdError = "このユーザIDはすでに登録されています。";
				}
			}catch (SQLException e) {
				logger.error("ユーザIDの重複チェックでエラーが発生しました。", e);
			}finally {
				DbUtil.close(conn);
			}
		}
		
		boolean hasError = false;
		if (mailAdressError != null) {
			request.setAttribute("mailAdressError", mailAdressError);
			hasError = true;
		}
		if (userIdError != null) {
			request.setAttribute("userIdError", userIdError);
			hasError = true;
		}
		
		//バリデーションエラー・ユーザID重複エラーがあればログイン画面に遷移する
		if(hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//エラーがなければ ログイン処理を行う
		else {
			LoginService loginService = new LoginService();
			UserInfo userInfo = loginService.auth(mailAdress, userId);

			// ログイン成功時、セッションにユーザー情報を保存
			if (userInfo != null) {
				HttpSession session = request.getSession();
				session.setAttribute("userInfo", userInfo);

				//logout.jspに遷移
				response.sendRedirect(request.getContextPath() + "/game/Play");

			    
			} else {
				//認証に失敗した場合は再度ログイン画面を出す
				request.setAttribute("errorMessage", "メールアドレスまたはユーザIDが間違っています");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
