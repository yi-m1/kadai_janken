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
import com.kadai.aws.service.login.RegisterService;
import com.kadai.aws.service.login.ValidatorService;

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
		String mailAddress = request.getParameter("mailAddress");
		String userName = request.getParameter("userName");

		//メールアドレスとユーザ名のバリデーションチェックを行う
		ValidatorService validator = new ValidatorService();
		String mailAddressError = validator.validateMailAddress(mailAddress);
		String userNameError = validator.validateUserName(userName);

		//メールアドレスとユーザ名の重複チェックを行う
		if (mailAddressError == null) {
			Connection conn = null;
			try {
				conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
				UserInformationDao dao = new UserInformationDaoImpl(conn);
				if (dao.isMailAddressExists(mailAddress)) {
					mailAddressError = "このメールアドレスはすでに使われています。";
				}
			} catch (SQLException e) {
				logger.error("メールアドレスの重複チェックでエラーが発生しました。", e);
				request.setAttribute("registerError", "問題が発生しユーザ登録に失敗しました。再度お試しください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
				dispatcher.forward(request, response);
				return;
			} finally {
				DbUtil.close(conn);
			}
		}
		if (userNameError == null) {
			Connection conn = null;
			try {
				conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
				UserInformationDao dao = new UserInformationDaoImpl(conn);
				if (dao.isUserNameExists(userName)) {
					userNameError = "このユーザ名はすでに使われています。";
				}
			} catch (SQLException e) {
				logger.error("ユーザ名の重複チェックでエラーが発生しました。", e);
				request.setAttribute("registerError", "問題が発生しユーザ登録に失敗しました。再度お試しください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
				dispatcher.forward(request, response);
				return;
			} finally {
				DbUtil.close(conn);
			}
		}

		boolean hasError = false;
		if (mailAddressError != null) {
			request.setAttribute("mailAddressError", mailAddressError);
			hasError = true;
		}
		if (userNameError != null) {
			request.setAttribute("userNameError", userNameError);
			hasError = true;
		}

		//バリデーションエラー・重複エラーがあれば新規ユーザ登録画面に遷移する
		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
			dispatcher.forward(request, response);
			return;
		} else {
			//エラーがなければ新規登録処理を行う
			RegisterService registerService = new RegisterService();
			try {
				registerService.registerUser(mailAddress, userName);
			} catch (SQLException e) {
				logger.error("ユーザ登録中にエラーが発生しました。", e);
				//登録でエラーが発生した場合、エラーメッセージを表示する。
				request.setAttribute("registerError", "問題が発生しユーザ登録に失敗しました。再度お試しください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
				dispatcher.forward(request, response);
				return;
			}

			//新規登録後、ログイン処理を行う
			LoginService loginService = new LoginService();
			UserInfo userInfo;
			try {
				userInfo = loginService.auth(mailAddress);
			} catch (SQLException e) {
				logger.error("ログイン処理中にエラーが発生しました。", e);
				request.setAttribute("registerError", "問題が発生しログインに失敗しました。再度お試しください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
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
		}
	}
}
