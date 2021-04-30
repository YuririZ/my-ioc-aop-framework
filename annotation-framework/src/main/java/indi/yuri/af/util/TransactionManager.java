package indi.yuri.af.util;

import indi.yuri.af.annotation.AutoWrite;
import indi.yuri.af.annotation.Component;

import java.sql.SQLException;

/**
 * @author yurizhang
 * @date 2021/4/28 13:48
 */
@Component
public class TransactionManager {
    @AutoWrite
    private ConnectionUtils connectionUtils;

    /**
     * 开始事务
     */
    public void startTransaction() throws SQLException {
        System.out.println("start transaction");
        connectionUtils.getTreadLocalConnection().setAutoCommit(false);
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public void commit() throws SQLException {
        System.out.println("commit transaction");
        connectionUtils.getTreadLocalConnection().commit();
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        System.out.println("rollback transaction");
        connectionUtils.getTreadLocalConnection().rollback();
    }

    /**
     * 释放链接
     * @throws SQLException
     */
    public void release() throws SQLException {
        System.out.println("close connection");
        connectionUtils.getTreadLocalConnection().close();
        connectionUtils.removeConnection();
    }

}
