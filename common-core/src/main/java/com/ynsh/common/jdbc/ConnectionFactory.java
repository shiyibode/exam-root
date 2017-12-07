package com.ynsh.common.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by chenjianjun on 2016/11/2.
 */
public class ConnectionFactory {
    private static final String JDBC_DRIVER = "jdbc.driver";
    private static final String JDBC_URL = "jdbc.url";
    private static final String JDBC_USERNAME = "jdbc.username";
    private static final String JDBC_PASSWORD = "jdbc.password";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("data-source");

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(RESOURCE_BUNDLE.getString(JDBC_DRIVER));
            connection = DriverManager.getConnection(RESOURCE_BUNDLE.getString(JDBC_URL),
                    RESOURCE_BUNDLE.getString(JDBC_USERNAME), RESOURCE_BUNDLE.getString(JDBC_PASSWORD));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

        }
        return connection;
    }
}
