package com.aop;

import com.utils.RequestUtil;
import com.utils.IpUtil;
import com.utils.JsonWebTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HeaderTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null || "".equals(token)) {
            return verifyTokenFromCookie(request, response);
        }
        String ipAddress = IpUtil.getIpAddress(request);
        Claims claims = JsonWebTokenUtil.parseToken(token, ipAddress);
        if (claims == null) {
            return verifyTokenFromCookie(request, response, ipAddress);
        }
        this.setToken(request, response, claims.getAudience(), ipAddress);
        return true;
    }

    private boolean verifyTokenFromCookie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return verifyTokenFromCookie(request, response, IpUtil.getIpAddress(request));
    }

    private boolean verifyTokenFromCookie(HttpServletRequest request, HttpServletResponse response, String ipAddress) throws Exception {
        String token = RequestUtil.getCookieByName(request, "token");
        if (token == null || "".equals(token)) {
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        } else {
            return this.verifyClaims(request, response, token, ipAddress);
        }
    }

    private boolean verifyClaims(HttpServletRequest request, HttpServletResponse response, String token, String ipAddress) throws Exception {
        Claims claims = JsonWebTokenUtil.parseToken(token, ipAddress);
        if (claims == null) {
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        } else {
            this.setToken(request, response, claims.getAudience(), ipAddress);
            return true;
        }
    }

    public String setToken(HttpServletRequest request, HttpServletResponse response, String audience, String ipAddress) {
        request.getSession().setAttribute("uid", audience);
        String token = JsonWebTokenUtil.generateToken(audience, ipAddress);
        response.addHeader("token", token);
        response.addCookie(new Cookie("token", token));
        return token;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}