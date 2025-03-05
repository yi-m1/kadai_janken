package jp.co.sfrontier.ss3.janken_game.service.janken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.Hand;
import entity.ResultHistoryTbl;
import jp.co.sfrontier.ss3.janken_game.repository.DbUtil;
import jp.co.sfrontier.ss3.janken_game.repository.ResultHistoryTblDao;
import value.Player;

/**
 * じゃんけんゲームを提供するためのサービスクラス
 */
public class JankenServiceTomari {

    private static final Logger logger = LogManager.getLogger(JankenServiceTomari.class);

    public static final int CPU_ID = 1;

    public int fight(Player player) throws SQLException {
        Player cpu = new Player(CPU_ID, createHand());

        return fight(player, cpu);
    }

    /**
     * じゃんけんの対戦を行い、その結果をデータベースに保存するメソッド
     * @throws SQLException 
     * 
     */
    public int fight(Player player1, Player player2) throws SQLException {

        // じゃんけんの結果を求める
        int result = player1.getHand().compair(player2.getHand());

        // じゃんけんの結果を保存する
        Date now = new Date();

        Connection connection = DbUtil.getConnection();
        logger.debug("connection get");
        ResultHistoryTblDao dao = new ResultHistoryTblDao(connection);
        logger.debug("tblDao new");
        try {
            dao.insert(createRecord(player1, player2.getUserId(), result, now));

            dao.insert(createRecord(player2, player1.getUserId(), result * (-1), now));

            logger.debug("connection commit");
            DbUtil.commit(connection);
            
        } catch (SQLException e) {

            DbUtil.rollback(connection);

        } finally {

            DbUtil.close(connection);
            logger.debug("connection close");
        }

        return result;
    }

    /**
     * プレイヤーの対戦履歴を保存するためのレコードを作成
     */
    private ResultHistoryTbl createRecord(Player player, int i, int result, Date targetDate) {

        ResultHistoryTbl entity = new ResultHistoryTbl();
        entity.setUserId(player.getUserId());
        entity.setExecuteDatetime(targetDate);
        entity.setCreateDatetime(targetDate);
        entity.setUpdateDatetime(targetDate);
        entity.setOpponent(i);
        entity.setResult(getResultText(result));
        entity.setUserChoice(player.getHand().name());
        entity.setVersion(Integer.valueOf(1));

        return entity;
    }

    private Hand createHand() {
        // ランダムな整数を生成するために Random クラスを使う
        Random random = new Random();

        // 0, 1, 2 のいずれかをランダムに選ぶ
        int randomNumber = random.nextInt(3); // 0 から 2 の範囲でランダムな数を生成

        // ランダムな数に基づいて Hand を返す
        switch (randomNumber) {
        case 0:
            return Hand.ROCK;
        case 1:
            return Hand.SCISSORS;
        case 2:
            return Hand.PAPER;
        default:
            throw new IllegalStateException("Unexpected value: " + randomNumber);
        }
    }

    private String getResultText(int result) {
        if (result >= 1) {
            return "WIN";
        } else if (result == 0) {
            return "DROW";
        } else {
            return "LOSE";
        }
    }
}
