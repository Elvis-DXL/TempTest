package interceptor;

import context.UserContext;
import context.UserContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static basejpa.util.DSUtil.EmptyTool.isNotEmpty;
import static basejpa.util.DSUtil.trueDo;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/23 15:45
 */
@Component
public class TokenPreHandlerInterceptor implements AsyncHandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取Token
        String token = request.getHeader("token");
        trueDo(isNotEmpty(token), token, it -> {
            //通过Token，获取用户相关信息，存入上下文容器中
            UserContext userContext = new UserContext();


            UserContextHolder.setUser(userContext);
        });
        return true;
    }
}