package com.king.config.data;

import com.king.annotation.Develop;
import com.king.annotation.Production;
import com.king.domain.DataBaseProperties;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:database-config.properties")
public class DataSourceConfig {

    //    数据库参数
    private final DataBaseProperties dbProps;
    //    注入数据库参数
    @Autowired
    public DataSourceConfig(DataBaseProperties dbProps) {
        this.dbProps = dbProps;
    }

    //开发用数据源
    @Bean(name = "dataSource")
//    @Develop
    public DataSource getDevDataSource(){
        Properties properties=getProperties();
        DataSource dataSource=null;
        try {
            dataSource= BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    //jndi数据源，需要在tomcat中设置相关数据源
    @Bean
    @Production //自定义profile annotation
    public JndiObjectFactoryBean dataSource(){
        JndiObjectFactoryBean jndiObjectFactoryBean=new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:comp/env/jdbc/EmployeeDB");
        jndiObjectFactoryBean.setProxyInterface(DataSource.class);
        return jndiObjectFactoryBean;
    }

    //    driverManager数据源，需要设置mysql驱动
    @Bean("driverManagerDataSource")
    @Profile("notUsed")
    public DataSource getDriverManagerDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbProps.getDriver());
        dataSource.setUrl(dbProps.getUrl());
        dataSource.setUsername(dbProps.getUsername());
        dataSource.setPassword(dbProps.getPassword());
        return dataSource;
    }

    //设置BasicDataSource的各个属性，注意property的名称不要弄错
    private Properties getProperties() {
        Properties properties=new Properties();
        properties.setProperty("maxIdle",dbProps.getBasicDataSourceMaxIdle());
        properties.setProperty("maxWaitMillis",dbProps.getBdcMaxWaitMills());
        properties.setProperty("maxTotal", dbProps.getBasicDataSourceMaxTotal());
        properties.setProperty("driverClassName",dbProps.getDriver());
        properties.setProperty("url",dbProps.getUrl());
        properties.setProperty("username", dbProps.getUsername());
        properties.setProperty("password",dbProps.getPassword());
        return properties;
    }

}
