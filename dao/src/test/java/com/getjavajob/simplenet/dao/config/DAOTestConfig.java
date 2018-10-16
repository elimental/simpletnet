package com.getjavajob.simplenet.dao.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = {"classpath:db.properties"})
public class DAOTestConfig {
    @Autowired
    Environment environment;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("database.driverclass"));
        dataSource.setUrl(environment.getProperty("database.url"));
        dataSource.setUsername(environment.getProperty("database.user"));
        dataSource.setPassword(environment.getProperty("database.password"));
        return dataSource;
    }

    @Bean
    @Autowired
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
