package com.auth.starter.annotation;

/**
 * 权限控制器
 */
public enum CollectionType {
    /** 交集，即满足所有才能通过 */
    AND,

    /** 并集，即满足任意一个就能通过 */
    OR
}
