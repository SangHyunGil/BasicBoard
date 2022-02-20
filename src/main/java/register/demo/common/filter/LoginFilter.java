package register.demo.common.filter;

import org.springframework.util.PatternMatchUtils;
import register.demo.common.session.SessionConst;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    private static final String[] whitelist = {"/", "/login", "/register", "/css/*", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (isRequireLogin(httpServletRequest.getRequestURI()) && isLogin(httpServletRequest.getSession())) {
            httpServletResponse.sendRedirect("/login?redirectURL="+((HttpServletRequest) request).getRequestURI());
            return;
        }
        chain.doFilter(request, response);
    }

    public Boolean isRequireLogin(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    public Boolean isLogin(HttpSession session) {
        if (session == null || session.getAttribute(SessionConst.LOGIN_STUDENT) == null) {
            return true;
        }
        return false;
    }
}
