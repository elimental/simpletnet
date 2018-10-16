package com.getjavajob.simplenet.web.listeners;

import com.getjavajob.simplenet.web.config.WebConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
