package com.king.config.data;

import org.apache.ibatis.session.SqlSessionFactory;
import com.king.repository.EmployeeRepository;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory( DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }


    //单独的获取一个Mapper
    //需要设置一个SqlSessionFactory
    //以及一个Mapper接口
    @Bean
    public MapperFactoryBean<EmployeeRepository> employeeMapper(SqlSessionFactory sqlSessionFactory){
        MapperFactoryBean<EmployeeRepository> mapperFactoryBean=new MapperFactoryBean<>();
        mapperFactoryBean.setMapperInterface(EmployeeRepository.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        mapperFactoryBean.afterPropertiesSet();
        return mapperFactoryBean;
    }

    //通过扫描的方式设置mapper
    //需要设置一个SqlSessionFactory
    //一个扫描的基础包
    //一个过滤方式
    @Bean
    @Primary
    public MapperScannerConfigurer mapperScanner(){
        MapperScannerConfigurer scannerConfigurer=new MapperScannerConfigurer();
        scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        scannerConfigurer.setAnnotationClass(Repository.class);
        scannerConfigurer.setBasePackage("com.king.repository");
        return scannerConfigurer;
    }


}
