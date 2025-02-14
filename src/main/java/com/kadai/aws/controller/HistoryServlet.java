package com.kadai.aws.controller;

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

import com.kadai.aws.model.ResultHistory;
import com.kadai.aws.model.UserInfo;
import com.kadai.aws.repository.DbUtil;
import com.kadai.aws.repository.ResultHistoryDao;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		List<ResultHistory> resultHistory = new ArrayList<ResultHistory>();
		Connection conn = null;
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		int userId = 2;
		if (userInfo != null) {
			userId = userInfo.getUserId();
		}
		try {
			conn = DbUtil.getConnection(DbUtil.AutoCommitMode.OFF);
			ResultHistoryDao resultHistoryDao = new ResultHistoryDao(conn);
			resultHistory = resultHistoryDao.getResultHistoryInfo(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(conn);
		}
		request.setAttribute("history", resultHistory);
		request.getRequestDispatcher("/WEB-INF/views/history.jsp").forward(request, response);
	}

}
