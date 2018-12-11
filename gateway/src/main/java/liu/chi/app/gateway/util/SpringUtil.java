package liu.chi.app.gateway.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author liuchi
 * @date 2018-12-11 13:28
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
