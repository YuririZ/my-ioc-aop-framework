package indi.yuri.af.service.impl;

import indi.yuri.af.annotation.AutoWrite;
import indi.yuri.af.annotation.Service;
import indi.yuri.af.annotation.Transaction;
import indi.yuri.af.dao.UserDao;
import indi.yuri.af.pojo.User;
import indi.yuri.af.service.UserService;

/**
 * @author yurizhang
 * @date 2021/4/29 17:33
 */
@Transaction
@Service
public class UserServiceImpl implements UserService {

    @AutoWrite
    private UserDao userDao;


    /**
     * 通过id查询
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
