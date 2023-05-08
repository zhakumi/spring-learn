package com.wangcan.spring.learn.scan;

import java.io.IOException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.TypeFilter;

/**
 * @Author: wangcan
 * @Date: 2020/10/29 20:51
 */
public class CustomScanner  extends ClassPathBeanDefinitionScanner {


    public CustomScanner(
        BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void addIncludeFilter(TypeFilter includeFilter) {
        super.addIncludeFilter(includeFilter);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        //如果是非接口，则返回true，会添加到IOC容器中去
        return !beanDefinition.getMetadata().isInterface();
    }
}
