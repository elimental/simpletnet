package com.getjavajob.simplenet.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.getjavajob.simplenet.dao.dao")
public class DAOConfig {

    private static final String JNDI_NAME = "jdbc/simplenet";

    @Bean
    public DataSource getDataSource() {
        JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(JNDI_NAME);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(getDataSource());
    }
}
