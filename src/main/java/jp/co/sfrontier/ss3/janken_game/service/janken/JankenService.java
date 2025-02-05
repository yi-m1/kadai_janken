package jp.co.sfrontier.ss3.janken_game.service.janken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kadai.aws.repository.DbUtil;

import jp.co.sfrontier.ss3.janken_game.common.Hand;
import jp.co.sfrontier.ss3.janken_game.dao.ResultHistoryTblDao;
import jp.co.sfrontier.ss3.janken_game.entity.ResultHistoryTbl;
import jp.co.sfrontier.ss3.janken_game.service.janken.value.Player;

/**
 * じゃんけんゲームを提供するためのサービスクラス
 */
public class JankenService {

    private static final Logger logger = LogManager.getLogger(JankenService.class);

    public static final int CPU_ID = 1;

    public int fight(Player player) throws SQLException {
        Player cpu = new Player(CPU_ID, createHand());

        return fight(player, cpu);
    }

    /**
     * @throws SQLException 
     * 
     */
    public int fight(Player player1, Player player2) throws SQLException {

        // じゃんけんの結果を求める
        int result = player1.getHand().compair(player2.getHand());

        // じゃんけんの結果を保存する
        Date now = new Date();

        Connection connection = DbUtil.getConnection();

        ResultHistoryTblDao dao = new ResultHistoryTblDao(connection);

        try {
            dao.insert(createRecord(player1, player2.getUserId(), result, now));

            dao.insert(createRecord(player2, player1.getUserId(), result * (-1), now));

            DbUtil.commit(connection);
        } catch (SQLException e) {

            DbUtil.rollback(connection);

        } finally {

            DbUtil.close(connection);
        }

        return result;
    }

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
        return Hand.ROCK;
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
