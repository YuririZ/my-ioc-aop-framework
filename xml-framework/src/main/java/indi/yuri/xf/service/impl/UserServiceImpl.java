package indi.yuri.xf.service.impl;

import indi.yuri.xf.pojo.User;
import indi.yuri.xf.dao.UserDao;
import indi.yuri.xf.service.UserService;

/**
 * @author yurizhang
 * @date 2021/4/28 11:49
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 通过id获取User
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(Integer id) {
        if (id == null) {
            return null;
        }
        User user = userDao.selectById(id);
        System.out.println(user);
        return user;
    }
}
