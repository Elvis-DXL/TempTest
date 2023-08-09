package com.elvis.test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author : Elvis
 * @since : 2023/3/29 9:53
 */
@Aspect
@Component
public class ElvisAspect {

    @Pointcut("")
    public void elvisPointCut() {
    }

    @Around("")
    public Object around(ProceedingJoinPoint point) throws Throwable {




        Object result = point.proceed();

        return result;
    }
}
