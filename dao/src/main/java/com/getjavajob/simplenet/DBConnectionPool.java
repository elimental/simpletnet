package com.getjavajob.simplenet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

public class DBConnectionPool {
    private static final int INITIAL_CAPACITY = 10;
    private static DBConnectionPool ourInstance = new DBConnectionPool();
    private LinkedList<Connection> poll;
    private String url;
    private String user;
    private String password;

    private DBConnectionPool() {
        Properties properties = new Properties();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            properties.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.url = properties.getProperty("database.url");
        this.user = properties.getProperty("database.user");
        this.password = properties.getProperty("database.password");
        poll = new LinkedList<>();
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            poll.add(createConnection());
        }
    }

    public static DBConnectionPool getInstance() {
        return ourInstance;
    }

    public synchronized Connection getConnection() {
        if (poll.isEmpty()) {
            poll.add(createConnection());
        }
        return poll.pop();
    }

    public synchronized void returnConnection(Connection connection) {
        poll.push(connection);
    }

    private Connection createConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            DBConnection dbConnection = new DBConnection(connection, this);
            return dbConnection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
