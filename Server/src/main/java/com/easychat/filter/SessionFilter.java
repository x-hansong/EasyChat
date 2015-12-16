package com.easychat.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
public class SessionFilter implements Filter {
    static Logger logger = LoggerFactory.getLogger(SessionFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpRequest.getSession();
        logger.debug(httpRequest.getSession().getId());
        if (httpSession.getAttribute("id") != null | isPass(httpRequest)){
            chain.doFilter(request,response);
        }else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
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
        return uri.endsWith("users");
    }

    @Override
    public void destroy() {

    }
}
