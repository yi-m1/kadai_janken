package jp.co.sfrontier.ss3.janken_game.service.janken.value;

import java.io.Serializable;

import jp.co.sfrontier.ss3.janken_game.common.Hand;

/**
 * プレイヤーの情報を格納するクラス
 */
public class Player implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /** ユーザーID */
    private Long userId;
    
    /** じゃんけんの手 */
    private Hand hand;

    public Player(Long userId, Hand hand) {
        super();
        this.userId = userId;
        this.hand = hand;
    }

    public Long getUserId() {
        return userId;
    }

    public Hand getHand() {
        return hand;
    }


    
}
