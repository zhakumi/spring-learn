package com.wang.test;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 17:21
 */
public class CodeGenerator {

        /**
         * <p>
         * 读取控制台内容
         * </p>
         */
        public static String scanner(String tip) {
            Scanner scanner = new Scanner(System.in);
            StringBuilder help = new StringBuilder();
            help.append("请输入" + tip + "：");
            System.out.println(help.toString());
            if (scanner.hasNext()) {
                String ipt = scanner.next();
                if (StringUtils.isNotEmpty(ipt)) {
                    return ipt;
                }
            }
            throw new MybatisPlusException("请输入正确的" + tip + "！");
        }

        public static void main(String[] args) {
            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();

            // 全局策略配置
            GlobalConfig gc = new GlobalConfig();
            String projectPath = System.getProperty("user.dir");
            gc.setOutputDir(projectPath + "/src/main/java");
            gc.setFileOverride(true);
            gc.setAuthor("wangcan");
            gc.setOpen(false);
            mpg.setGlobalConfig(gc);

            // 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8");
            dsc.setDriverName("com.mysql.jdbc.Driver");
            dsc.setUsername("root");
            dsc.setPassword("123456");
            mpg.setDataSource(dsc);

            // 包配置 指定生成代码的包路径
            PackageConfig packageConfig = new PackageConfig();
            packageConfig.setModuleName(scanner("模块名"));
            packageConfig.setParent("com.wang.test");
            packageConfig.setMapper("com.wang.mapper");
            packageConfig.setController("com.wang.controller");
            mpg.setPackageInfo(packageConfig);

            // 自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    // to do nothing
                }
            };
            // 如果模板引擎是 velocity
             String templatePath = "/templates/mapper.xml.vm";

            // 自定义输出配置
            List<FileOutConfig> focList = new ArrayList<>();
            // 自定义配置会被优先输出
            focList.add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名
                    return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                            + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });

            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);

            // 配置模板 可自定义代码生成的模板，实现个性化操作
            TemplateConfig templateConfig = new TemplateConfig();

            // 配置自定义输出模板
            // templateConfig.setEntity();
            // templateConfig.setService();
            // templateConfig.setController();

            templateConfig.setXml(null);
            mpg.setTemplate(templateConfig);

            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
            mpg.execute();
        }

    }
