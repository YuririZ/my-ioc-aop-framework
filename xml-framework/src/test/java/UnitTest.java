import indi.yuri.xf.factory.BeanFactory;
import indi.yuri.xf.factory.ProxyFactory;
import indi.yuri.xf.pojo.User;
import indi.yuri.xf.dao.UserDao;
import indi.yuri.xf.service.UserService;
import indi.yuri.xf.util.ConnectionUtils;
import org.junit.jupiter.api.Test;


/**
 * @author yurizhang
 * @date 2021/4/27 15:32
 */
public class UnitTest {

    @Test
    public void testXmlReaderUtils() {

    }

    @Test
    public void testBeanFactory() {
        UserService userService = (UserService) BeanFactory.getBean("userService");
        User user = userService.getUserById(1);
        System.out.println(user);
    }

    @Test
    public void testConnectionUtils() {
        ConnectionUtils connectionUtils = new ConnectionUtils();
        UserDao userDao = new UserDao();
        userDao.setConnectionUtils(connectionUtils);
        User user = userDao.selectById(1);
        System.out.println(user);
    }

    @Test
    public void testProxyFactory() {
        // proxyFactory也使用bean注入是为了保证一个线程使用的connection是同一个
        ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
        UserService userService = (UserService) proxyFactory.getProxy("userService");
        userService.getUserById(1);
    }

    @Test
    public void testProxy() {
    }
}
