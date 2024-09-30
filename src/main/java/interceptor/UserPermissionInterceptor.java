package interceptor;

import context.UserContext;
import context.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static basejpa.util.DSUtil.EmptyTool.isEmpty;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/23 15:50
 */
@Component
public class UserPermissionInterceptor implements AsyncHandlerInterceptor {
    @Autowired
    private PathMatcher pathMatcher;
    @Autowired
    private AuthWhiteListConfig authWhiteListConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //静态资源直接释放通过
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        String requestPath = request.getServletPath();
        //判断是否拥有权限:1.是否是白名单；2.是否是超级角色；3.是否拥有权限
        //1.是否是白名单
        if (this.isMatchCurrentRequestPath(authWhiteListConfig.getWhiteList(), requestPath)) {
            return true;
        }
        Optional<UserContext> optional = UserContextHolder.getUser();
        if (!optional.isPresent()) {
            //未登录，未获取到当前用户信息

        }
        //当前用户信息
        UserContext userContext = optional.get();
        //2.是否是超级角色
        if (userContext.isSuperAdmin()) {
            return true;
        }
        //3.是否拥有权限
        if (!this.isMatchCurrentRequestPath(userContext.getPermissions(), requestPath)) {
            //没有权限，抛异常

        }
        return true;
    }

    private boolean isMatchCurrentRequestPath(List<String> permissionList, String requestPath) {
        if (isEmpty(permissionList)) {
            return false;
        }
        for (String permission : permissionList) {
            if (pathMatcher.match(permission, requestPath)) {
                return true;
            }
        }
        return false;
    }
}