package com.alvarohdezarroyo.lookmomicanfly.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitorInfo {

    boolean logAccess() default false;

    int priority() default 0;

}
