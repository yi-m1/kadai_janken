package com.kadai.aws.model;

public class GameResult {
    private final String userChoice;
    private final String aiChoice;
    private final String resultMessage;

    // コンストラクタ
    public GameResult(String userChoice, String aiChoice, String resultMessage) {
        this.userChoice = userChoice;
        this.aiChoice = aiChoice;
        this.resultMessage = resultMessage;
    }

    // ゲッター
    public String getUserChoice() {
        return userChoice;
    }

    public String getAiChoice() {
        return aiChoice;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
