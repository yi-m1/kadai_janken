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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doTask(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doTask(request, response);
    }

    // 実際の処理を行うメソッド
    private void doTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションを取得する
        HttpSession session = request.getSession(false);

        if (session != null) {
            // セッションが存在する場合、セッションを無効化する
            session.invalidate();
        }

        // ログアウト後にログイン画面にリダイレクトする
        response.sendRedirect(request.getContextPath() + "/login");
    }
}