package indi.yuri.xf.factory;

import indi.yuri.xf.util.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yurizhang
 * @date 2021/4/28 13:57
 */
public class ProxyFactory {
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * 获取代理类
     *
     * @param target
     * @return
     */
    public Object getProxy(Object target) {
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
    public Object getProxy(String targetName) {
        Object target = BeanFactory.getBean(targetName);
        return getProxy(target);
    }
}
