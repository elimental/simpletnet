package com.getjavajob.simplenet.web.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class SecurityInterceptor implements HandlerInterceptor {

    private static final long SESSION_TIMEOUT = 10 * 60 * 1000;

    private Set<String> allowedPath;

    public SecurityInterceptor() {
        allowedPath = new HashSet<>();
        allowedPath.add("/");
        allowedPath.add("/login");
        allowedPath.add("/checkLogin");
        allowedPath.add("/registration");
        allowedPath.add("/checkRegistration");
        allowedPath.add("/logout");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        String requestPath = request.getRequestURI();
        String loginURI = "/login";
        boolean loggedIn = session != null && session.getAttribute("userId") != null;
        boolean allowedRequest = allowedPath.contains(requestPath) || requestPath.contains("/js/") ||
                requestPath.contains("/images/") || requestPath.contains("/css/");
        if (loggedIn) {
            Long lastExecutionTime = (Long) session.getAttribute("lastExecutionTime");
            Long currentTime = System.currentTimeMillis();
            if (lastExecutionTime != null && (currentTime - lastExecutionTime) > SESSION_TIMEOUT) {
                response.sendRedirect(loginURI);
                session.invalidate();
                return false;
            } else {
                session.setAttribute("lastExecutionTime", currentTime);
                return true;
            }
        } else if (allowedRequest) {
            return true;
        } else {
            response.sendRedirect(loginURI);
        }
        return false;
    }
}
