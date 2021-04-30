package indi.yuri.xf.factory;

import indi.yuri.xf.util.XmlReaderUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yurizhang
 * @date 2021/4/27 16:31
 */
public class BeanFactory {
    private static Map<String, Object> BEAN_MAP = new HashMap<String, Object>();

    /**
     * 初始化bean的map
     */
    static {
        // 循环将bean实例写入map
        List<Element> beanElements = XmlReaderUtils.getBeanElements();
        for (Element beanElement : beanElements) {
            String id = beanElement.attributeValue("id");
            String className = beanElement.attributeValue("class");
            try {
                Class<?> clazz = Class.forName(className);
                Object object = clazz.newInstance();
                BEAN_MAP.put(id, object);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 再将bean间的引用关系注入，实际上Spring就是分两步写入bean和bean间的引用关系的
        List<Element> propertyElements = XmlReaderUtils.getPropertyElements();
        for (Element propertyElement : propertyElements) {
            Element parent = propertyElement.getParent();
            String id = parent.attributeValue("id");
            // 作为父类的bean
            Object parentObj = BEAN_MAP.get(id);
            final String name = propertyElement.attributeValue("name");
            Method[] methods = parentObj.getClass().getMethods();
            // 查看bean有没有该属性的set方法
            List<Method> methodList = Arrays.stream(methods).filter(m -> m.getName().toLowerCase().equals("set" + name.toLowerCase())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(methodList)) {
                // 其实这里应该考虑基本数据类型的注入，但是这个demo就不考虑了，直接默认只有引用类型ref的注入
                Method method = methodList.get(0);
                // 作为属性的bean
                Object propertyObj = BEAN_MAP.get(propertyElement.attributeValue("ref"));
                // 通过反射执行set方法设置属性
                try {
                    method.invoke(parentObj, propertyObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(id + " doesn't have set method of " + name);
            }
        }
    }

    /**
     * 获取bean
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return BEAN_MAP.get(beanName);
    }
}
