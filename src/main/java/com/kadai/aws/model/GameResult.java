package com.kadai.aws.model;

public class GameResult {
    private int userId;
    private String userChoice;
    private String aiChoice;
    private String resultMessage;

    // コンストラクタ
    public GameResult(int userId, String userChoice, String aiChoice, String resultMessage) {
        this.userId = userId;
        this.userChoice = userChoice;
        this.aiChoice = aiChoice;
        this.resultMessage = resultMessage;
    }

    // ゲッターとセッター
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(String userChoice) {
        this.userChoice = userChoice;
    }

    public String getAiChoice() {
        return aiChoice;
    }

    public void setAiChoice(String aiChoice) {
        this.aiChoice = aiChoice;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
