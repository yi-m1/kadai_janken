package jp.co.sfrontier.ss3.janken_game.controller.util;

import javax.servlet.http.HttpServletRequest;

import jp.co.sfrontier.ss3.janken_game.model.UserInfo;

public class ServletUtils {
    public static final String KEY_LOGIN_USERINFO = "userInfo";
    
    public static UserInfo getUserInfo(HttpServletRequest request) {
        return (UserInfo) request.getSession().getAttribute(KEY_LOGIN_USERINFO);
    }
    public static void setUserInfo(HttpServletRequest request, UserInfo userInfo) {
        request.getSession().setAttribute(KEY_LOGIN_USERINFO, userInfo);
    }
}
