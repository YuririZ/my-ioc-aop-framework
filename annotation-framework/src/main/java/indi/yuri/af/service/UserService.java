package indi.yuri.af.service;

import indi.yuri.af.pojo.User;

/**
 * @author yurizhang
 * @date 2021/4/29 17:33
 */
public interface UserService {
    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    User getUserById(Integer id);
}
