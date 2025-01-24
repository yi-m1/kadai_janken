package jp.co.sfrontier.ss3.janken_game.controller.game;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

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
        
        String playerHand = request.getParameter("hand");
        String userId = request.getParameter("userId");  // ユーザーIDを取得
        String cpuHand = getCpuHand();
        String resultMessage = getResultMessage(playerHand, cpuHand);
        String winner = getWinner(playerHand, cpuHand);

        // 結果としてJSONを返す
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("cpuHand", cpuHand);
        jsonResponse.put("result", new JSONObject()
            .put("message", resultMessage)
            .put("winner", winner));

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
        
//        // JSP にディスパッチ
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/game/play.jsp");
//        dispatcher.forward(request, response);
        response.getOutputStream().print("{\"result\":\"-1\",\"cpuHand\":\"rock\"}");
        response.getOutputStream().flush();
    }

    private String getCpuHand() {
        // ランダムにCPUの手を選ぶ
        String[] hands = {"rock", "scissors", "paper"};
        Random random = new Random();
        return hands[random.nextInt(hands.length)];
    }

    private String getResultMessage(String playerHand, String cpuHand) {
        if (playerHand.equals(cpuHand)) {
            return "引き分けです";
        } else if (
            (playerHand.equals("rock") && cpuHand.equals("scissors")) ||
            (playerHand.equals("scissors") && cpuHand.equals("paper")) ||
            (playerHand.equals("paper") && cpuHand.equals("rock"))
        ) {
            return "あなたの勝ちです";
        } else {
            return "CPUの勝ちです";
        }
    }

    private String getWinner(String playerHand, String cpuHand) {
        if (playerHand.equals(cpuHand)) {
            return "draw";
        } else if (
            (playerHand.equals("rock") && cpuHand.equals("scissors")) ||
            (playerHand.equals("scissors") && cpuHand.equals("paper")) ||
            (playerHand.equals("paper") && cpuHand.equals("rock"))
        ) {
            return "player";
        } else {
            return "cpu";
        }
    }
    
  }



 

 
