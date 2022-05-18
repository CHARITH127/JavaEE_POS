package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    private static PreparedStatement getPreparedStatment(String sql,Connection con, Object... args) throws SQLException, ClassNotFoundException {
        Connection connection = con;
        PreparedStatement statement = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }

        return statement;
    }

    public static boolean executeUpdate(String sql ,Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return getPreparedStatment(sql,connection, args).executeUpdate() > 0;
    }

    public static ResultSet executeQuery(String sql,Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return getPreparedStatment(sql, connection, args).executeQuery();
    }
}
