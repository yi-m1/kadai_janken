package com.kadai.aws.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kadai.aws.controller.ServletUtils;

@WebFilter(urlPatterns = { "/game/Play", "/history" })
public class LoginFilter extends HttpFilter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// ユーザー情報がセッションに存在しない場合（未ログイン状態）
		if (ServletUtils.getUserInfo(request) == null) {
			// ログイン画面にリダイレクトする
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			// ログイン済みの場合、次のサーブレットを実行する
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}
}
