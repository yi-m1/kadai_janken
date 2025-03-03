package value;

import java.io.Serializable;

import common.Hand;

/**
 * プレイヤーの情報を格納するクラス
 */
public class Player implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /** ユーザーID */
    private int userId;
    
    /** じゃんけんの手 */
    private Hand hand;

    public Player(int i, Hand hand) {
        super();
        this.userId = i;
        this.hand = hand;
    }

    public int getUserId() {
        return userId;
    }

    public Hand getHand() {
        return hand;
    }


    
}
