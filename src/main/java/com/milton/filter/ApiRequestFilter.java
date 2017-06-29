package com.milton.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author milton.zhang
 * @description api请求过滤器，对主要校验token
 * @date 2017-06-05
 */
@Order(2)
@WebFilter(filterName = "apiRequestFilter", urlPatterns = "/api/web/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源
        }
)
public class ApiRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        if (url.indexOf("login") > -1) {
            //登录接口、公司列表接口、渠道无需登录访问
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            /*
            //路径/api/web/（前端页面访问接口）需要校验接口登录权限
            boolean flag = false;//false 无权限，true 有权限
            String token = request.getParameter("token");
            if (!StringUtils.isEmpty(token)) {
                Object obj = request.getSession(true).getAttribute(token);
                if (obj != null) {
                    //session中有改token，则表示已经登录过
                    flag = true;
                }
            }

            if (flag) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                //无权限
                CommonResponse res = new CommonResponse();
                res.setCode(ResultCode.ERR_NO_LOGIN.getCode());
                res.setMsg(ResultCode.ERR_NO_LOGIN.getDesc());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.write(JSON.toJSONString(res));
                out.flush();
            }
            */
        }
    }

    @Override
    public void destroy() {

    }
}
