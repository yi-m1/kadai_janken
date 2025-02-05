package jp.co.sfrontier.ss3.janken_game.controller.game;

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

import jp.co.sfrontier.ss3.janken_game.common.Hand;
import jp.co.sfrontier.ss3.janken_game.controller.common.ServletUtils;
import jp.co.sfrontier.ss3.janken_game.service.janken.JankenService;
import jp.co.sfrontier.ss3.janken_game.service.janken.value.Player;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Game Play");

        UserInfo userInfo = ServletUtils.getUserInfo(request);

        String playerHand = request.getParameter("hand");

        Hand hand = Hand.get(playerHand);

        // 不正な入力の場合エラーを返す
        if (hand == null) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "ERROR");
            putResponse(response, jsonResponse);
            return;
        }

        JankenService jankenService = new JankenService();

        Player player = new Player(userInfo.getUserId(), hand);

        try {
            int result = jankenService.fight(player);

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

    private void putResponse(HttpServletResponse response, JSONObject jsonResponse) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        response.getOutputStream().print(jsonResponse.toString());
        response.getOutputStream().flush();

    }
}
