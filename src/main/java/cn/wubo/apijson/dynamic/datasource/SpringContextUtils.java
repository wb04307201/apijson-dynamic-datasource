package cn.wubo.apijson.dynamic.datasource;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {
    @Getter
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtils.applicationContext == null) SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 根据指定的名称获取Bean对象。
     *
     * @param name 要获取的Bean对象的名称
     * @return 根据名称获取到的Bean对象
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 获取指定类型的应用上下文中的Bean对象
     *
     * @param clazz 要获取的Bean对象的类型
     * @param <T>   Bean对象的类型
     * @return 返回指定类型的应用上下文中的Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name，以及clazz返回指定的Bean
     *
     * @param name  Bean的名称
     * @param clazz Bean的类型
     * @param <T>   Bean的类型参数
     * @return 返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 判断应用上下文中是否包含指定名称的bean
     *
     * @param name 要判断的bean名称
     * @return 如果包含指定名称的bean则返回true，否则返回false
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    /**
     * 判断给定Bean是否是单例
     *
     * @param name Bean的名称
     * @return 如果Bean是单例则返回true，否则返回false
     */
    public static boolean isSingleton(String name) {
        return getApplicationContext().isSingleton(name);
    }

    /**
     * 获取指定名称的对象类型。
     *
     * @param name 对象的名称
     * @return 指定名称的对象类型
     */
    public static Class<?> getType(String name) {
        return getApplicationContext().getType(name);
    }
}
