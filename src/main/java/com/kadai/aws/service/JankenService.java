package com.kadai.aws.service;

import com.kadai.aws.model.GameResult;
import com.kadai.aws.model.JankenChoice;

import java.util.Random;

public class JankenService {

    // コンピュータの選択をランダムで決定
    public String getComputerChoice() {
        JankenChoice[] choices = JankenChoice.values();
        Random random = new Random();
        return choices[random.nextInt(choices.length)].toString();
    }

    // ユーザーの選択とコンピュータの選択を比較して結果を返す
    public GameResult playJanken(JankenChoice userChoice) {
        String computerChoice = getComputerChoice();
        String resultMessage = getResult(userChoice.toString(), computerChoice);
        int userId = 1;  // 仮に 0 として設定（適切な方法で取得する）
        return new GameResult(userId, userChoice.toString(), computerChoice, resultMessage);
    }

    // 結果を判定
    public String getResult(String userChoice, String computerChoice) {
        if (userChoice.equals(computerChoice)) {
            return "引き分け";
        }

        if ((userChoice.equals("ROCK") && computerChoice.equals("SCISSORS")) ||
            (userChoice.equals("PAPER") && computerChoice.equals("ROCK")) ||
            (userChoice.equals("SCISSORS") && computerChoice.equals("PAPER"))) {
            return "あなたの勝ち!";
        } else {
            return "コンピュータの勝ち!";
        }
    }
}
