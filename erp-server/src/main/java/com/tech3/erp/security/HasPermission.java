package com.tech3.erp.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPermission {
    String value(); // The sub-menu path or identifier
    String action() default "VIEW"; // VIEW, ADD, EDIT, DELETE, etc.
}
