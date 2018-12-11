package liu.chi.web.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App Listener 监听到 ServletContext 已创建");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("App Listener 监听到 ServletContext 已销毁");
    }
}
