package com.getjavajob.simplenet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class DBConnectionPool {
    private static final int INITIAL_CAPACITY = 5;
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private static DBConnectionPool ourInstance = new DBConnectionPool();
    private DataSource dataSource;
    private LinkedList<Connection> poll;

    private DBConnectionPool() {
        this.dataSource = DataSourceHolder.getDataSource();
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
        if (connection != null) {
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
            Connection connection = dataSource.getConnection();
            return new DBConnection(connection, this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
