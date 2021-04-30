package indi.yuri.xf.mapper;

import indi.yuri.xf.pojo.User;

/**
 * @author yurizhang
 * @date 2021/4/28 11:16
 */
public interface UserMapper {
    /**
     * 通过id查询
     * @param id
     * @return
     */
    User selectById(Integer id);
}
