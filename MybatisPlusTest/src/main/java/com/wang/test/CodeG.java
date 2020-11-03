package com.wang.test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ResourceBundle;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 17:46
 */
public class CodeG {

    public static void main(String[] args) {
        //用来获取Mybatis-Plus.properties文件的配置信息
        ResourceBundle rb = ResourceBundle.getBundle("code-generator.yml");
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(rb.getString("outputDir"));
        gc.setFileOverride(true);
        gc.setAuthor(rb.getString("author"));
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("passWord"));
        dsc.setUrl(rb.getString("url"));
        mpg.setDataSource(dsc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
       //strategy.setTablePrefix(new String[] { "bmd_", "mp_" });
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(new String[] { rb.getString("tableName")});
        // 字段名生成策略
        // strategy.setFieldNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperServiceImplClass("com.baomidou.springwind.service.support.BaseServiceImpl");
        mpg.setStrategy(strategy);


        // 包配置
        PackageConfig pc = new PackageConfig();
        // 自定义包路径
        pc.setParent(rb.getString("parent"));
        // 这里是控制器包名，默认 web
        pc.setController("controller."+rb.getString("className"));
        pc.setEntity("model."+rb.getString("className"));
        pc.setMapper("dao."+rb.getString("className"));
        pc.setXml("mapping."+rb.getString("className"));
        pc.setService("service."+rb.getString("className"));
        pc.setServiceImpl("service."+rb.getString("className")+".impl");
        mpg.setPackageInfo(pc);
// 执行生成
        mpg.execute();
    }

}
