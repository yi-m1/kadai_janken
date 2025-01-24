package com.kadai.aws.controller;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * サンプルコード<br>
 * 画面からの入力を受け取ったりするクラス
 */
//@WebServlet("/hello")
public class SampleServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("message", "こんにちは！");
		req.getRequestDispatcher("/WEB-INF/views/hello.jsp").forward(req, resp);
	}

}
