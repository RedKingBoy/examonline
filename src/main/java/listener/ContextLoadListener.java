package listener;

import com.alibaba.druid.pool.DruidDataSource;
import utils.DataSourceManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class ContextLoadListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String jdbc = servletContext.getInitParameter("jdbc");
        Properties properties = new Properties();
        InputStream is = ContextLoadListener.class.getResourceAsStream(jdbc);
        try {
            properties.load(is);
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.configFromPropety(properties);
            druidDataSource.init();
            DataSourceManager.setDataSource(druidDataSource);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DataSource dataSource = DataSourceManager.getDataSource();
        if (dataSource instanceof DruidDataSource) {
            ((DruidDataSource) dataSource).close();
        }
    }
}
