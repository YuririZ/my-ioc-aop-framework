package indi.yuri.xf;

import indi.yuri.xf.factory.BeanFactory;
import indi.yuri.xf.factory.ProxyFactory;
import indi.yuri.xf.service.UserService;

/**
 * @author yurizhang
 * @date 2021/4/28 16:43
 */
public class App {
    public static void main(String[] args) {
        // proxyFactory也使用bean注入是为了保证一个线程使用的connection是同一个
        ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
        UserService userService = (UserService) proxyFactory.getProxy("userService");
        userService.getUserById(1);
    }
}
