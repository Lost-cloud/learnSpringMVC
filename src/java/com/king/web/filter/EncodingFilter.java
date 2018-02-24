package com.king.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private static final String CHARACTER_ENCODING = "utf-8";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(CHARACTER_ENCODING);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) {
    }

}
