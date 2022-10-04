package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {
    T handler(ResultSet resultSet) throws SQLException;
}
