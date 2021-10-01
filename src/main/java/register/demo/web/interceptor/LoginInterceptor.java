package register.demo.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import register.demo.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_STUDENT) == null) {
            response.sendRedirect("/login?redirectURL="+request.getRequestURI());
            return false;
        }

        return true;
    }
}
