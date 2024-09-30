package interceptor;

import com.zx.core.log.annotation.ZxOperaLog;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static basejpaold.util.DSUtil.trueDo;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/24 9:42
 */
@Component
public class SystemLogHandleInterceptor implements AsyncHandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        trueDo(handler instanceof HandlerMethod, handler, obj -> {
            HandlerMethod method = (HandlerMethod) obj;
            //判断是否拥有需要记录日志的注解，进行日志记录操作
            Optional.ofNullable(method.getMethodAnnotation(ZxOperaLog.class)).ifPresent(anno -> {
                //日志操作

            });
        });
    }
}