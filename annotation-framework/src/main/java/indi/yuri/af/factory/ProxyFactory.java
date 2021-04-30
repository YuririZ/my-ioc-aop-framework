package indi.yuri.af.factory;



import indi.yuri.af.util.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yurizhang
 * @date 2021/4/28 13:57
 */
public class ProxyFactory {

    /**
     * 获取代理类
     *
     * @param target
     * @return
     */
    public static Object getProxy(final Object target) {
        final TransactionManager transactionManager = (TransactionManager) BeanFactory.getBean("transactionManager");
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    transactionManager.startTransaction();
                    Object result = method.invoke(target, args);
                    transactionManager.commit();
                    return result;
                } catch (Exception e) {
                    transactionManager.rollback();
                    throw new RuntimeException(e);
                } finally {
                    transactionManager.release();
                }
            }
        });
        return proxy;
    }

    /**
     * 获取代理类
     *
     * @param targetName
     * @return
     */
    public static Object getProxy(String targetName) {
        Object target = BeanFactory.getBean(targetName);
        return getProxy(target);
    }
}
