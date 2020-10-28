package com.wangcan.spring.learn;

import java.util.Arrays;
import org.springframework.core.SimpleAliasRegistry;

/**
 * @Author: wangcan
 * @Date: 2020/10/28 10:54 别名测试
 */
public class AliasTestMain {

    public static void main(String[] args) {
        SimpleAliasRegistry aliasRegistry = new SimpleAliasRegistry();
        aliasRegistry.registerAlias("B","A");
        aliasRegistry.registerAlias("A","C");
        aliasRegistry.registerAlias("C","D");
        System.out.println("B的别名:"+Arrays.toString(aliasRegistry.getAliases("B")));
        System.out.println("A的别名:"+Arrays.toString(aliasRegistry.getAliases("A")));
        System.out.println("C的别名:"+Arrays.toString(aliasRegistry.getAliases("C")));
    }
}
