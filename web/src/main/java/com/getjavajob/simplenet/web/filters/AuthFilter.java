package com.getjavajob.simplenet.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private Set<String> allowedPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedPath = new HashSet<>();
        allowedPath.add("/jsp/login.jsp");
        allowedPath.add("/jsp/registration.jsp");
        allowedPath.add("/regcheck");
        allowedPath.add("/jsp/regerror.jsp");
        allowedPath.add("/jsp/regaccept.jsp");
        allowedPath.add("/logout");
        allowedPath.add("/jsp/loginerror.jsp");
        allowedPath.add("/logincheck");
        allowedPath.add("/js/script.js");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String requestPath = req.getRequestURI();
        String loginURI = "/jsp/login.jsp";
        boolean loggedIn = session != null && session.getAttribute("userId") != null;
        boolean allowedRequest = allowedPath.contains(requestPath);
        if (loggedIn || allowedRequest) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }
}
