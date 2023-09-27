package com.elvis.test.anno;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FontTransMethod {
    String method() default "";
}
