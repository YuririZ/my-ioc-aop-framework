package indi.yuri.af.factory;

import indi.yuri.af.annotation.*;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author yurizhang
 * @date 2021/4/29 14:40
 */
public class BeanFactory {
    private static String BASE_PACKAGE = "indi.yuri.af";

    private static Map<String, Object> BEAN_MAP = new HashMap<String, Object>();

    static {
        List<String> classNames = null;
        classNames = getClassNames();
        try {
            registerBeans(classNames);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String beanName) {
        Object object = BEAN_MAP.get(beanName);
        Class<?> clazz = object.getClass();
        if (clazz.isAnnotationPresent(Transaction.class)) {
            return ProxyFactory.getProxy(object);
        }
        return BEAN_MAP.get(beanName);
    }

    /**
     * 注册bean到map
     *
     * @param classNames
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void registerBeans(List<String> classNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (CollectionUtils.isEmpty(classNames)) {
            return;
        }
        // 先一步写入bean
        for (String className : classNames) {
            Class<?> clazz = Class.forName(className);
            String beanName = getBeanName(clazz);
            if (beanName == null) {
                continue;
            }
            BEAN_MAP.put(beanName, clazz.newInstance());
        }
        // 再注入bean间的引用关系
        for(Map.Entry<String, Object> entry : BEAN_MAP.entrySet()){
            String beanName = entry.getKey();
            Object beanObject = entry.getValue();
            Class<?> clazz = beanObject.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(AutoWrite.class)) {
                    String fieldName = field.getName();
                    Object fieldObject = BEAN_MAP.get(fieldName);
                    field.setAccessible(true);
                    field.set(beanObject, fieldObject);
                }
            }
        }
    }

    /**
     * 获取包下的类的全限定名
     *
     * @return
     */
    private static List<String> getClassNames() {
        //先把包名转换为路径,首先得到项目的classpath
        String classPath = BeanFactory.class.getResource("/").getPath();
        //把包名basePackage转换为路径名
        String basePackage = BASE_PACKAGE.replace(".", File.separator);
        //把classpath和basePack合并
        String scanPath = classPath + basePackage;
        // 文件list
        List<String> filePaths = new ArrayList<String>();
        // 类全限定名list
        List<String> classNames = new ArrayList<String>();
        scan(new File(scanPath), filePaths);
        for (String filePath : filePaths) {
            String className = filePath.replace(classPath.replace("/", "\\").replaceFirst("\\\\", ""), "").replace("\\", ".").replace(".class", "");
            classNames.add(className);
        }
        return classNames;
    }

    /**
     * 扫描文件夹下的类路径
     *
     * @param file
     * @param filePaths
     */
    private static void scan(File file, List<String> filePaths) {
        // 文件夹递归下一级
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                scan(f, filePaths);
            }
        } else {
            //文件判断是否是class文件
            if (file.getName().endsWith(".class")) {
                //如果是class文件我们就放入我们的集合中。
                filePaths.add(file.getPath());
            }
        }
    }

    /**
     * 获取被注解的bean名
     *
     * @param clazz
     * @return
     */
    private static String getBeanName(Class<?> clazz) {
        // 判断是否需要注入
        if (clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Mapper.class)) {
            Class<?>[] interfaces = clazz.getInterfaces();
            // 判断是否只继承了一个接口, 多于一个接口报错, 有且只有一个接口使用接口名, 没有继承接口使用自己的类名
            if (interfaces.length > 1) {
                System.out.println(clazz.getSimpleName() + " has multiple interfaces");
                throw new RuntimeException();
            }
            String beanName;
            if (interfaces.length == 1) {
                Class<?> inter = interfaces[0];
                beanName = inter.getSimpleName();
            } else {
                beanName = clazz.getSimpleName();
            }
            return (new StringBuilder()).append(Character.toLowerCase(beanName.charAt(0))).append(beanName.substring(1)).toString();
        } else {
            return null;
        }
    }
}

