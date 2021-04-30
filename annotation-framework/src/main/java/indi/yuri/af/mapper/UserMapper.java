package indi.yuri.af.mapper;

import indi.yuri.af.annotation.AutoWrite;
import indi.yuri.af.annotation.Mapper;
import indi.yuri.af.pojo.User;
import indi.yuri.af.util.ConnectionUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author yurizhang
 * @date 2021/4/30 11:15
 */
@Mapper
public class UserMapper {
    @AutoWrite
    private ConnectionUtils connectionUtils;

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
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
