package com.getjavajob.simplenet.service.config;

import com.getjavajob.simplenet.dao.config.DAOConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.getjavajob.simplenet.service")
@EnableTransactionManagement
public class ServiceConfig {
}
