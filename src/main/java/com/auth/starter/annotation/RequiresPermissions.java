package com.auth.starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限认证
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {
    /**
     * 权限
     * @return
     */
    String [] value() default {};

    /**
     * CollectionType.AND表示要具有所有权限，即表示 且 的意思，CollectionType.OR只要具有其中一个权限，表示 或 的意思
     * @return
     */
    CollectionType type() default CollectionType.AND;
}
