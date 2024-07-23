package interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/23 15:42
 */
@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private UserPermissionInterceptor userPermissionInterceptor;
    @Autowired
    private TokenPreHandlerInterceptor tokenPreHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenPreHandlerInterceptor).addPathPatterns("/**").order(1);
        registry.addInterceptor(userPermissionInterceptor).addPathPatterns("/**").order(2);
    }
}