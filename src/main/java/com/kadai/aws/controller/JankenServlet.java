package com.kadai.aws.controller;

import com.kadai.aws.model.GameResult;
import com.kadai.aws.model.JankenChoice;
import com.kadai.aws.service.JankenService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/play")
public class JankenServlet extends HttpServlet {

    private final JankenService jankenService = new JankenService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userChoiceString = request.getParameter("choice");

        // ユーザーの選択をJankenChoiceに変換
        JankenChoice userChoice = JankenChoice.fromString(userChoiceString);

        // playJanken メソッドを呼び出してゲームの結果を取得
        GameResult result = jankenService.playJanken(userChoice);

        // 結果をJSPに渡す
        request.setAttribute("userChoice", result.getUserChoice());
        request.setAttribute("computerChoice", result.getAiChoice());
        request.setAttribute("resultMessage", result.getResultMessage());

        // 同じ画面 (index.jsp) にフォワード
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
