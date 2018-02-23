package com.king.config.data;

import com.king.config.data.DataSourceConfig;
import com.king.repository.RepositoryScanMarker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan(basePackageClasses = {RepositoryScanMarker.class})
public class RepositoryConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
