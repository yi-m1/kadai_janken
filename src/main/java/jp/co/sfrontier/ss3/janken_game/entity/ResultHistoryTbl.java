package jp.co.sfrontier.ss3.janken_game.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * じゃんけん結果履歴テーブルのエンティティクラス
 */
public class ResultHistoryTbl implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long historyId;
    
    private int userId;
    
    private Date executeDatetime;
    
    private int opponent;
    
    private String result;
    
    private String userChoice;
    
    private Date createDatetime;
    
    private Date updateDatetime;
    
    private Integer version;

    /**
     * @return historyId
     */
    public Long getHistoryId() {
        return historyId;
    }

    /**
     * @param historyId セットする historyId
     */
    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    /**
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param i セットする userId
     */
    public void setUserId(int i) {
        this.userId = i;
    }

    /**
     * @return executeDatetime
     */
    public Date getExecuteDatetime() {
        return executeDatetime;
    }

    /**
     * @param executeDatetime セットする executeDatetime
     */
    public void setExecuteDatetime(Date executeDatetime) {
        this.executeDatetime = executeDatetime;
    }

    /**
     * @return opponent
     */
    public int getOpponent() {
        return opponent;
    }

    /**
     * @param i セットする opponent
     */
    public void setOpponent(int i) {
        this.opponent = i;
    }

    /**
     * @return result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result セットする result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return userChoice
     */
    public String getUserChoice() {
        return userChoice;
    }

    /**
     * @param userChoice セットする userChoice
     */
    public void setUserChoice(String userChoice) {
        this.userChoice = userChoice;
    }

    /**
     * @return createDatetime
     */
    public Date getCreateDatetime() {
        return createDatetime;
    }

    /**
     * @param createDatetime セットする createDatetime
     */
    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * @return updateDatetime
     */
    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    /**
     * @param updateDatetime セットする updateDatetime
     */
    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    /**
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version セットする version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
}
