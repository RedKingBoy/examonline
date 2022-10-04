package utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceManager {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(DataSource d) {
        dataSource = d;
    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
