package com.getjavajob.simplenet.dao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = {"classpath:db.test.properties"})
@EnableTransactionManagement
public class DAOTestConfig {
}
