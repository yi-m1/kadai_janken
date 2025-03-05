package jp.co.sfrontier.ss3.janken_game.repository;

import java.util.ArrayList;
import java.util.List;

import jp.co.sfrontier.ss3.janken_game.model.JankenChoice;

public class GameRepository {

    // ゲームの履歴を保存するリスト
    private List<GameRecord> gameRecords = new ArrayList<>();

    // ゲーム結果を保存
    public void saveGameResult(JankenChoice userChoice, JankenChoice computerChoice, String result) {
        GameRecord gameRecord = new GameRecord(userChoice, computerChoice, result);
        gameRecords.add(gameRecord);
    }

    // すべてのゲーム履歴を取得
    public List<GameRecord> getAllGameResults() {
        return gameRecords;
    }

    // ゲーム履歴を表示するための内部クラス
    public static class GameRecord {
        private JankenChoice userChoice;
        private JankenChoice computerChoice;
        private String result;

        public GameRecord(JankenChoice userChoice, JankenChoice computerChoice, String result) {
            this.userChoice = userChoice;
            this.computerChoice = computerChoice;
            this.result = result;
        }

        public JankenChoice getUserChoice() {
            return userChoice;
        }

        public JankenChoice getComputerChoice() {
            return computerChoice;
        }

        public String getResult() {
            return result;
        }

        @Override
        public String toString() {
            return "GameRecord{" +
                    "userChoice=" + userChoice +
                    ", computerChoice=" + computerChoice +
                    ", result='" + result + '\'' +
                    '}';
        }
    }
}
