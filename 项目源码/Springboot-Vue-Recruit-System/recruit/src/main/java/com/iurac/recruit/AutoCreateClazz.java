package com.iurac.recruit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import javax.sql.DataSource;

public class AutoCreateClazz {

    public static void main(String[] args) {
        //创建自动生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        //设置全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //设置生成路径
        String path = System.getProperty("user.dir");
        globalConfig.setOutputDir(path+"/src/main/java");
        globalConfig.setAuthor("iurac");
        //设置命名规则
        globalConfig.setMapperName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        //设置主键类型
        globalConfig.setIdType(IdType.INPUT);

        //设置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("12345678");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/recruitment?serverTimezone=UTC");

        //设置package信息
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.iurac");
        packageConfig.setModuleName("recruit");

        //设置策略
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix("t_");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);

        //执行
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.execute();

    }
}
