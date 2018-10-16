package com.getjavajob.simplenet.web.config;

import com.getjavajob.simplenet.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfig.class)
@ComponentScan("com.getjavajob.simplenet.web")
public class WebConfig {
}
