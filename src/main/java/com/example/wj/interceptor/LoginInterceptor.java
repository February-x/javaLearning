package com.example.wj.interceptor;

import com.example.wj.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object obj) throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        String contextPath = httpSession.getServletContext().getContextPath();
        String[] requireAuthPages = new String[] {
                "index",
        };
        String uri = httpServletRequest.getRequestURI();
        uri = StringUtils.remove(uri, contextPath + "/");
        if (beginWith(uri, requireAuthPages)) {
            User user = (User) httpSession.getAttribute("user");
            if (user == null) {
                httpServletResponse.sendRedirect("login");
                return false;
            }
        }
        return true;
    }

    private boolean beginWith(String page, String[] requireAuthPages) {
        for (String requireAuthPage : requireAuthPages) {
            if (StringUtils.startsWith(page, requireAuthPage)) {
                return true;
            }
        }
        return false;
    }
}
