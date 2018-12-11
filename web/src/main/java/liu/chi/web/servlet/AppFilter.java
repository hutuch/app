package liu.chi.web.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * /*: 拦截所有目录
 * /**:无效
 * 会拦截 spring mvc的controller
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class AppFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FortuneFilter 初始化。。。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("进入FortuneFilter..name= " + request.getParameter("name"));
        response.setCharacterEncoding("utf-8");
        chain.doFilter(request, response);
        System.out.println("离开FortuneFilter。。。");

    }

    @Override
    public void destroy() {
        System.out.println("FortuneFilter 销毁。。。");
    }
}
