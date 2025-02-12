package jp.co.sfrontier.ss3.janken_game.controller;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sfrontier.ss3.janken_game.model.GameResult;
import jp.co.sfrontier.ss3.janken_game.model.JankenChoice;
import jp.co.sfrontier.ss3.janken_game.repository.DbUtil;
import jp.co.sfrontier.ss3.janken_game.repository.JankenGameDaoImpl;
import jp.co.sfrontier.ss3.janken_game.service.JankenService;

@WebServlet("/play")
public class JankenServlet extends HttpServlet {

	private final JankenService jankenService = new JankenService();

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		String userChoiceString = request.getParameter("choice");

		// ユーザーの選択をJankenChoiceに変換
		JankenChoice userChoice = JankenChoice.fromString(userChoiceString);

		// playJanken メソッドを呼び出してゲームの結果を取得
		GameResult result = jankenService.playJanken(userChoice);
		try {
			connection = DbUtil.getConnection(DbUtil.AutoCommitMode.ON);
			JankenGameDao jankenGameDaoImpl
					= new JankenGameDaoImpl(connection);
			jankenGameDaoImpl.recordGameResult(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}

		// 結果をJSPに渡す
		request.setAttribute("userChoice", result.getUserChoice());
		request.setAttribute("computerChoice", result.getAiChoice());
		request.setAttribute("resultMessage", result.getResultMessage());

		// 同じ画面 (index.jsp) にフォワード
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
