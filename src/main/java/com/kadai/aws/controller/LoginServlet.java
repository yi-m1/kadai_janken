package com.kadai.aws.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kadai.aws.model.UserInfo;
import com.kadai.aws.service.login.LoginService;
import com.kadai.aws.service.login.ValidatorService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		
		boolean hasError = false;
		if (mailAdressError != null) {
			request.setAttribute("mailAdressError", mailAdressError);
			hasError = true;
		}
		if (userIdError != null) {
			request.setAttribute("userIdError", userIdError);
			hasError = true;
		}
		
		//バリデーションエラーがあればログイン画面に遷移する
		if(hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//バリデーションエラーがなければ ログイン処理を行う
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
