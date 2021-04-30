package indi.yuri.af.dao;

import indi.yuri.af.annotation.AutoWrite;
import indi.yuri.af.annotation.Component;
import indi.yuri.af.mapper.UserMapper;
import indi.yuri.af.pojo.User;

/**
 * @author yurizhang
 * @date 2021/4/30 10:33
 */
@Component
public class UserDao {
    @AutoWrite
    private UserMapper userMapper;

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }
}
