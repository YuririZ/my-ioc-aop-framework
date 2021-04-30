package indi.yuri.af;

import indi.yuri.af.factory.BeanFactory;
import indi.yuri.af.service.UserService;

/**
 * @author yurizhang
 * @date 2021/4/29 16:16
 */
public class App {
    public static void main(String[] args) {
        UserService userService = (UserService) BeanFactory.getBean("userService");
        userService.getUserById(1);
    }
}
