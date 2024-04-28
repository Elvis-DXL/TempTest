//package com.elvis.test.projectaop;
//
//import com.elvis.test.anno.EnhanceRequest;
//import com.elvis.test.enums.AnnoEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpInputMessage;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
//
//import javax.servlet.Servlet;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * 请求增强处理器
// *
// * @author : Elvis
// * @since : 2023/4/12 10:37
// */
//@Slf4j
//@Configuration
//@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
//@RestControllerAdvice
//public class EnhanceRequestBodyAdvice implements RequestBodyAdvice {
//
//    private List<AnnoEnum> annoEnums;
//
//    @Override
//    public boolean supports(MethodParameter methodParameter, Type targetType,
//                            Class<? extends HttpMessageConverter<?>> converterType) {
//        EnhanceRequest enhanceRequest = methodParameter.getMethodAnnotation(EnhanceRequest.class);
//        if (null == enhanceRequest || enhanceRequest.value().length == 0) {
//            return false;
//        }
//        annoEnums = new ArrayList<>();
//        annoEnums.addAll(Arrays.asList(enhanceRequest.value()));
//        return true;
//    }
//
//    @Override
//    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
//                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
//        return inputMessage;
//    }
//
//    @Override
//    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
//                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        if (null == annoEnums || annoEnums.size() == 0) {
//            return body;
//        }
//        for (AnnoEnum annoEnum : annoEnums) {
//            switch (annoEnum) {
//                case Desensitization:
//                    break;
//                case ListMerge:
//                    break;
//                case JsonTrans:
//                    break;
//            }
//        }
//        return body;
//    }
//
//    @Override
//    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
//                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return body;
//    }
//}
