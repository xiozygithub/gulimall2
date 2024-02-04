package com.xunqi.gulimall.order.config;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;


/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-06 11:57
 **/

@Configuration
public class MySeataConfig {

    //容器中数据源的配置属性都在这个对象中
    @Autowired
    DataSourceProperties dataSourceProperties;


    //自定义数据源并返回seata代理数据源
    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {

        //自定义数据源
        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(dataSourceProperties.getName())) {
            dataSource.setPoolName(dataSourceProperties.getName());
        }
        //用seata包装数据源
        return new DataSourceProxy(dataSource);
    }

}
