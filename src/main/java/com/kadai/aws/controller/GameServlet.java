package com.kadai.aws.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.kadai.aws.model.UserInfo;
import com.kadai.aws.service.JankenServiceTomari;

import common.Hand;
import value.Player;

/**
 * じゃんけんゲームをするためのコントローラークラス
 */
@WebServlet("/game/Play")
public class GameServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(GameServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Game Start");

        // JSP にディスパッチ
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/game/play.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * プレイヤーが選んだ手を受け取り、じゃんけんの結果を計算するPOSTリクエストの処理メソッド。
     * クライアントにゲームの結果をJSON形式で返す。
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Game Play");

        UserInfo userInfo = ServletUtils.getUserInfo(request);

        String playerHand = request.getParameter("hand");

        Hand hand = Hand.get(playerHand);

        // 不正な入力の場合エラーを返す
        if (hand == null) {
            logger.debug("input error{hand={}}", playerHand);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "ERROR");
            putResponse(response, jsonResponse);
            return;
        }
        // じゃんけんの処理を行うサービスオブジェクトを作成
        JankenServiceTomari jankenService = new JankenServiceTomari();
        // プレイヤーオブジェクトを作成
        Player player = new Player(userInfo.getUserId(), hand);
        logger.debug("call jankenService");
        try {
            int result = jankenService.fight(player);
            logger.debug("jankenResult{result={}}",result);
            // 結果としてJSONを返す
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "OK");
            jsonResponse.put("cpuHand", getCpuHand(hand, result));
            jsonResponse.put("result", Integer.valueOf(result));

            putResponse(response, jsonResponse);

        } catch (SQLException e) {
            logger.error("じゃんけんの処理中にエラーが発生しました", e);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "ERROR");
            putResponse(response, jsonResponse);
        }
    }

    /**
     * プレイヤーとCPUの手を比較して、CPUの手を決定するメソッド。
     * じゃんけんの結果（勝ち/負け/あいこ）に基づき、CPUの手を返す。
     */
    private String getCpuHand(Hand hand, int result) {
        Hand cpuHand = hand;
        if (result > 0) {
            switch (hand) {
            case Hand.ROCK:
                cpuHand = Hand.SCISSORS;
                break;
            case Hand.SCISSORS:
                cpuHand = Hand.PAPER;
                break;
            case Hand.PAPER:
                cpuHand = Hand.ROCK;
                break;

            }
        } else if (result < 0) {
            switch (hand) {
            case Hand.ROCK:
                cpuHand = Hand.PAPER;
                break;
            case Hand.SCISSORS:
                cpuHand = Hand.ROCK;
                break;
            case Hand.PAPER:
                cpuHand = Hand.SCISSORS;
                break;
            }
        }

        return cpuHand.name();
    }

    /**
     * クライアントにJSONレスポンスを返すためのメソッド
     */
    private void putResponse(HttpServletResponse response, JSONObject jsonResponse) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        response.getOutputStream().print(jsonResponse.toString());
        response.getOutputStream().flush();

    }

}
