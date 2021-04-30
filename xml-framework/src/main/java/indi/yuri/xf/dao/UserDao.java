package indi.yuri.xf.dao;

import indi.yuri.xf.pojo.User;
import indi.yuri.xf.mapper.UserMapper;
import indi.yuri.xf.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author yurizhang
 * @date 2021/4/28 11:23
 */
public class UserDao implements UserMapper {
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Override
    public User selectById(Integer id) {
        if (id == null) {
            return null;
        }
        // 获取当前线程的connection
        Connection connection = connectionUtils.getTreadLocalConnection();
        if (connection == null) {
            System.out.println("connection is null");
            return null;
        }
        User user = new User();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from user where id = %s";
            sql = String.format(sql, id);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                user.setName(name);
                user.setAge(age);
                user.setId(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
