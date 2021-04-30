package indi.yuri.xf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author yurizhang
 * @date 2021/4/28 10:46
 */
public class ConnectionUtils {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "rootroot";

    /**
     * 获取当前线程的connection
     * @return
     */
    public Connection getTreadLocalConnection() {
        Connection connection = threadLocal.get();
        if (connection != null) {
            return connection;
        }
        connection = getConnection();
        threadLocal.set(connection);
        return connection;
    }

    /**
     * 建立连接
     * @return
     */
    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 清除链接
     */
    public void removeConnection() {
        threadLocal.remove();
    }

}
