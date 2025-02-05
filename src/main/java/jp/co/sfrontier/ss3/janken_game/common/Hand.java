package jp.co.sfrontier.ss3.janken_game.common;

/**
 * じゃんけんの手を定義する
 */
public enum Hand {
    /** グー */
    ROCK(1),

    /** チョキ */
    SCISSORS(2),

    /** パー */
    PAPER(3);

    private int value;

    private Hand(int value) {
        this.value = value;
    }

    public static Hand get(String hand) {
        if(hand==null) {
            return null;
        }
        
        if(ROCK.name().equalsIgnoreCase(hand)) {
            return ROCK;
        }
        
        if(SCISSORS.name().equalsIgnoreCase(hand)) {
            return SCISSORS;
        }
        
        if(PAPER.name().equalsIgnoreCase(hand)) {
            return PAPER;
        }
        
        return null;
    }
    
    public int compair(Hand hand) {
        int result = 0;
        if (this.value == 1) {
            switch (hand) {
            case ROCK:
                result = 0;
                break;
            case SCISSORS:
                result = 1;
                break;
            case PAPER:
                result = -1;
                break;
            }
        } else if (this.value == 2) {
            switch (hand) {
            case ROCK:
                result = -1;
                break;
            case SCISSORS:
                result = 0;
                break;
            case PAPER:
                result = 1;
                break;
            }
        } else {
            switch (hand) {
            case ROCK:
                result = 1;
                break;
            case SCISSORS:
                result = -1;
                break;
            case PAPER:
                result = 0;
                break;
            }
        }
        return result;

    }
}
