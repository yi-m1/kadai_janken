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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doTask(request, response);
    }
    
    private void doTask (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// セッションを破棄する
        HttpSession session = request.getSession();
        session.invalidate();

        // ログイン画面に遷移する
	    response.sendRedirect(request.getContextPath() + "/Login");
	}
}