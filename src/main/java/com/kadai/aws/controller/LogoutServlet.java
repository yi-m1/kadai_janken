package com.kadai.aws.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// GET メソッドを追加して、GETリクエストにも対応
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doTask(request, response);
    }

    // POST メソッド
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doTask(request, response);
    }

    // 実際の処理を行うメソッド
    private void doTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションを取得
        HttpSession session = request.getSession(false); // false: セッションがない場合は新しく作成しない

        if (session != null) {
            // セッションが存在する場合、セッションを無効化
            session.invalidate();
        }

        // ログアウト後にログイン画面にリダイレクト
        response.sendRedirect(request.getContextPath() + "/login");
    }
}