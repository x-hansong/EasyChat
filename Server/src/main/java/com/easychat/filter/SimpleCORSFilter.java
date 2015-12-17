package com.easychat.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xhans on 2015/12/13.
 */
/*
    跨域请求过滤器，添加CORS相关请求头
 */
@Component
public class SimpleCORSFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response=(HttpServletResponse) resp;
        
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept");
        response.setHeader("Access-Control-Expose-Headers","x-auth-token");

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}

}
