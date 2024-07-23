package interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/23 15:42
 */
@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenPreHandlerInterceptor()).addPathPatterns("/**").order(1);
        registry.addInterceptor(new UserPermissionInterceptor()).addPathPatterns("/**").order(2);
    }
}