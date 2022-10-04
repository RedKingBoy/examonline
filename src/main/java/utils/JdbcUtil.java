package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;

public class JdbcUtil {
    public static<T> T query(ResultSetHandler<T> resultSetHandler, String sql, Object...params){
        T t = null;
        try {
            Connection connection = DataSourceManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParameterForPreparedStatement(preparedStatement,params);
            ResultSet resultSet = preparedStatement.executeQuery();
            t = resultSetHandler.handler(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }
    public static int update(String sql,Object...params){
        int t = 0;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = DataSourceManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            setParameterForPreparedStatement(preparedStatement,params);
            t = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }
    public static void setParameterForPreparedStatement(PreparedStatement preparedStatement,Object...param) throws SQLException {
        for (int i=0;i<param.length;i++){
            preparedStatement.setObject(i+1,param[i]);
        }
    }
    public static<T> T getObject(ResultSet resultSet,Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i=0;i<columnCount;i++){
            String columnLabel = metaData.getColumnLabel(i + 1);
            Field declaredField = clazz.getDeclaredField(columnLabel);
            String name = declaredField.getName();
            Class<?> type = declaredField.getType();
            String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1);
            Method method = clazz.getMethod(methodName, type);
            Object columnValue = resultSet.getObject(columnLabel, type);
            method.invoke(t, columnValue);
        }
        return t;
    }
}
