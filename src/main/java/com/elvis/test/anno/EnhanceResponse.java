package com.elvis.test.anno;

import com.elvis.test.enums.AnnoEnum;

import java.lang.annotation.*;

/**
 * @author : Elvis
 * @since : 2023/4/12 11:52
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnhanceResponse {
    AnnoEnum[] value() default {};
}
