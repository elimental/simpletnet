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
        allowedPath.add("/login");
        allowedPath.add("/loginCheck");
        allowedPath.add("/loginError");
        allowedPath.add("/registration");
        allowedPath.add("/regError");
        allowedPath.add("/regAccept");
        allowedPath.add("/regCheck");
        allowedPath.add("/logout");
        allowedPath.add("/js/script.js");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String requestPath = req.getRequestURI();
        String loginURI = "/login";
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
