package project.base.enhance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.Servlet;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/6/7 10:51
 */
@Slf4j
@Configuration
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice
public class EnhanceRequestBodyAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        //判断是否要执行本类方法,true为执行,false不执行.通过该方法可以选择哪些类或那些方法要进行处理,其他的不进行处理.

        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        //body读取前进行处理

        return null;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        //body读取后进行处理

        return null;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                  Class<? extends HttpMessageConverter<?>> converterType) {
        //body为空时处理

        return null;
    }
}
