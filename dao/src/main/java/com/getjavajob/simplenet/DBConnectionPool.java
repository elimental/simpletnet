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
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private LinkedList<Connection> poll;
    private String url;
    private String user;
    private String password;

    private DBConnectionPool() {
        Properties properties = new Properties();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            properties.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException | ClassNotFoundException e) {
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
        Connection connection = threadLocalConnection.get();
        if (connection!=null) {
            return connection;
        }
        while (poll.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        connection = poll.pop();
        threadLocalConnection.set(connection);
        return connection;
    }

    synchronized void returnConnection(Connection connection) {
        threadLocalConnection.set(null);
        poll.push(connection);
        notifyAll();
    }

    private Connection createConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            return new DBConnection(connection, this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
