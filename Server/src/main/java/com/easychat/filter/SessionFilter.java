package com.easychat.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by xhans on 2015/12/16.
 */
/*
    对session进行检查，两种情况可以继续请求：
    1.如果session已经存在id字段，说明已经登录
    2.求注册登录接口。
    其他情况返回403
 */
@Component
public class SessionFilter extends OncePerRequestFilter {
    static Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        logger.debug(request.getSession().getId());
        if (httpSession.getAttribute("id") != null | isPass(request)){
            filterChain.doFilter(request,response);
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    /*
    判断是否可以通过
     */
    private boolean isPass(HttpServletRequest httpRequest){
        String uri = httpRequest.getRequestURI();
        logger.debug(uri);
        if (uri.endsWith("authorization")) {
            return true;
        }
        if (uri.endsWith("users")){
            return true;
        }
        if (httpRequest.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(httpRequest.getMethod())) {
            return true;
        }
        return false;
    }

}
