package com.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author monetto
 */
public class RequestUtil {

    public static String getSessionByName(HttpServletRequest httpServletRequest, String sessionName) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (httpSession == null) {
            return null;
        }
        return httpSession.getAttribute(sessionName).toString();
    }

    public static String getCookieByName(HttpServletRequest httpServletRequest, String cookieName) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie != null && "token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
