package jp.co.sfrontier.ss3.janken_game.model;

public enum JankenChoice {
    ROCK("rock"),
    SCISSORS("scissors"),
    PAPER("paper");

    private final String choice;

    JankenChoice(String choice) {
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }

    public static JankenChoice fromString(String choice) {
        for (JankenChoice jankenChoice : JankenChoice.values()) {
            if (jankenChoice.getChoice().equals(choice)) {
                return jankenChoice;
            }
        }
        throw new IllegalArgumentException("Invalid choice: " + choice);
    }
}
