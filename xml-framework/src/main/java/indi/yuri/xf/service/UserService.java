package indi.yuri.xf.service;

import indi.yuri.xf.pojo.User;

/**
 * @author yurizhang
 * @date 2021/4/28 11:49
 */
public interface UserService {
    /**
     * 通过id获取User
     *
     * @param id
     * @return
     */
    User getUserById(Integer id);
}
