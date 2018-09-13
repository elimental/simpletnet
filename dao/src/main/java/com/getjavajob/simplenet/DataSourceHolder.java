package com.getjavajob.simplenet;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class DataSourceHolder {
    private static DataSource dataSource;

    static {
        Properties properties = new Properties();
        try {
            properties.load(DataSourceHolder.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driverClass = properties.getProperty("database.driverclass");
        String url = properties.getProperty("database.url");
        String user = properties.getProperty("database.user");
        String password = properties.getProperty("database.password");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setMaxTotal(10);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        setDataSource(dataSource);
    }

    public static void setDataSource(DataSource dataSource) {
        DataSourceHolder.dataSource = dataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
