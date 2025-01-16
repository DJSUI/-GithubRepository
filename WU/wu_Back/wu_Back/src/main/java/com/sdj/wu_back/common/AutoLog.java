package com.sdj.wu_back.common;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
    String value() default "";
}
