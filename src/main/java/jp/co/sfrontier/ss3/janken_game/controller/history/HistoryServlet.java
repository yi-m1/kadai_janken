package jp.co.sfrontier.ss3.janken_game.controller.history;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sfrontier.ss3.janken_game.controller.util.ServletUtils;
import jp.co.sfrontier.ss3.janken_game.model.ResultHistory;
import jp.co.sfrontier.ss3.janken_game.model.UserInfo;
import jp.co.sfrontier.ss3.janken_game.repository.DbUtil;
import jp.co.sfrontier.ss3.janken_game.repository.ResultHistoryDao;
import jp.co.sfrontier.ss3.janken_game.service.history.HistoryService;

/**
 * 履歴画面のサーブレット
 */
@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

	/**
	 * じゃんけん画面にある対戦履歴ボタンを押下した後に実行されるメソッド<br>
	 * DBから対戦履歴を取得表示
	 *
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		try {
			UserInfo userInfo = ServletUtils.getUserInfo(request);
			// 履歴情報を取り出す
			HistoryService historyService = new HistoryService();
			List<ResultHistory> resultHistory
					= historyService.getHistory(userInfo.getUserId());

			// レスポンスを作成する
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			request.setAttribute("history", resultHistory);
			request.getRequestDispatcher("/WEB-INF/views/history.jsp").forward(
					request, response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
