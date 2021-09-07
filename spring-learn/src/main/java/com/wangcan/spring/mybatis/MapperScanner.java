package com.wangcan.spring.mybatis;

import com.wangcan.spring.mybatis.mapper.OrderMapper;
import com.wangcan.spring.mybatis.mapper.UserMapper;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author wangcan
 * @date 2021/9/7 8:59
 */
public class MapperScanner extends ClassPathBeanDefinitionScanner {

    public MapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> holders= super.doScan(basePackages);

        for(BeanDefinitionHolder beanDefinitionHolder:holders){
            BeanDefinition beanDefinition=  beanDefinitionHolder.getBeanDefinition();
            // 改成工厂类
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClassName(MapperFactoryBean.class.getName());
        }

        return holders;
    }
}
