/**
 * 
 */
package jp.co.sfrontier.ss3.janken_game.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.sfrontier.ss3.janken_game.entity.ResultHistoryTbl;

/**
 * 
 */
public class ResultHistoryTblDao {

    private static final Logger logger = LogManager.getLogger(ResultHistoryTblDao.class);

    private Connection connection;

    public ResultHistoryTblDao(Connection connection) {
        this.connection = connection;

    }

    public void insert(ResultHistoryTbl record) throws SQLException {

        String sql = "INSERT INTO result_history_tbl (user_id,execute_datetime,opponent,result,user_choice,create_datetime,update_datetime,version) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = connection.prepareStatement(sql);

        //値を埋め込む
        int idx = 0;

        pstmt.setLong(++idx, record.getUserId());
        pstmt.setTimestamp(++idx, new Timestamp(record.getExecuteDatetime().getTime()));
        pstmt.setLong(++idx, record.getOpponent());
        pstmt.setString(++idx, record.getResult());
        pstmt.setString(++idx, record.getUserChoice());
        pstmt.setTimestamp(++idx, new Timestamp(record.getCreateDatetime().getTime()));
        pstmt.setTimestamp(++idx, new Timestamp(record.getUpdateDatetime().getTime()));
        pstmt.setInt(++idx, record.getVersion());

        pstmt.execute();

    }
    
}
