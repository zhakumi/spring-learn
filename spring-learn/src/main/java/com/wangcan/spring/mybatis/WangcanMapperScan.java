package com.wangcan.spring.mybatis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author wangcan
 * @date 2021/9/8 9:04
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MapperBeanDefinitionRegistry.class)
public @interface WangcanMapperScan {

    String value();
}
