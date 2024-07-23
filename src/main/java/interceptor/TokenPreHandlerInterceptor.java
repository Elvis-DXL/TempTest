package interceptor;

import context.UserContextHolder;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/23 15:45
 */
public class TokenPreHandlerInterceptor implements AsyncHandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        return true;
    }
}