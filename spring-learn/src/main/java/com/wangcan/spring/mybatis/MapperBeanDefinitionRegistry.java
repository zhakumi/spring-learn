package com.wangcan.spring.mybatis;

import com.wangcan.spring.mybatis.mapper.OrderMapper;
import com.wangcan.spring.mybatis.mapper.UserMapper;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * @author wangcan
 * @date 2021/9/7 8:51 maperr beanDefinitrion 注册类
 */

public class MapperBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
        BeanDefinitionRegistry registry) {

        // 读取wangcanMapperScan 注解上的value值 当成路径
        Map<String, Object> annotations = annotationMetadata.getAnnotationAttributes(
            WangcanMapperScan.class.getName());
        String path = (String) annotations.get("value");

        MapperScanner mapperScanner = new MapperScanner(registry);
        mapperScanner.addIncludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader,
                MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });
        mapperScanner.scan(path);
        // 注册userMapper 移动到mapperScanner
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition()
//            .getBeanDefinition();
//        beanDefinition.setBeanClass(MapperFactoryBean.class);
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
//        registry.registerBeanDefinition("userMapper", beanDefinition);
//
//        // 注册orderMapper
//        AbstractBeanDefinition orderDefinition = BeanDefinitionBuilder.genericBeanDefinition()
//            .getBeanDefinition();
//        orderDefinition.setBeanClass(MapperFactoryBean.class);
//        orderDefinition.getConstructorArgumentValues().addGenericArgumentValue(OrderMapper.class);
//        registry.registerBeanDefinition("orderMapper", orderDefinition);

    }
}
