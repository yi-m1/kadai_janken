package com.kadai.aws.controller;

import javax.servlet.http.HttpServletRequest;

import com.kadai.aws.model.UserInfo;

public class ServletUtils {
    public static final String KEY_LOGIN_USERINFO = "userInfo";
    
    public static UserInfo getUserInfo(HttpServletRequest request) {
        return (UserInfo) request.getSession().getAttribute(KEY_LOGIN_USERINFO);
    }
    public static void setUserInfo(HttpServletRequest request, UserInfo userInfo) {
        request.getSession().setAttribute(KEY_LOGIN_USERINFO, userInfo);
    }
}
