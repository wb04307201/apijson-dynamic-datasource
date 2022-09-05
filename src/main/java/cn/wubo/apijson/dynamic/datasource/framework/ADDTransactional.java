package cn.wubo.apijson.dynamic.datasource.framework;

import java.lang.annotation.*;

/**
 * 用于标记事务一致性
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ADDTransactional {
}
