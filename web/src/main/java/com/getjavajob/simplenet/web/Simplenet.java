package com.getjavajob.simplenet.web;

import com.getjavajob.simplenet.dao.config.DAOConfig;
import com.getjavajob.simplenet.service.config.ServiceConfig;
import com.getjavajob.simplenet.web.config.WebConfig;
import com.getjavajob.simplenet.web.config.WebSecurityConfig;
import com.getjavajob.simplenet.web.config.WebSocketConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {
        WebConfig.class,
        WebSecurityConfig.class,
        WebSocketConfig.class,
        ServiceConfig.class,
        DAOConfig.class})
public class Simplenet extends SpringBootServletInitializer {
}
